package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.ProvinceContainer;
import ar.edu.itba.pod.model.Airport;
import ar.edu.itba.pod.model.Movement;
import ar.edu.itba.pod.queries.query6.Query6Collator;
import ar.edu.itba.pod.queries.query6.Query6CombinerFactory;
import ar.edu.itba.pod.queries.query6.Query6Mapper;
import ar.edu.itba.pod.queries.query6.Query6ReducerFactory;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.toMap;

public class Query6 {
    private final Map<String, String> airportMap;
    private final JobTracker jobTracker;
    private final KeyValueSource<Integer, Movement> source;
    private final String outputPath;
    private final int min;

    private static final String SEPARATOR = ";";
    private static final String HEADER = "Provincia A;Provincia B;Movimientos";

    public Query6(final List<Airport> airports, final IMap<Integer, Movement> movementMap,
                  JobTracker jobTracker, final String outputPath, Integer min) {
        airportMap = new HashMap<>();
        airports.forEach(ap -> airportMap.put(ap.getOACIDesignator(), ap.getProvince()));
        this.jobTracker = jobTracker;
        this.source = KeyValueSource.fromMap(movementMap);
        this.outputPath = outputPath;
        this.min = min;
    }

    public void execute() {
        Job<Integer, Movement> job = jobTracker.newJob(source);
        ICompletableFuture<SortedSet<Map.Entry<ProvinceContainer, Long>>> future = job
                .mapper(new Query6Mapper(airportMap))
                .combiner(new Query6CombinerFactory())
                .reducer(new Query6ReducerFactory())
                .submit(new Query6Collator());

        try {
            SortedSet<Map.Entry<ProvinceContainer, Long>> result = future.get();
            writeOutputFile(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void writeOutputFile(SortedSet<Map.Entry<ProvinceContainer, Long>> entries) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            bw.write(HEADER + "\n");
            for(Map.Entry<ProvinceContainer, Long> entry : entries) {
                if(entry.getValue() < min) return;

                bw.write(entry.getKey().toString() + SEPARATOR + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
