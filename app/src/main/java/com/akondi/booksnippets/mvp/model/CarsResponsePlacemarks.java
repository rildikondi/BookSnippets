package com.akondi.booksnippets.mvp.model;


public class CarsResponsePlacemarks {

    private String address;
    private int fuel;
    private String exterior;
    private double[] coordinates;
    private String name;
    private String engineType;
    private String vin;
    private String interior;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFuel() {
        return this.fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public String getExterior() {
        return this.exterior;
    }

    public void setExterior(String exterior) {
        this.exterior = exterior;
    }

    public double[] getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngineType() {
        return this.engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getVin() {
        return this.vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getInterior() {
        return this.interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

}
