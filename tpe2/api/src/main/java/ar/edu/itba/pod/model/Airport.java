package ar.edu.itba.pod.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class Airport implements DataSerializable {
    private final String OACIDesignator;
    private final String name;
    private final String province;

    public Airport(String OACIDesignator, String name, String province) {
        this.OACIDesignator = OACIDesignator;
        this.name = name;
        this.province = province;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "OACIDesignator='" + OACIDesignator + '\'' +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                '}';
    }

    public String getOACIDesignator() {
        return OACIDesignator;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(OACIDesignator);
        out.writeUTF(name);
        out.writeUTF(province);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {

    }
}
