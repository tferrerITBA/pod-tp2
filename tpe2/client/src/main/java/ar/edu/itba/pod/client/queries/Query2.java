package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Movement;
import ar.edu.itba.pod.queries.query2.Query2Collator;
import ar.edu.itba.pod.queries.query2.Query2CombinerFactory;
import ar.edu.itba.pod.queries.query2.Query2Mapper;
import ar.edu.itba.pod.queries.query2.Query2ReducerFactory;
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
import java.util.concurrent.ExecutionException;

public class Query2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query2.class);

    private final JobTracker jobTracker;
    private final KeyValueSource<Integer, Movement> source;
    private final String outputPath;
    private final int n;

    private static final String SEPARATOR = ";";
    private static final String HEADER = "Aerolínea;Porcentaje";

    public Query2(final IMap<Integer, Movement> movementMap,
                  JobTracker jobTracker, final String outputPath, final int n) {
        this.jobTracker = jobTracker;
        this.source = KeyValueSource.fromMap(movementMap);
        this.outputPath = outputPath;
        this.n = n;
    }

    public void execute() {
        try {
            Job<Integer, Movement> job = jobTracker.newJob(source);
            ICompletableFuture<List<Map.Entry<String, Double>>> future = job
                    .mapper(new Query2Mapper())
                    .combiner(new Query2CombinerFactory())
                    .reducer(new Query2ReducerFactory())
                    .submit(new Query2Collator(n));

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
                bw.write(entry.getKey() + SEPARATOR + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
