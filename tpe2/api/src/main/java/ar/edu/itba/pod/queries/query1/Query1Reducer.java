package ar.edu.itba.pod.queries.query1;

import com.hazelcast.mapreduce.Reducer;

public class Query1Reducer extends Reducer<Long, Long> {
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
