package ar.edu.itba.pod.client;

import ar.edu.itba.pod.model.Airport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AirportsParser implements CsvParser<Airport> {

    @Override
    public List<Airport> parseCsv(final String path) throws IOException, InvalidCsvException {
        List<Airport> airports = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            for(int i = 0; (line = br.readLine()) != null; i++) {
                String[] airportStr = line.split(SEPARATOR);
                try {
                    Airport airport = new Airport(
                            airportStr[0],
                            airportStr[1],
                            airportStr[2],
                            airportStr[3],
                            airportStr[4],
                            airportStr[5],
                            parseDouble(airportStr[6]),
                            parseDouble(airportStr[7]),
                            parseDouble(airportStr[8]),
                            airportStr[9],
                            airportStr[10],
                            parseDouble(airportStr[11]),
                            airportStr[12],
                            airportStr[13],
                            airportStr[14],
                            airportStr[15],
                            airportStr[16],
                            airportStr[17],
                            airportStr[18],
                            airportStr[19],
                            airportStr[20],
                            airportStr[21],
                            airportStr[22]
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

    private static Double parseDouble(String str) {
        Double d;
        try {
            d = Double.parseDouble(str);
        } catch(NumberFormatException e) {
            return null;
        }
        return d;
    }
}
