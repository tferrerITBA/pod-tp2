package ar.edu.itba.pod.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class Movement implements DataSerializable {
    private String flightType;
    private String flightClassification;
    private String movementType;
    private String OACIOrigin;
    private String OACIDestination;
    private String airlineName;

    public Movement(String flightType, String flightClassification, String movementType,
                    String OACIOrigin, String OACIDestination, String airlineName) {
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
                "flightType='" + flightType + '\'' +
                ", flightClassification='" + flightClassification + '\'' +
                ", movementType='" + movementType + '\'' +
                ", OACIOrigin='" + OACIOrigin + '\'' +
                ", OACIDestination='" + OACIDestination + '\'' +
                ", airlineName='" + airlineName + '\'' +
                '}';
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

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(flightType);
        out.writeUTF(flightClassification);
        out.writeUTF(movementType);
        out.writeUTF(OACIOrigin);
        out.writeUTF(OACIDestination);
        out.writeUTF(airlineName);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        flightType = in.readUTF();
        flightClassification = in.readUTF();
        movementType = in.readUTF();
        OACIOrigin = in.readUTF();
        OACIDestination = in.readUTF();
        airlineName = in.readUTF();
    }
}
