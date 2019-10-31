package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.OACIcontainer;
import ar.edu.itba.pod.model.Airport;
import ar.edu.itba.pod.model.Movement;
import ar.edu.itba.pod.queries.query6.Query6Collator;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ExecutionException;

public class Query6 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query6.class);

    private static final Map<String, Airport> airportMap = new HashMap<>();
    private final JobTracker jobTracker;
    private final KeyValueSource<Integer, Movement> source;
    private final String outputPath;
    private final int min;

    private static final String SEPARATOR = ";";
    private static final String HEADER = "Provincia A;Provincia B;Movimientos";

    public Query6(final List<Airport> airports, final IMap<Integer, Movement> movementMap,
                  JobTracker jobTracker, final String outputPath, Integer min) {
        airports.forEach(ap -> this.airportMap.put(ap.getOACIDesignator(), ap));
        this.jobTracker = jobTracker;
        this.source = KeyValueSource.fromMap(movementMap);
        this.outputPath = outputPath;
        this.min = min;
    }

    public void execute() {
        Job<Integer, Movement> job = jobTracker.newJob(source);
        ICompletableFuture<SortedSet<Map.Entry<OACIcontainer, Long>>> future = job
                .mapper(new Query6Mapper())
                .reducer(new Query6ReducerFactory())
                .submit(new Query6Collator());

        try {
            SortedSet<Map.Entry<OACIcontainer, Long>> result = future.get();
            writeOutputFile(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void writeOutputFile(SortedSet<Map.Entry<OACIcontainer, Long>> entries) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            bw.write(HEADER + "\n");
            for(Map.Entry<OACIcontainer, Long> entry : entries) {
                if(entry.getValue() < min) continue;

                String originProvince = getProvince(entry.getKey().getFirstOACI());
                String destinationProvince = getProvince(entry.getKey().getSecondOACI());
                if(originProvince != null && destinationProvince != null) {
                    String keyToWrite = originProvince.compareTo(destinationProvince) < 0 ?
                            originProvince + SEPARATOR + destinationProvince:
                            destinationProvince + SEPARATOR + originProvince;
                    bw.write(keyToWrite + SEPARATOR + entry.getValue() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static String getProvince(String OACI) {
        //LOGGER.info("INSIDE getProvince");
        //for(Map.Entry<String,Airport> entry : airportMap.entrySet()) {
        //    LOGGER.info(entry.getKey());
        //}
        if(airportMap.containsKey(OACI))
            return airportMap.get(OACI).getProvince();
        return null;
    }

}
