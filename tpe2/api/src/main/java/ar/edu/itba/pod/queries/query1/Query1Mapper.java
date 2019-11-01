package ar.edu.itba.pod.queries.query1;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.Set;


public class Query1Mapper implements Mapper<Integer, Movement, String, Long> {
    private final Set<String> airports;
    private static final String TAKEOFF = "Despegue";
    private static final Long ONE = 1L;

    public Query1Mapper(Set<String> airports) {
        this.airports = airports;
    }

    @Override
    public void map(Integer key, Movement movement, Context<String, Long> context) {
        if(isTakeoff(movement)) {
            if(airports.contains(movement.getOACIOrigin()))
                context.emit(movement.getOACIOrigin(), ONE);
        } else {
            if(airports.contains(movement.getOACIDestination()))
                context.emit(movement.getOACIDestination(), ONE);
        }
    }

    private boolean isTakeoff(final Movement movement) {
        return movement.getMovementType().equalsIgnoreCase(TAKEOFF);
    }

}
