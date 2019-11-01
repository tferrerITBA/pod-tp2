package ar.edu.itba.pod.queries.query5;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.Set;

public class Query5Mapper implements Mapper<Integer, Movement, String, Boolean> {
    private final Set<String> airports;
    private static final String NATIONAL_PRIVATE = "Vuelo Privado con Matrícula Nacional";
    private static final String FOREIGN_PRIVATE = "Vuelo Privado con Matrícula Extranjera";

    public Query5Mapper(Set<String> airports) {
        this.airports = airports;
    }

    @Override
    public void map(Integer key, Movement movement, Context<String, Boolean> context) {
        // Emit OACI designator and a boolean describing whether or not a flight is private
        if(airports.contains(movement.getOACIOrigin())) {
            context.emit(movement.getOACIOrigin(), isPrivateFlight(movement));
        }
        if(airports.contains(movement.getOACIDestination())) {
            context.emit(movement.getOACIDestination(), isPrivateFlight(movement));
        }
    }

    private boolean isPrivateFlight(final Movement movement) {
        return movement.getFlightType().equalsIgnoreCase(NATIONAL_PRIVATE)
                || movement.getFlightType().equalsIgnoreCase(FOREIGN_PRIVATE);
    }

}
