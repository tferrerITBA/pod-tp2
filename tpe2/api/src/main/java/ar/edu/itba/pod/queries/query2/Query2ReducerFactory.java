package ar.edu.itba.pod.queries.query2;

import ar.edu.itba.pod.queries.query1.Query1Reducer;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query2ReducerFactory implements ReducerFactory<String, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(String key) {
        return new Query1Reducer();
    }

}
