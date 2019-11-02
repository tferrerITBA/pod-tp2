package ar.edu.itba.pod.queries.query6;

import ar.edu.itba.pod.queries.query1.Query1Reducer;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query6ReducerFactory implements ReducerFactory<ProvinceContainer, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(ProvinceContainer key) {
        return new Query1Reducer();
    }

}