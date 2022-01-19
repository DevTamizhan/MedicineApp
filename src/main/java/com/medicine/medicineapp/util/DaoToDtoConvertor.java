package com.medicine.medicineapp.util;

import java.sql.SQLException;

import com.medicine.medicineapp.dao.MedicineDetails;
import com.medicine.medicineapp.dao.MedicineDiseaseMap;
import com.medicine.medicineapp.dao.MedicineDiseaseRating;
import com.medicine.medicineapp.dao.UserLogin;
import com.medicine.medicineapp.dao.UserType;
import com.medicine.medicineapp.dto.MedicineDetailsDto;
import com.medicine.medicineapp.dto.MedicineDiseaseMapDto;
import com.medicine.medicineapp.dto.MedicineDiseaseRatingDto;
import com.medicine.medicineapp.dto.UserLoginDto;
import com.medicine.medicineapp.dto.UserTypeDto;

public class DaoToDtoConvertor {

    public UserTypeDto toUserTypeDto(UserType dao)
    {
        return UserTypeDto.valueOf(dao.toString());
    }

    public UserLoginDto toUserLoginDto(UserLogin login)
    {
        UserLoginDto dto = new UserLoginDto();
        dto.setUserId(login.getUserId());
        dto.setPassword(login.getPassword());
        dto.setUserTypeDto(toUserTypeDto(login.getUserType()));
        return dto;
    }

    public MedicineDetailsDto toMedicineDetailsDto(MedicineDetails details)
    {
        MedicineDetailsDto dto = new MedicineDetailsDto();
        dto.setMaxAge(details.getMaxAge());
        dto.setMedicineId(details.getMedicineId());
        dto.setMedicineName(details.getMedicineName());
        dto.setMinAge(details.getMinAge());
        dto.setPrice(details.getPrice());
        return dto;
    }

    public MedicineDiseaseMapDto toMedicineDiseaseMapDto(MedicineDiseaseMap map)
    {
        if(map == null)
            return null;
    	MedicineDiseaseMapDto dto = new MedicineDiseaseMapDto();
    	dto.setDiseaseName(map.getDiseaseName());
    	dto.setEntryId(map.getEntryId());
    	return dto;
    }

    public MedicineDiseaseRatingDto toMedicineDiseaseRatingDto(MedicineDiseaseRating rating) throws SQLException
    {
    	MedicineDiseaseRatingDto dto = new MedicineDiseaseRatingDto();
        dto.setRatingId(rating.getRatingId());
    	dto.setUserId(rating.getUserId());
    	dto.setRating(rating.getRating());
    	return dto;
    }
    
}
