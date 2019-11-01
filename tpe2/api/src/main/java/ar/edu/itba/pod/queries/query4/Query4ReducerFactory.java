package ar.edu.itba.pod.queries.query4;

import ar.edu.itba.pod.queries.query1.Query1Reducer;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query4ReducerFactory implements ReducerFactory<String, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(String key) {
        return new Query1Reducer();
    }
}
