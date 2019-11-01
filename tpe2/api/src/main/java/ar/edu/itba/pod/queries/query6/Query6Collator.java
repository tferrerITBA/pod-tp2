package ar.edu.itba.pod.queries.query6;

import ar.edu.itba.pod.ProvinceContainer;
import com.hazelcast.mapreduce.Collator;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Query6Collator implements Collator<Map.Entry<ProvinceContainer, Long>, SortedSet<Map.Entry<ProvinceContainer, Long>>> {

    @Override
    public SortedSet<Map.Entry<ProvinceContainer, Long>> collate(Iterable<Map.Entry<ProvinceContainer, Long>> iterable) {
        final SortedSet<Map.Entry<ProvinceContainer, Long>> sortedEntries = new TreeSet<>(
                Comparator
                        .comparing(Map.Entry<ProvinceContainer, Long>::getValue)
                        .reversed()
        );
        iterable.forEach(sortedEntries::add);
        return sortedEntries;
    }
}
