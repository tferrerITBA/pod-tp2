package ar.edu.itba.pod.queries.query6;

import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class Query6Collator implements Collator<Map.Entry<ProvinceContainer, Long>, SortedSet<Map.Entry<ProvinceContainer, Long>>> {
    private final int min;

    public Query6Collator(final int min) {
        super();
        this.min = min;
    }

    @Override
    public SortedSet<Map.Entry<ProvinceContainer, Long>> collate(Iterable<Map.Entry<ProvinceContainer, Long>> iterable) {
        // Sort entries by movement quantity, then by province name
        final SortedSet<Map.Entry<ProvinceContainer, Long>> entries = new TreeSet<>(
                Comparator
                        .comparing(Map.Entry<ProvinceContainer, Long>::getValue)
                        .reversed()
                        .thenComparing(Map.Entry::getKey)
        );
        iterable.forEach(entry -> {
            if(entry.getValue() >= min)
                entries.add(entry);
        });
        return entries;
    }
}
