package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Airport;
import ar.edu.itba.pod.model.Movement;
import ar.edu.itba.pod.queries.query5.Query5Collator;
import ar.edu.itba.pod.queries.query5.Query5CombinerFactory;
import ar.edu.itba.pod.queries.query5.Query5Mapper;
import ar.edu.itba.pod.queries.query5.Query5ReducerFactory;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Query5 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query5.class);

    private final Set<String> airports;
    private final JobTracker jobTracker;
    private final KeyValueSource<Integer, Movement> source;
    private final String outputPath;
    private final int n;

    private static final String SEPARATOR = ";";
    private static final String HEADER = "OACI;Porcentaje";

    public Query5(final List<Airport> airports, final IMap<Integer, Movement> movementMap,
                  JobTracker jobTracker, final String outputPath,
                  final int n) {
        this.airports = airports
                .stream()
                .map(Airport::getOACIDesignator)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
        this.jobTracker = jobTracker;
        this.source = KeyValueSource.fromMap(movementMap);
        this.outputPath = outputPath;
        this.n = n;
    }

    public void execute() {
        try {
            Job<Integer, Movement> job = jobTracker.newJob(source);
            ICompletableFuture<List<Map.Entry<String, Double>>> future = job
                    .mapper(new Query5Mapper(airports))
                    .combiner(new Query5CombinerFactory())
                    .reducer(new Query5ReducerFactory())
                    .submit(new Query5Collator(n));

            List<Map.Entry<String, Double>> result = future.get();
            writeOutputFile(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void writeOutputFile(List<Map.Entry<String, Double>> result) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            bw.write(HEADER + "\n");
            for(Map.Entry<String, Double> entry : result) {
                bw.write(entry.getKey() + SEPARATOR + String.format("%.2f", entry.getValue()) + "%\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
