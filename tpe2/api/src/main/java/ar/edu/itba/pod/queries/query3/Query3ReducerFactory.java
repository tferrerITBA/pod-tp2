package ar.edu.itba.pod.queries.query3;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.*;

public class Query3ReducerFactory implements ReducerFactory<Long, String, List<String>> {

    @Override
    public Reducer<String, List<String>> newReducer(Long key) {
        return new Query3ReducerFactory.QueryReducer();
    }

    private static class QueryReducer extends Reducer<String, List<String>> {
        // List containing OACI designators which have the same amount of thousand movements
        private volatile List<String> apList;

        @Override
        public void beginReduce() {
            apList = new ArrayList<>();
        }

        @Override
        public void reduce(String value) {
            apList.add(value);
        }

        @Override
        public List<String> finalizeReduce() {
            return apList;
        }
    }

}
