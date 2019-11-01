package ar.edu.itba.pod.queries.query6;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import ar.edu.itba.pod.*;

import java.util.Map;

public class Query6Mapper implements Mapper<Integer, Movement, ProvinceContainer, Long> {
    private final Map<String, String> airports;

    public Query6Mapper(Map<String, String> airports) {
        this.airports = airports;
    }

    @Override
    public void map(Integer key, Movement movement, Context<ProvinceContainer, Long> context) {
        String originOACI = movement.getOACIOrigin();
        String destinationOACI = movement.getOACIDestination();
        if(airports.containsKey(originOACI) && airports.containsKey(destinationOACI)) {
            String originProvince = airports.get(originOACI);
            String destinationProvince = airports.get(destinationOACI);

            if(!originProvince.equals(destinationProvince))
                context.emit(new ProvinceContainer(originProvince, destinationProvince), 1L);
        }
    }
}
