package ar.edu.itba.pod.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.Objects;

public class Movement implements DataSerializable {
    private String movementDate;
    private String movementTime;
    private String flightType;
    private String flightClassification;
    private String movementType;
    private String OACIOrigin;
    private String OACIDestination;
    private String airlineName;

    public Movement() {

    }

    public Movement(String movementDate, String movementTime, String flightType,
                    String flightClassification, String movementType,
                    String OACIOrigin, String OACIDestination, String airlineName) {
        this.movementDate = movementDate;
        this.movementTime = movementTime;
        this.flightType = flightType;
        this.flightClassification = flightClassification;
        this.movementType = movementType;
        this.OACIOrigin = OACIOrigin;
        this.OACIDestination = OACIDestination;
        this.airlineName = airlineName;
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
                '}';
    }

    public String getMovementDate() { return movementDate; }

    public String getMovementTime() { return movementTime; }

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

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(movementDate);
        out.writeUTF(movementTime);
        out.writeUTF(flightType);
        out.writeUTF(flightClassification);
        out.writeUTF(movementType);
        out.writeUTF(OACIOrigin);
        out.writeUTF(OACIDestination);
        out.writeUTF(airlineName);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        movementDate = in.readUTF();
        movementTime = in.readUTF();
        flightType = in.readUTF();
        flightClassification = in.readUTF();
        movementType = in.readUTF();
        OACIOrigin = in.readUTF();
        OACIDestination = in.readUTF();
        airlineName = in.readUTF();
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(!(other instanceof Movement))
            return false;
        Movement o = (Movement) other;
        return this.movementDate.equals(o.getMovementDate()) &&
            this.movementTime.equals(o.getMovementTime()) &&
            this.flightType.equals(o.getFlightType()) &&
            this.flightClassification.equals(o.getFlightClassification()) &&
            this.movementType.equals(o.getMovementType()) &&
            this.OACIOrigin.equals(o.getOACIOrigin()) &&
            this.OACIDestination.equals(o.getOACIDestination()) &&
            this.airlineName.equals(o.getAirlineName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.movementDate,
                            this.movementTime,
                            this.flightType,
                            this.flightClassification,
                            this.movementType,
                            this.OACIOrigin,
                            this.OACIDestination,
                            this.airlineName);
    }
}
