package ar.edu.itba.pod.queries.query2;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query2Mapper implements Mapper<String, Movement, String, Long> {

    @Override
    public void map(String key, Movement movement, Context<String, Long> context) {
        if(movement.getFlightClassification().equalsIgnoreCase("cabotaje")) {
            if(movement.getAirlineName() == null || movement.getAirlineName().isEmpty()
                    || movement.getAirlineName().equalsIgnoreCase("n/a")) {
                context.emit("Otros", 1L);
            } else {
                context.emit(movement.getAirlineName(), 1L);
            }
        }
    }

}
