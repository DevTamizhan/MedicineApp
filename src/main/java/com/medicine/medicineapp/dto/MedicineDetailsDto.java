package com.medicine.medicineapp.dto;

public class MedicineDetailsDto {
    
    private int medicineId;

    private String medicineName;

    private double price;

    private int minAge;

    private int maxAge;

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + maxAge;
        result = prime * result + medicineId;
        result = prime * result + ((medicineName == null) ? 0 : medicineName.hashCode());
        result = prime * result + minAge;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MedicineDetailsDto other = (MedicineDetailsDto) obj;
        if (maxAge != other.maxAge)
            return false;
        if (medicineId != other.medicineId)
            return false;
        if (medicineName == null) {
            if (other.medicineName != null)
                return false;
        } else if (medicineName.compareTo(other.medicineName) != 0)
            return false;
        if (minAge != other.minAge)
            return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;
        return true;
    }

    

}
