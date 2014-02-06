package x40240.sidek.sirun.a4;

public final class LocationInfo {
	
    //private float  latitude;
	private String latitude;
    //private float  longitude;
	private String longitude;
    //private int    altitude_unit;
	private String altitude_unit;
    private String address;
    private String description;

    /*
     * @return the latitude
     */
    //public float getLatitude() {}
    public String getLatitude() {
        return latitude;
    }    
    /*
     * @param latitude to set
     */
    //public void setLatitude (float latitude) {}
    public void setLatitude (String latitude) {
        this.latitude = latitude;
    }
    
    /*
     * @return the longitude
     */
    //public float getLongitude() {}
    public String getLongitude() {
        return longitude;
    }    
    /*
     * @param latitude to set
     */
    //public void setLongitude (float longitude) {}
    public void setLongitude (String longitude) {
        this.longitude = longitude;
    }
    
    /*
     * @return the altitude_unit
     */
    //public int getAltitudeUnit() {}
    public String getAltitudeUnit() {
        return altitude_unit;
    }    
    /*
     * @param altitude_unit to set
     */
    //public void setAltitudeUnit (int altitude_unit) {}
    public void setAltitudeUnit (String altitude_unit) {
        this.altitude_unit = altitude_unit;
    }
    
    /*
     * @return address
     */
    public String getAddress() {
    	return address;
    }
    /*
     * @param the address to set
     */
    public void setAddress (String address) {
    	this.address = address;
    }
    
    /*
     * @return description
     */
    public String getDescription() {
    	return description;
    }
    /*
     * @param description to set
     */
    public void setDescription (String description) {
    	this.description = description;
    }
}
