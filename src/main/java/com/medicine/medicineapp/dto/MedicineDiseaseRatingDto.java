package com.medicine.medicineapp.dto;

public class MedicineDiseaseRatingDto {

    private int ratingId;

    private String userId;

    private MedicineDiseaseMapDto diseaseMap;

    private double rating;
    
    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MedicineDiseaseMapDto getDiseaseMap() {
        return diseaseMap;
    }

    public void setDiseaseMap(MedicineDiseaseMapDto diseaseMap) {
        this.diseaseMap = diseaseMap;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
    
}
