package ar.edu.itba.pod;

import java.util.Objects;

public class FlightPercentageContainer {
    private final String OACI;
    private final double percentage;

    public FlightPercentageContainer(final String OACI, final double percentage) {
        this.OACI = OACI;
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof FlightPercentageContainer))
            return false;
        FlightPercentageContainer other = (FlightPercentageContainer) o;
        return OACI.equals(other.OACI) &&
                Double.compare(percentage, other.percentage) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(OACI, percentage);
    }

    public String getOACI() {
        return OACI;
    }

    public double getPercentage() {
        return percentage;
    }
}
