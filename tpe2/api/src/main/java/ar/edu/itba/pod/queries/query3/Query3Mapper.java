package ar.edu.itba.pod.queries.query3;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query3Mapper implements Mapper<String, Long, Long, String> {

    @Override
    public void map(String key, Long movementCount, Context<Long, String> context) {
        long thousandMovements = (movementCount / 1000) * 1000;
        if(thousandMovements != 0L) {
            context.emit(thousandMovements, key);
        }
    }

}
