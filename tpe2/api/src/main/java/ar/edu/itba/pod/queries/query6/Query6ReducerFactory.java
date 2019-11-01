package ar.edu.itba.pod.queries.query6;

import ar.edu.itba.pod.ProvinceContainer;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query6ReducerFactory implements ReducerFactory<ProvinceContainer, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(ProvinceContainer key) {
        return new QueryReducer();
    }

    private static class QueryReducer extends Reducer<Long, Long> {
        private volatile long sum;

        @Override
        public void beginReduce() {
            sum = 0;
        }

        @Override
        public void reduce(Long value) {
            sum += value;
        }

        @Override
        public Long finalizeReduce() {
            return sum;
        }
    }

}