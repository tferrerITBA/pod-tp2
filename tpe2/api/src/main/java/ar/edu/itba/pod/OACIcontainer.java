package ar.edu.itba.pod;

import java.io.Serializable;
import java.util.Objects;

public class OACIcontainer implements Serializable {
    private String firstOACI;
    private String secondOACI;

    public OACIcontainer(String o1, String o2) {
        firstOACI = o1;
        secondOACI = o2;
    }

    public String getFirstOACI() {
        return firstOACI;
    }

    public String getSecondOACI() {
        return secondOACI;
    }

    @Override
    public String toString() {
        if(firstOACI.compareTo(secondOACI) < 0)
            return firstOACI + ";" + secondOACI;
        return secondOACI + ";" + firstOACI;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(!(other instanceof OACIcontainer))
            return false;
        OACIcontainer o = (OACIcontainer) other;
        return (this.firstOACI.equals(o.firstOACI) &&
                this.secondOACI.equals(o.secondOACI))||
                (this.firstOACI.equals(o.secondOACI) &&
                        this.secondOACI.equals(o.firstOACI));

    }

    @Override
    public int hashCode() {
        return Objects.hash(firstOACI) + Objects.hash(secondOACI);
    }
}
