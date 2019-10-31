package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Movement;
import ar.edu.itba.pod.queries.query4.Query4Collator;
import ar.edu.itba.pod.queries.query4.Query4Mapper;
import ar.edu.itba.pod.queries.query4.Query4ReducerFactory;
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
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ExecutionException;

public class Query4 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query1.class);

    private final JobTracker jobTracker;
    private final KeyValueSource<Integer, Movement> source;
    private final String outputPath;
    private final int n;
    private final String OACI;

    private static final String SEPARATOR = ";";
    private static final String HEADER = "OACI;Despegues";

    public Query4(final IMap<Integer, Movement> movementMap,
                  JobTracker jobTracker, final String outputPath, final int n, final String OACI) {
        this.jobTracker = jobTracker;
        this.source = KeyValueSource.fromMap(movementMap);
        this.outputPath = outputPath;
        this.n = n;
        this.OACI = OACI;
    }

    public void execute() {
        Job<Integer, Movement> job = jobTracker.newJob(source);
        ICompletableFuture<SortedSet<Map.Entry<String, Long>>> future = job
                .mapper(new Query4Mapper(OACI))
                .reducer(new Query4ReducerFactory())
                .submit(new Query4Collator(n));

        try {
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
                bw.write(entry.getKey() + SEPARATOR + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
