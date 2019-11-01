package ar.edu.itba.pod.queries.query2;

import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class Query2Collator implements Collator<Map.Entry<String, Long>, List<FlightPercentageContainer>> {
    private final int n;
    private static final String OTHERS = "Otros";

    public Query2Collator(int n) {
        super();
        this.n = n;
    }

    @Override
    public List<FlightPercentageContainer> collate(Iterable<Map.Entry<String, Long>> iterable) {
        // Calculate total domestic flights
        Long total = 0L;
        for(Map.Entry<String, Long> entry : iterable) {
            total += entry.getValue();
        }
        final long totalAux = total;

        final SortedSet<FlightPercentageContainer> percentages = new TreeSet<>(
                Comparator
                        .comparing(FlightPercentageContainer::getPercentage)
                        .reversed()
                        .thenComparing(FlightPercentageContainer::getOACI)
        );
        final List<FlightPercentageContainer> selectedPercentages = new ArrayList<>();

        // Convert Flight count to percentages and sort them
        iterable.forEach(entry ->
            percentages.add(new FlightPercentageContainer(
                        entry.getKey(),
                        entry.getValue() * 100 / (double) totalAux))
        );

        // Select n elements with highest percentage
        int count = 0;
        Iterator<FlightPercentageContainer> iterator = percentages.iterator();
        while(iterator.hasNext() && count < n) {
            FlightPercentageContainer curr = iterator.next();
            if(!curr.getOACI().equalsIgnoreCase(OTHERS)) {
                selectedPercentages.add(curr);
                count++;
            }
        }

        // Add final element comprised of all unused percentages
        double remainingPercentage = 100.0 - selectedPercentages.stream()
                .mapToDouble(FlightPercentageContainer::getPercentage)
                .sum();
        selectedPercentages.add(new FlightPercentageContainer(
                OTHERS, remainingPercentage
        ));
        return selectedPercentages;
    }

}
