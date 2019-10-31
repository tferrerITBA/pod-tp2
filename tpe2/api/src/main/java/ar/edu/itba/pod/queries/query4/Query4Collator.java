package ar.edu.itba.pod.queries.query4;

import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class Query4Collator implements Collator<Map.Entry<String, Long>, SortedSet<Map.Entry<String, Long>>> {
    private final int n;

    public Query4Collator(int n) {
        this.n = n;
    }

    @Override
    public SortedSet<Map.Entry<String, Long>> collate(Iterable<Map.Entry<String, Long>> iterable) {

        Comparator<Map.Entry<String, Long>> queryComparator = Comparator
                .comparing(Map.Entry<String, Long>::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey);
        
        final SortedSet<Map.Entry<String, Long>> sortedEntries = new TreeSet<>(queryComparator);
        iterable.forEach(sortedEntries::add);

        SortedSet<Map.Entry<String, Long>> firstNEntries = new TreeSet<>(queryComparator);

        Iterator<Map.Entry<String, Long>> sortedEntriesIterator = sortedEntries.iterator();
        int counter = 0;
        while(sortedEntriesIterator.hasNext() && counter < n) {
            firstNEntries.add(sortedEntriesIterator.next());
            counter++;
        }

        return firstNEntries;
    }
}
