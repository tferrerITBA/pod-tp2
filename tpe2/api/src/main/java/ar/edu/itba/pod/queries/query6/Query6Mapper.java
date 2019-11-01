package ar.edu.itba.pod.queries.query6;

import ar.edu.itba.pod.model.Airport;
import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import ar.edu.itba.pod.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Query6Mapper implements Serializable, Mapper<Integer, Movement, OACIcontainer, Long> {
    private Map<String, Airport> airports;

    public Query6Mapper(Map<String, Airport> airports) {
        this.airports = airports;
    }
    @Override
    public void map(Integer key, Movement movement, Context<OACIcontainer, Long> context) {
        String originOACI = movement.getOACIOrigin(), destinationOACI = movement.getOACIDestination();
        if(airports.containsKey(originOACI) && airports.containsKey(destinationOACI)) {
            String originProvince = airports.get(originOACI).getProvince();
            String destinationProvince = airports.get(destinationOACI).getProvince();

            if(!originProvince.equals(destinationProvince))
                context.emit(new OACIcontainer(originProvince,destinationProvince), 1L);
        }
    }
}
