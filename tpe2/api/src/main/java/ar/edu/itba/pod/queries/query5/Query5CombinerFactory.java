package ar.edu.itba.pod.queries.query5;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class Query5CombinerFactory implements CombinerFactory<String, Boolean, Long[]> {
    @Override
    public Combiner<Boolean, Long[]> newCombiner(String s) {
        return new Query5Combiner();
    }

    private class Query5Combiner extends Combiner<Boolean, Long[]> {
        private long privateMovements;
        private long totalMovements;

        @Override
        public void reset() {
            totalMovements = privateMovements = 0;
        }

        @Override
        public void combine(Boolean isPrivate) {
            if(isPrivate)
                privateMovements++;
            totalMovements++;
        }

        @Override
        public Long[] finalizeChunk() {
            return new Long[] { privateMovements, totalMovements };
        }
    }
}
