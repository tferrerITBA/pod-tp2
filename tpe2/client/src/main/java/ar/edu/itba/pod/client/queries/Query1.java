package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.model.Airport;
import ar.edu.itba.pod.model.Movement;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.*;

public class Query1 {
    private final IList<Airport> airports;
    private final IList<Movement> movements;
    private final JobTracker jobTracker;

    public Query1(IList<Airport> airports, IList<Movement> movements, JobTracker jobTracker) {
        this.airports = airports;
        this.movements = movements;
        this.jobTracker = jobTracker;
    }

    public void execute() {
        Job<String, String> job = jobTracker.newJob(null);
    }

    private static class QueryMapper implements Mapper<Airport, Movement, String, String> {

        @Override
        public void map(Airport s, Movement s2, Context<String, String> context) {

        }
    }
}
