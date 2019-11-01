package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.queries.*;
import ar.edu.itba.pod.model.Airport;
import ar.edu.itba.pod.model.Movement;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.JobTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private static String[] serverAddresses;
    private static Mode mode;
    private static Path inputDirectory;
    private static Path outputDirectory;
    private static final String AP_INPUT_FILENAME = "aeropuertos.csv";
    private static final String MOV_INPUT_FILENAME = "movimientos.csv";
    private static Integer n;
    private static String OACI;
    private static List<Airport> airports;
    private static List<Movement> movements;

    public static void main(String[] args) {
        LOGGER.info("tpe2 Client Starting ...");
        if(!parseArguments() || !parseQueryArguments())
            System.exit(1);

        LOGGER.info("Inicio de la lectura del archivo");
        parseInputFiles();

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getGroupConfig().setName("g5").setPassword("12345678");
        clientConfig.getNetworkConfig().addAddress(serverAddresses);
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(clientConfig);

        Instant now = Instant.now();
        JobTracker jobTracker = hz.getJobTracker(mode.toString() + "-" + now);
        final IMap<Integer, Movement> remoteMovements;
        switch (mode) {
            case QUERY_1:
                remoteMovements = hz.getMap("mv-" + now);

                for(int i = 0; i < movements.size(); i++)
                    remoteMovements.set(i, movements.get(i));
                LOGGER.info("Fin de lectura del archivo");

                LOGGER.info("Inicio del trabajo map/reduce");
                new Query1(airports, remoteMovements, jobTracker,
                        concatPath(outputDirectory, "query1.csv")).execute();
                LOGGER.info("Fin del trabajo map/reduce");

                remoteMovements.destroy();
                break;
            case QUERY_2:
                remoteMovements = hz.getMap("mv-" + now);
                for(int i = 0; i < movements.size(); i++)
                    remoteMovements.set(i, movements.get(i));
                LOGGER.info("Fin de lectura del archivo");

                LOGGER.info("Inicio del trabajo map/reduce");
                new Query2(remoteMovements, jobTracker,
                        concatPath(outputDirectory, "query2.csv"), n).execute();
                LOGGER.info("Fin del trabajo map/reduce");

                remoteMovements.destroy();
                break;
            case QUERY_3:
                remoteMovements = hz.getMap("mv-" + now);
                for(int i = 0; i < movements.size(); i++)
                    remoteMovements.set(i, movements.get(i));
                LOGGER.info("Fin de lectura del archivo");
                final IMap<String, Long> remoteMovementsCount = hz.getMap("mvc-" + now);

                LOGGER.info("Inicio del trabajo map/reduce");
                new Query3(airports, remoteMovements, remoteMovementsCount, jobTracker,
                        concatPath(outputDirectory, "query3.csv")).execute();
                LOGGER.info("Fin del trabajo map/reduce");

                remoteMovements.destroy();
                break;
            case QUERY_4:
                remoteMovements = hz.getMap("mv-" + now);

                for(int i = 0; i < movements.size(); i++)
                    remoteMovements.set(i, movements.get(i));
                LOGGER.info("Fin de lectura del archivo");

                LOGGER.info("Inicio del trabajo map/reduce");
                new Query4(remoteMovements, jobTracker,
                        concatPath(outputDirectory, "query4.csv"), n, OACI).execute();
                LOGGER.info("Fin del trabajo map/reduce");

                remoteMovements.destroy();
                break;
            case QUERY_5:
                remoteMovements = hz.getMap("mv-" + now);
                for(int i = 0; i < movements.size(); i++)
                    remoteMovements.set(i, movements.get(i));
                LOGGER.info("Fin de lectura del archivo");

                LOGGER.info("Inicio del trabajo map/reduce");
                new Query5(airports, remoteMovements, jobTracker,
                        concatPath(outputDirectory, "query5.csv"), n).execute();
                LOGGER.info("Fin del trabajo map/reduce");

                remoteMovements.destroy();
                break;
            case QUERY_6:
                break;
        }
        HazelcastClient.shutdownAll();
    }

    private static boolean parseArguments() {
        boolean success;
        success = parseServerAddress();
        success &= parseMode();
        success &= parseInputDirectory();
        success &= parseOutputDirectory();
        return success;
    }

    private static boolean parseMode() {
        String modeStr = System.getProperty("query");
        if(modeStr == null) {
            LOGGER.error("Mode must be present.");
            return false;
        }
        try {
            mode = Mode.valueOf("QUERY_" + modeStr);
        } catch (Exception e) {
            LOGGER.error("Mode must be a number between 1 and 6.");
            return false;
        }
        return true;
    }

    private static boolean parseServerAddress() {
        String serverAddress = System.getProperty("addresses");
        if(serverAddress == null) {
            LOGGER.error("serverAddresses must be present.");
            return false;
        }
        serverAddresses = serverAddress.split(";");
        return true;
    }

    private static boolean parseInputDirectory() {
        String inputDirectoryStr = System.getProperty("inPath");
        if(inputDirectoryStr == null) {
            LOGGER.error("input directory must be present");
            return false;
        }
        inputDirectory = Paths.get(inputDirectoryStr);
        return true;
    }

    private static boolean parseOutputDirectory() {
        String outputDirectoryStr = System.getProperty("outPath");
        if(outputDirectoryStr == null) {
            LOGGER.error("output directory must be present");
            return false;
        }
        outputDirectory = Paths.get(outputDirectoryStr);
        return true;
    }

    private static void parseInputFiles() {
        try {
            airports = new AirportsParser().parseCsv(concatPath(inputDirectory, AP_INPUT_FILENAME));
            movements = new MovementsParser().parseCsv(concatPath(inputDirectory, MOV_INPUT_FILENAME));
        } catch(IOException e) {
            LOGGER.error("Error reading input file.");
            e.printStackTrace();
            System.exit(1);
        } catch(InvalidCsvException e) {
            LOGGER.error("Error reading {} file at line {} (got {})", e.filename, e.lineNumber, e.line);
            System.exit(1);
        }
    }

    private static boolean parseQueryArguments() {
        boolean success = true;
        if(mode.equals(Mode.QUERY_2) || mode.equals(Mode.QUERY_5)) {
            success &= parseN();
        }
        if(mode.equals(Mode.QUERY_4)) {
            success &= parseOACI();
            success &= parseN();
        }
        return success;
    }

    private static boolean parseOACI()
    {
        OACI = System.getProperty("oaci");
        if(OACI == null) {
            LOGGER.error("oaci parameter must be present");
            return false;
        }
        return true;
    }

    private static boolean parseN() {
        String nStr = System.getProperty("n");
        n = stringToInt(nStr);
        if(n == null || n <= 0) {
            LOGGER.error("\'n\' parameter must be present and a positive number");
            return false;
        }
        return true;
    }

    private static Integer stringToInt(String str) {
        Integer i;
        try {
            i = Integer.valueOf(str);
        } catch(NumberFormatException e) {
            return null;
        }
        return i;
    }

    private static String concatPath(Path path, String childPath) {
        return path.resolve(childPath).toString();
    }
}
