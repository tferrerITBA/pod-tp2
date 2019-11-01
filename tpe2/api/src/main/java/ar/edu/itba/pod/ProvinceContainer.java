package ar.edu.itba.pod;

import java.io.Serializable;
import java.util.Objects;

public class ProvinceContainer implements Serializable, Comparable<ProvinceContainer> {
    private final String firstProvince;
    private final String secondProvince;

    public ProvinceContainer(String p1, String p2) {
        Objects.requireNonNull(p1, p2);
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
                this.secondProvince.equals(o.secondProvince)) ||
                (this.firstProvince.equals(o.secondProvince) &&
                        this.secondProvince.equals(o.firstProvince));

    }

    @Override
    public int hashCode() {
        int res = 7;
        boolean cmp = firstProvince.compareTo(secondProvince) >= 0;
        res = 31 * res + (cmp ? firstProvince.hashCode() : secondProvince.hashCode());
        res = 31 * res + (cmp ? secondProvince.hashCode() : firstProvince.hashCode());
        return res;
    }

    @Override
    public int compareTo(ProvinceContainer o) {
        boolean cmpThis = firstProvince.compareTo(secondProvince) >= 0;
        boolean cmpOther = o.firstProvince.compareTo(o.secondProvince) >= 0;
        final String thisMinStr = cmpThis? firstProvince : secondProvince;
        final String otherMinStr = cmpOther? o.firstProvince : o.secondProvince;
        final String thisMaxStr = cmpThis? secondProvince : firstProvince;
        final String otherMaxStr = cmpThis ? o.secondProvince : o.firstProvince;

        return thisMinStr.compareTo(otherMinStr) + thisMaxStr.compareTo(otherMaxStr);
    }
}
