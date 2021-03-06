package ar.edu.itba.pod.client;

import ar.edu.itba.pod.model.Movement;
import com.hazelcast.core.IMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovementsParser {
    private static final String SEPARATOR = ";";
    private static final int MOVEMENT_DATE_INDEX = 0;
    private static final int MOVEMENT_TIME_INDEX = 1;
    private static final int FLIGHT_TYPE_INDEX = 2;
    private static final int FLIGHT_CLASSIFICATION_INDEX = 3;
    private static final int MOVEMENT_TYPE_INDEX = 4;
    private static final int OACI_ORIGIN_INDEX = 5;
    private static final int OACI_DESTINATION_INDEX = 6;
    private static final int AIRLINE_NAME_INDEX = 7;
    private final IMap<Integer, Movement> remoteMovements;

    public MovementsParser(IMap<Integer, Movement> remoteMovements) {
        this.remoteMovements = remoteMovements;
    }

    public void parseCsv(final String path) throws IOException, InvalidCsvException {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // First line consists of headers only
            if(line == null)
                throw new InvalidCsvException("Invalid CSV file. First line is empty.", path, 0, line);

            for(int i = 0; (line = br.readLine()) != null; i++) {
                String[] movementStr = line.split(SEPARATOR);
                try {
                    Movement movement = new Movement(
                            movementStr[MOVEMENT_DATE_INDEX],
                            movementStr[MOVEMENT_TIME_INDEX],
                            movementStr[FLIGHT_TYPE_INDEX],
                            movementStr[FLIGHT_CLASSIFICATION_INDEX],
                            movementStr[MOVEMENT_TYPE_INDEX],
                            movementStr[OACI_ORIGIN_INDEX],
                            movementStr[OACI_DESTINATION_INDEX],
                            movementStr[AIRLINE_NAME_INDEX]
                    );
                    remoteMovements.set(i, movement);
                } catch(IllegalArgumentException e) {
                    throw new InvalidCsvException(
                            "Invalid CSV file. Error parsing line " + (i+1), path, i+1, line);
                }
            }
        }
    }
}
