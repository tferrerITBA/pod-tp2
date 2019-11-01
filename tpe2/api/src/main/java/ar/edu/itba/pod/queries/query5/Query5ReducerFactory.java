package ar.edu.itba.pod.queries.query5;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query5ReducerFactory implements ReducerFactory<String, Boolean, Double> {

    @Override
    public Reducer<Boolean, Double> newReducer(String key) {
        return new QueryReducer();
    }

    private static class QueryReducer extends Reducer<Boolean, Double> {
        private volatile int totalMovements;
        private volatile int privateMovements;

        @Override
        public void beginReduce() {
            totalMovements = privateMovements = 0;
        }

        @Override
        public void reduce(Boolean isPrivateFlight) {
            totalMovements += 1;
            if(isPrivateFlight) {
                privateMovements++;
            }
        }

        @Override
        public Double finalizeReduce() {
            return Math.floor((privateMovements * 100 / (double) totalMovements) * 100) / 100;
        }
    }

}
