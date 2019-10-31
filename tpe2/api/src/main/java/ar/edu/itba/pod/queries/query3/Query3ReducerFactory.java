package ar.edu.itba.pod.queries.query3;

import ar.edu.itba.pod.queries.query2.Query2ReducerFactory;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.*;

public class Query3ReducerFactory implements ReducerFactory<Long, String, List<String>> {

    @Override
    public Reducer<String, List<String>> newReducer(Long key) {
        return new Query3ReducerFactory.QueryReducer();
    }

    private static class QueryReducer extends Reducer<String, List<String>> {
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
