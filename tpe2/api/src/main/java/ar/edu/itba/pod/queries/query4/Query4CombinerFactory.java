package ar.edu.itba.pod.queries.query4;

import ar.edu.itba.pod.queries.query1.Query1Combiner;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class Query4CombinerFactory implements CombinerFactory<String, Long, Long> {

    @Override
    public Combiner<Long, Long> newCombiner(String s) {
        return new Query1Combiner();
    }
}
