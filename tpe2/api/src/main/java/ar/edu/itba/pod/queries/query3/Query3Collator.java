package ar.edu.itba.pod.queries.query3;

import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class Query3Collator implements Collator<Map.Entry<Long, List<String>>, SortedSet<Query3Record>> {

    @Override
    public SortedSet<Query3Record> collate(Iterable<Map.Entry<Long, List<String>>> iterable) {
        // Sort entries by thousand movements, then by OACI pair
        final SortedSet<Query3Record> sortedPairs = new TreeSet<>(
                Comparator
                        .comparing(Query3Record::getThousandMovements)
                        .reversed()
                        .thenComparing(qr -> qr.getAirports()[0])
                        .thenComparing(qr -> qr.getAirports()[1])
        );

        iterable.forEach(e -> {
            // Ignore solitary OACI designators
            if(e.getValue().size() > 1) {
                e.getValue().sort(Comparator.naturalOrder());
                for(int i = 0; i < e.getValue().size() - 1; i++) {
                    for(int j = i+1; j < e.getValue().size(); j++) {
                        // Create a Query3Record object containing entry information
                        sortedPairs.add(new Query3Record(
                                e.getKey(),
                                new String[]{e.getValue().get(i), e.getValue().get(j)}
                        ));
                    }
                }
            }
        });
        return sortedPairs;
    }

}
