package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Movement;
import ar.edu.itba.pod.queries.query1.Query1Mapper;
import ar.edu.itba.pod.queries.query1.Query1ReducerFactory;
import ar.edu.itba.pod.queries.query3.Query3Collator;
import ar.edu.itba.pod.queries.query3.Query3Mapper;
import ar.edu.itba.pod.queries.query3.Query3Record;
import ar.edu.itba.pod.queries.query3.Query3ReducerFactory;
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

public class Query3 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Query3.class);

    private final IMap<String, Long> remoteMovementsCount;
    private final JobTracker jobTracker;
    private final KeyValueSource<Integer, Movement> source;
    private final String outputPath;

    private static final String SEPARATOR = ";";
    private static final String HEADER = "Grupo;Aeropuerto A;Aeropuerto B";

    public Query3(final IMap<Integer, Movement> movementMap,
                  final IMap<String, Long> remoteMovementsCount,
                  JobTracker jobTracker, final String outputPath) {
        this.remoteMovementsCount = remoteMovementsCount;
        this.jobTracker = jobTracker;
        this.source = KeyValueSource.fromMap(movementMap);
        this.outputPath = outputPath;
    }

    public void execute() {
        Job<Integer, Movement> job1 = jobTracker.newJob(source);
        ICompletableFuture<Map<String, Long>> future1 = job1
                .mapper(new Query1Mapper())
                .reducer(new Query1ReducerFactory())
                .submit();

        try {
            Map<String, Long> result = future1.get();
            result.forEach(remoteMovementsCount::put); // se puede mejorar?

            Job<String, Long> job2 = jobTracker.newJob(
                    KeyValueSource.fromMap(remoteMovementsCount)
            );
            ICompletableFuture<SortedSet<Query3Record>> future2 = job2
                    .mapper(new Query3Mapper())
                    .reducer(new Query3ReducerFactory())
                    .submit(new Query3Collator());

            SortedSet<Query3Record> result2 = future2.get();
            writeOutputFile(result2);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void writeOutputFile(SortedSet<Query3Record> result) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            bw.write(HEADER + "\n");
            for(Query3Record record : result) {
                bw.write(record.getThousandMovements() + SEPARATOR + record.getAirports()[0]
                        + SEPARATOR + record.getAirports()[1] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
