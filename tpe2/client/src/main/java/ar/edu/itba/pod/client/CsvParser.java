package ar.edu.itba.pod.client;

import java.io.IOException;
import java.util.List;

public interface CsvParser<T> {
    String SEPARATOR = ";";

    List<T> parseCsv(String path) throws IOException, InvalidCsvException;
}
