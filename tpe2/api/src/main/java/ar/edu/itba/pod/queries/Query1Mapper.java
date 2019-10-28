package ar.edu.itba.pod.queries;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query1Mapper implements Mapper<String, Movement, String, Long> {

    @Override
    public void map(String key, Movement movement, Context<String, Long> context) {
        if(movement.getMovementType().toLowerCase().equals("despegue")) {
            context.emit(movement.getOACIOrigin(), 1L);
        } else {
            context.emit(movement.getOACIDestination(), 1L);
        }
    }

}
