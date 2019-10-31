package ar.edu.itba.pod.queries.query3;

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
}
