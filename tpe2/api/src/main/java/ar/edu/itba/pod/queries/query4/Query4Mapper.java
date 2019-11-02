package ar.edu.itba.pod.queries.query4;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query4Mapper implements Mapper<Integer, Movement, String, Long> {
    private final String OACIParam;
    private static final Long ONE = 1L;

    public Query4Mapper(final String OACIParam) {
        this.OACIParam = OACIParam;
    }

    @Override
    public void map(Integer integer, Movement movement, Context<String, Long> context) {
        // If origin is correct, emit OACI destination, and '1' accounting for
        // one movement entry
        if(movement.getOACIOrigin().equals(OACIParam)) {
            context.emit(movement.getOACIDestination(), ONE);
        }
    }
}
