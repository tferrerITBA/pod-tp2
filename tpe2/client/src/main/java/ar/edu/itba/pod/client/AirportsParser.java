package ar.edu.itba.pod.client;

import ar.edu.itba.pod.model.Airport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AirportsParser {
    private static final String SEPARATOR = ";";
    private static final int OACI_INDEX = 1;
    private static final int NAME_INDEX = 4;
    private static final int PROVINCE_INDEX = 21;

    public List<Airport> parseCsv(final String path) throws IOException, InvalidCsvException {
        List<Airport> airports = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // First line consists of headers only
            if(line == null)
                throw new InvalidCsvException("Invalid CSV file. First line is empty.", path, 0, line);

            for(int i = 1; (line = br.readLine()) != null; i++) {
                String[] airportStr = line.split(SEPARATOR);
                try {
                    Airport airport = new Airport(
                            airportStr[OACI_INDEX],
                            airportStr[NAME_INDEX],
                            airportStr[PROVINCE_INDEX]
                    );
                    airports.add(airport);
                } catch(IllegalArgumentException e) {
                    throw new InvalidCsvException(
                            "Invalid CSV file. Error parsing line " + i, path, i, line);
                }
            }
        }
        return airports;
    }
}
