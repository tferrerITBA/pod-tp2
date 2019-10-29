package ar.edu.itba.pod.queries.query2;

import com.hazelcast.mapreduce.Collator;

import java.util.HashMap;
import java.util.Map;

public class Query2Collator implements Collator<Map.Entry<String, Long>, Map<String, Double>> {

    @Override
    public Map<String, Double> collate(Iterable<Map.Entry<String, Long>> iterable) {
        Map<String, Double> results = new HashMap<>();
        Long total = 0L;
        for(Map.Entry<String, Long> entry : iterable) {
            total += entry.getValue();
        }
        final long totalAux = total;
        iterable.forEach(entry -> results.put(entry.getKey(),
                entry.getValue() * 100 / (double) totalAux));
        return results;
    }

}
