package ar.edu.itba.pod.queries.query2;

import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class Query2Collator implements Collator<Map.Entry<String, Long>, List<Map.Entry<String, Double>>> {
    private final int n;

    public Query2Collator(int n) {
        super();
        this.n = n;
    }

    @Override
    public List<Map.Entry<String, Double>> collate(Iterable<Map.Entry<String, Long>> iterable) {
        Long total = 0L;
        for(Map.Entry<String, Long> entry : iterable) {
            total += entry.getValue();
        }

        final long totalAux = total;
        Map<String, Double> percentageMap = new HashMap<>();
        iterable.forEach(entry -> percentageMap.put(
                entry.getKey(), (entry.getValue() * 100) / (double) totalAux)
        );
        percentageMap.putIfAbsent("Otros", 0.0);

        Map.Entry<String, Double> othersEntry = null;
        for(Map.Entry<String, Double> entry : percentageMap.entrySet()) {
            if(entry.getKey().equals("Otros")) {
                othersEntry = entry;
                break;
            }
        }

        double sum = percentageMap.remove("Otros");

        final List<Map.Entry<String, Double>> entries = new ArrayList<>(percentageMap.size());
        entries.addAll(percentageMap.entrySet());
        entries.sort(Comparator
                .comparing(Map.Entry<String, Double>::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey));
        List<Map.Entry<String, Double>> otherEntries = entries.subList(n, entries.size());

        sum += otherEntries.stream().mapToDouble(Map.Entry::getValue).sum();
        entries.removeAll(otherEntries);
        othersEntry.setValue(sum); // othersEntry is never null
        entries.add(othersEntry);

        return entries;
    }

}
