package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Airport;
import ar.edu.itba.pod.model.Movement;
import ar.edu.itba.pod.queries.query1.Query1Collator;
import ar.edu.itba.pod.queries.query1.Query1CombinerFactory;
import ar.edu.itba.pod.queries.query1.Query1Mapper;
import ar.edu.itba.pod.queries.query1.Query1ReducerFactory;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Query1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query1.class);

    private final Map<String, Airport> airportMap;
    private final JobTracker jobTracker;
    private final KeyValueSource<Integer, Movement> source;
    private final String outputPath;

    private static final String SEPARATOR = ";";
    private static final String HEADER = "OACI;Denominación;Movimientos";

    public Query1(final List<Airport> airports, final IMap<Integer, Movement> movementMap,
                  JobTracker jobTracker, final String outputPath) {
        this.airportMap = new HashMap<>();
        airports.forEach(ap -> this.airportMap.put(ap.getOACIDesignator(), ap));
        this.jobTracker = jobTracker;
        this.source = KeyValueSource.fromMap(movementMap);
        this.outputPath = outputPath;
    }

    public void execute() {
        try {
            Job<Integer, Movement> job = jobTracker.newJob(source);
            ICompletableFuture<SortedSet<Map.Entry<String, Long>>> future = job
                    .mapper(new Query1Mapper(new HashSet<>(airportMap.keySet())))
                    .combiner(new Query1CombinerFactory())
                    .reducer(new Query1ReducerFactory())
                    .submit(new Query1Collator());

            SortedSet<Map.Entry<String, Long>> result = future.get();
            writeOutputFile(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void writeOutputFile(SortedSet<Map.Entry<String, Long>> entries) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            bw.write(HEADER + "\n");
            for(Map.Entry<String, Long> entry : entries) {
                Airport ap = airportMap.get(entry.getKey());
                bw.write(entry.getKey() + SEPARATOR + ap.getName() + SEPARATOR +
                        entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
