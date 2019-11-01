package ar.edu.itba.pod.queries.query2;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query2Mapper implements Mapper<Integer, Movement, String, Long> {
    private static final String DOMESTIC_FLIGHT = "Cabotaje";
    private static final String OTHERS = "Otros";
    private static final String N_A = "N/A";
    private static final Long ONE = 1L;

    @Override
    public void map(Integer key, Movement movement, Context<String, Long> context) {
        if(movement.getFlightClassification().equalsIgnoreCase(DOMESTIC_FLIGHT)) {
            if(movement.getAirlineName() == null || movement.getAirlineName().isEmpty()
                    || movement.getAirlineName().equalsIgnoreCase(N_A)) {
                context.emit(OTHERS, ONE);
            } else {
                context.emit(movement.getAirlineName(), ONE);
            }
        }
    }

}
