package ar.edu.itba.pod.queries.query3;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query3Mapper implements Mapper<String, Long, Long, String> {
    private static final Long ZERO = 0L;

    @Override
    public void map(String key, Long movementCount, Context<Long, String> context) {
        long thousandMovements = (movementCount / 1000) * 1000;
        if(thousandMovements != ZERO) {
            context.emit(thousandMovements, key);
        }
    }

}
