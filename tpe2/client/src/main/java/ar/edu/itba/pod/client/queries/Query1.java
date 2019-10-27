package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Airport;
import ar.edu.itba.pod.model.Movement;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query1 {
    private final Map<String, Airport> airportMap;
    private final IList<Movement> movements;
    private final JobTracker jobTracker;
    private final KeyValueSource<String, Movement> source;

    public Query1(final List<Airport> airports, final IList<Movement> movements, JobTracker jobTracker) {
        this.airportMap = new HashMap<>();
        airports.forEach(ap -> this.airportMap.put(ap.getOACIDesignator(), ap));
        this.movements = movements;
        this.jobTracker = jobTracker;
        this.source = KeyValueSource.fromList(movements);
    }

    public void execute() {
        Job<String, Movement> job = jobTracker.newJob(source);
        job.mapper(new QueryMapper()).reducer(null).submit();
    }

    private static class QueryMapper implements Mapper<String, Movement, String, Integer> {

        @Override
        public void map(String key, Movement movement, Context<String, Integer> context) {
            context.emit(movement.getOACIOrigin(), 1);
            context.emit(movement.getOACIDestination(), 1);
        }
    }
}
