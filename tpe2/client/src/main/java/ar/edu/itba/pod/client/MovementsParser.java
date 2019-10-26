package ar.edu.itba.pod.client;

import ar.edu.itba.pod.model.Movement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovementsParser implements CsvParser<Movement> {

    @Override
    public List<Movement> parseCsv(final String path) throws IOException, InvalidCsvException {
        List<Movement> movements = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            for(int i = 0; (line = br.readLine()) != null; i++) {
                String[] movementStr = line.split(SEPARATOR);
                try {
                    Movement movement = new Movement(
                            movementStr[0],
                            movementStr[1],
                            movementStr[2],
                            movementStr[3],
                            movementStr[4],
                            movementStr[5],
                            movementStr[6],
                            movementStr[7],
                            movementStr[8]
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
