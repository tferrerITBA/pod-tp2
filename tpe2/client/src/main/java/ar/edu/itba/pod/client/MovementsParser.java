package ar.edu.itba.pod.client;

import ar.edu.itba.pod.model.Movement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovementsParser implements CsvParser<Movement> {
    private static final int FLIGHT_TYPE_INDEX = 2;
    private static final int FLIGHT_CLASSIFICATION_INDEX = 3;
    private static final int MOVEMENT_TYPE_INDEX = 4;
    private static final int OACI_ORIGIN_INDEX = 5;
    private static final int OACI_DESTINATION_INDEX = 6;
    private static final int AIRLINE_NAME_INDEX = 7;

    @Override
    public List<Movement> parseCsv(final String path) throws IOException, InvalidCsvException {
        List<Movement> movements = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // First line consists of headers only
            if(line == null)
                throw new InvalidCsvException("Invalid CSV file. First line is empty.", path, 0, line);

            for(int i = 1; (line = br.readLine()) != null; i++) {
                String[] movementStr = line.split(SEPARATOR);
                try {
                    Movement movement = new Movement(
                            movementStr[FLIGHT_TYPE_INDEX],
                            movementStr[FLIGHT_CLASSIFICATION_INDEX],
                            movementStr[MOVEMENT_TYPE_INDEX],
                            movementStr[OACI_ORIGIN_INDEX],
                            movementStr[OACI_DESTINATION_INDEX],
                            movementStr[AIRLINE_NAME_INDEX]
                    );
                    movements.add(movement);
                } catch(IllegalArgumentException e) {
                    throw new InvalidCsvException(
                            "Invalid CSV file. Error parsing line " + i, path, i, line);
                }
            }
        }
        return movements;
    }
}
