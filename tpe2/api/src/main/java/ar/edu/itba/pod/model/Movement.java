package ar.edu.itba.pod.model;

public class Movement {
    private final String movementDate;
    private final String movementTime;
    private final String flightType;
    private final String flightClassification;
    private final String movementType;
    private final String OACIOrigin;
    private final String OACIDestination;
    private final String airlineName;
    private final String airshipName;

    public Movement(String movementDate, String movementTime, String flightType, String flightClassification, String movementType, String OACIOrigin, String OACIDestination, String airlineName, String airshipName) {
        this.movementDate = movementDate;
        this.movementTime = movementTime;
        this.flightType = flightType;
        this.flightClassification = flightClassification;
        this.movementType = movementType;
        this.OACIOrigin = OACIOrigin;
        this.OACIDestination = OACIDestination;
        this.airlineName = airlineName;
        this.airshipName = airshipName;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "movementDate='" + movementDate + '\'' +
                ", movementTime='" + movementTime + '\'' +
                ", flightType='" + flightType + '\'' +
                ", flightClassification='" + flightClassification + '\'' +
                ", movementType='" + movementType + '\'' +
                ", OACIOrigin='" + OACIOrigin + '\'' +
                ", OACIDestination='" + OACIDestination + '\'' +
                ", airlineName='" + airlineName + '\'' +
                ", airshipName='" + airshipName + '\'' +
                '}';
    }

    public String getMovementDate() {
        return movementDate;
    }

    public String getMovementTime() {
        return movementTime;
    }

    public String getFlightType() {
        return flightType;
    }

    public String getFlightClassification() {
        return flightClassification;
    }

    public String getMovementType() {
        return movementType;
    }

    public String getOACIOrigin() {
        return OACIOrigin;
    }

    public String getOACIDestination() {
        return OACIDestination;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getAirshipName() {
        return airshipName;
    }
}
