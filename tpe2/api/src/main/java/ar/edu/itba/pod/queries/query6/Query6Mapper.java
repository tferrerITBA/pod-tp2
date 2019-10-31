package ar.edu.itba.pod.queries.query6;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import ar.edu.itba.pod.*;

public class Query6Mapper implements Mapper<Integer, Movement, OACIcontainer, Long> {

    @Override
    public void map(Integer key, Movement movement, Context<OACIcontainer, Long> context) {
        String originOACI = movement.getOACIOrigin();
        String destinationOACI = movement.getOACIDestination();
        if(!originOACI.equals(destinationOACI))
            context.emit(new OACIcontainer(originOACI,destinationOACI), 1L);
    }

}
