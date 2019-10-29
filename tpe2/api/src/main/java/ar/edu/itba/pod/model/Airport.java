package ar.edu.itba.pod.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.Objects;

public class Airport implements DataSerializable {
    private String OACIDesignator;
    private String name;
    private String province;

    public Airport() {

    }

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
        OACIDesignator = in.readUTF();
        name = in.readUTF();
        province = in.readUTF();
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(!(other instanceof Airport))
            return false;
        Airport o = (Airport) other;
        return this.OACIDesignator.equals(o.getOACIDesignator()) &&
                this.province.equals(o.getProvince()) &&
                this.name.equals(o.getName());

    }

    @Override
    public int hashCode() {
        return Objects.hash(OACIDesignator, province, name);
    }
}
