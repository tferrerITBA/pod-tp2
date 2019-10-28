package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Airport;
import ar.edu.itba.pod.model.Movement;
import ar.edu.itba.pod.queries.Query1Mapper;
import ar.edu.itba.pod.queries.Query1ReducerFactory;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Query1 {
    private final Map<String, Airport> airportMap;
    private final IMap<String, Movement> movementMap;
    private final JobTracker jobTracker;
    private final KeyValueSource<String, Movement> source;
    private final String outputPath;
    private String SEPARATOR = ";";

    public Query1(final List<Airport> airports, final IMap<String, Movement> movementMap,
                  JobTracker jobTracker, final String outputPath) {
        this.airportMap = new HashMap<>();
        airports.forEach(ap -> this.airportMap.put(ap.getOACIDesignator(), ap));
        this.movementMap = movementMap;
        this.jobTracker = jobTracker;
        this.source = KeyValueSource.fromMap(movementMap);
        this.outputPath = outputPath;
    }

    public void execute() {
        Job<String, Movement> job = jobTracker.newJob(source);
        ICompletableFuture<Map<String, Long>> future = job
                .mapper(new Query1Mapper())
                .reducer(new Query1ReducerFactory())
                .submit();

        try {
            Map<String, Long> result = future.get(); // SINCRONICO
            writeOutputFile(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void writeOutputFile(Map<String, Long> result) { // ORDENAR! TIRA NULL POINTER EXC
        // BORRAR ARCHIVO?
        System.out.println(result.values().size());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            for(Map.Entry<String, Long> entry : result.entrySet()) {
                bw.write(entry.getKey() + SEPARATOR + airportMap.get(entry.getKey()).getName()
                        + SEPARATOR + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
