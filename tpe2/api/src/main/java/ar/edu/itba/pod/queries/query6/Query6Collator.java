package ar.edu.itba.pod.queries.query6;

import ar.edu.itba.pod.OACIcontainer;
import com.hazelcast.mapreduce.Collator;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Query6Collator implements Collator<Map.Entry<OACIcontainer, Long>, SortedSet<Map.Entry<OACIcontainer, Long>>> {

    @Override
    public SortedSet<Map.Entry<OACIcontainer, Long>> collate(Iterable<Map.Entry<OACIcontainer, Long>> iterable) {
        final SortedSet<Map.Entry<OACIcontainer, Long>> sortedEntries = new TreeSet<>(
                Comparator
                        .comparing(Map.Entry<OACIcontainer, Long>::getValue)
                        .reversed()
        );
        iterable.forEach(sortedEntries::add);
        return sortedEntries;
    }
}
