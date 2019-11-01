package ar.edu.itba.pod;

import java.io.Serializable;
import java.util.Objects;

public class ProvinceContainer implements Serializable {
    private String firstProvince;
    private String secondProvince;

    public ProvinceContainer(String p1, String p2) {
        firstProvince = p1;
        secondProvince = p2;
    }

    @Override
    public String toString() {
        if(firstProvince.compareTo(secondProvince) < 0)
            return firstProvince + ";" + secondProvince;
        return secondProvince + ";" + firstProvince;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(!(other instanceof ProvinceContainer))
            return false;
        ProvinceContainer o = (ProvinceContainer) other;
        return (this.firstProvince.equals(o.firstProvince) &&
                this.secondProvince.equals(o.secondProvince))||
                (this.firstProvince.equals(o.secondProvince) &&
                        this.secondProvince.equals(o.firstProvince));

    }

    @Override
    public int hashCode() {
        return Objects.hash(firstProvince) + Objects.hash(secondProvince);
    }
}
