package ar.edu.itba.pod.queries.query4;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query4Mapper implements Mapper<Integer, Movement, String, Long> {
    private final String OACIParam;

    public Query4Mapper(final String OACIParam) {
        this.OACIParam = OACIParam;
    }

    @Override
    public void map(Integer integer, Movement movement, Context<String, Long> context) {
        if(movement.getOACIOrigin().equals(OACIParam)) {
            context.emit(movement.getOACIDestination(), 1L);
        }
    }
}
