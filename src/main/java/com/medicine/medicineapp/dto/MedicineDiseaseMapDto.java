package com.medicine.medicineapp.dto;

public class MedicineDiseaseMapDto {
    
    private int entryId;

    private MedicineDetailsDto medicineDetails;

    private String diseaseName;

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public MedicineDetailsDto getMedicineDetails() {
        return medicineDetails;
    }

    public void setMedicineDetails(MedicineDetailsDto medicineDetails) {
        this.medicineDetails = medicineDetails;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }


}
