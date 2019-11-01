package ar.edu.itba.pod.queries.query6;

import ar.edu.itba.pod.queries.query1.Query1Combiner;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class Query6CombinerFactory implements CombinerFactory<ProvinceContainer, Long, Long> {

    @Override
    public Combiner<Long, Long> newCombiner(ProvinceContainer provinceContainer) {
        return new Query1Combiner();
    }
}
