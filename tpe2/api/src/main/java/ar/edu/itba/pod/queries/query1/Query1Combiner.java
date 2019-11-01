package ar.edu.itba.pod.queries.query1;

import com.hazelcast.mapreduce.Combiner;

public class Query1Combiner extends Combiner<Long, Long> {
    private long sum = 0;

    @Override
    public void reset() {
        sum = 0;
    }

    @Override
    public void combine(Long value) {
        sum += value;
    }

    @Override
    public Long finalizeChunk() {
        return sum;
    }
}
