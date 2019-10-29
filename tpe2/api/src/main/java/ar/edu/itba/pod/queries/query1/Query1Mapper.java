package ar.edu.itba.pod.queries.query1;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query1Mapper implements Mapper<Integer, Movement, String, Long> {

    @Override
    public void map(Integer key, Movement movement, Context<String, Long> context) {
        if(movement.getMovementType().equalsIgnoreCase("despegue")) {
            context.emit(movement.getOACIOrigin(), 1L);
        } else {
            context.emit(movement.getOACIDestination(), 1L);
        }
    }

}
