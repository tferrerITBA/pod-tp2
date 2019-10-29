package ar.edu.itba.pod.queries.query1;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Collator;

import java.util.Map;

public class Query1Collator implements Collator<Map.Entry<String, Movement>, Map<String, Movement>> {

    @Override
    public Map<String, Movement> collate(Iterable<Map.Entry<String, Movement>> iterable) {
        return null;
    }
}
