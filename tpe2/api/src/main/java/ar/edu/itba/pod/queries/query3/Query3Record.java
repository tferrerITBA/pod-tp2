package ar.edu.itba.pod.queries.query3;

import java.util.Arrays;
import java.util.Objects;

public class Query3Record {
    private long thousandMovements;
    private String[] airports;

    public Query3Record(final long thousandMovements, final String[] airports) {
        this.thousandMovements = thousandMovements;
        this.airports = airports;
    }

    public long getThousandMovements() {
        return thousandMovements;
    }

    public String[] getAirports() {
        return airports;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(!(other instanceof Query3Record))
            return false;
        Query3Record o = (Query3Record) other;
        return (this.thousandMovements == o.thousandMovements) &&
                Arrays.equals(this.airports, o.airports);

    }

    @Override
    public int hashCode() {
        return Objects.hash(thousandMovements, airports[0], airports[1]);
    }
}
