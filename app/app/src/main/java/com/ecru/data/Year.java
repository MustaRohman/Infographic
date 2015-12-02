package com.ecru.data;

/**
 * Created by nashwan on 12/1/2015.
 */
public class Year {
    private float year;
    private float agri;
    private float services;
    private float industry;

    public Year(float year, float agri, float services, float industry) {
        this.year = year;
        this.agri = agri;
        this.services = services;
        this.industry = industry;
    }

    public float getYear() {
        return year;
    }

    public void setYear(float year) {
        this.year = year;
    }

    public float getAgri() {
        return agri;
    }

    public void setAgri(float agri) {
        this.agri = agri;
    }

    public float getServices() {
        return services;
    }

    public void setServices(float services) {
        this.services = services;
    }

    public float getIndustry() {
        return industry;
    }

    public void setIndustry(float industry) {
        this.industry = industry;
    }
}
