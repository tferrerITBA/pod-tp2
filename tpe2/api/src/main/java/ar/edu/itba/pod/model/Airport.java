package ar.edu.itba.pod.model;

public class Airport {
    private final String localDesignator;
    private final String OACIDesignator;
    private final String IATADesignator;
    private final String type;
    private final String name;
    private final String coordinates;
    private final Double latitude;
    private final Double longitude;
    private final Double altitude;
    private final String altitudeUnit;
    private final String referenceCity;
    private final Double referenceDistance;
    private final String referenceDirection;
    private final String condition;
    private final String control;
    private final String region;
    private final String FIR;
    private final String usage;
    private final String traffic;
    private final String SNA;
    private final String concessioned;
    private final String province;
    private final String inhabilitated;

    public Airport(String localDesignator, String OACIDesignator, String IATADesignator,
                   String type, String name, String coordinates, Double latitude,
                   Double longitude, Double altitude, String altitudeUnit, String referenceCity,
                   Double referenceDistance, String referenceDirection, String condition,
                   String control, String region, String FIR, String usage, String traffic,
                   String SNA, String concessioned, String province, String inhabilitated) {
        this.localDesignator = localDesignator;
        this.OACIDesignator = OACIDesignator;
        this.IATADesignator = IATADesignator;
        this.type = type;
        this.name = name;
        this.coordinates = coordinates;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.altitudeUnit = altitudeUnit;
        this.referenceCity = referenceCity;
        this.referenceDistance = referenceDistance;
        this.referenceDirection = referenceDirection;
        this.condition = condition;
        this.control = control;
        this.region = region;
        this.FIR = FIR;
        this.usage = usage;
        this.traffic = traffic;
        this.SNA = SNA;
        this.concessioned = concessioned;
        this.province = province;
        this.inhabilitated = inhabilitated;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "localDesignator='" + localDesignator + '\'' +
                ", OACIDesignator='" + OACIDesignator + '\'' +
                ", IATADesignator='" + IATADesignator + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", altitudeUnit='" + altitudeUnit + '\'' +
                ", referenceCity='" + referenceCity + '\'' +
                ", referenceDistance=" + referenceDistance +
                ", referenceDirection='" + referenceDirection + '\'' +
                ", condition='" + condition + '\'' +
                ", control='" + control + '\'' +
                ", region='" + region + '\'' +
                ", FIR='" + FIR + '\'' +
                ", usage='" + usage + '\'' +
                ", traffic='" + traffic + '\'' +
                ", SNA='" + SNA + '\'' +
                ", concessioned='" + concessioned + '\'' +
                ", province='" + province + '\'' +
                ", inhabilitated='" + inhabilitated + '\'' +
                '}';
    }

    public String getLocalDesignator() {
        return localDesignator;
    }

    public String getOACIDesignator() {
        return OACIDesignator;
    }

    public String getIATADesignator() {
        return IATADesignator;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public String getAltitudeUnit() {
        return altitudeUnit;
    }

    public String getReferenceCity() {
        return referenceCity;
    }

    public double getReferenceDistance() {
        return referenceDistance;
    }

    public String getReferenceDirection() {
        return referenceDirection;
    }

    public String getCondition() {
        return condition;
    }

    public String getControl() {
        return control;
    }

    public String getRegion() {
        return region;
    }

    public String getFIR() {
        return FIR;
    }

    public String getUsage() {
        return usage;
    }

    public String getTraffic() {
        return traffic;
    }

    public String getSNA() {
        return SNA;
    }

    public String getConcessioned() {
        return concessioned;
    }

    public String getProvince() {
        return province;
    }

    public String getInhabilitated() {
        return inhabilitated;
    }

}
