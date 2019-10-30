package ar.edu.itba.pod.queries.query1;

import com.hazelcast.mapreduce.Collator;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Query1Collator implements Collator<Map.Entry<String, Long>, SortedSet<Map.Entry<String, Long>>> {

    @Override
    public SortedSet<Map.Entry<String, Long>> collate(Iterable<Map.Entry<String, Long>> iterable) {
        final SortedSet<Map.Entry<String, Long>> sortedEntries = new TreeSet<>(
                Comparator
                        .comparing(Map.Entry<String, Long>::getValue)
                        .reversed()
                        .thenComparing(Map.Entry::getKey)
        );
        iterable.forEach(sortedEntries::add);
        return sortedEntries;
    }
}
