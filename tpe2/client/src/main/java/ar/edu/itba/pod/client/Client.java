package ar.edu.itba.pod.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        LOGGER.info("tpe2 Client Starting ...");
        if(args.length < 1) {
            System.err.println("Expected server IP address as first and only argument.");
            System.exit(1);
        }

        //Example from the documentation

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getGroupConfig().setName("g5").setPassword("12345678");
        clientConfig.getNetworkConfig().addAddress(args[0]);

        HazelcastInstance client = HazelcastClient.newHazelcastClient( clientConfig );

        IMap map = client.getMap( "customers" );
        System.out.println( "Map Size:" + map.size() );
    }
}
