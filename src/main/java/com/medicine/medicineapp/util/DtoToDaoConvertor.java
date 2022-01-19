package com.medicine.medicineapp.util;

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

public class DtoToDaoConvertor {
	
	public UserType toUserType(UserTypeDto dto)
    {
        return UserType.valueOf(dto.toString());
    }

    public UserLogin toUserLogin(UserLoginDto login)
    {
        UserLogin dao = new UserLogin();
        dao.setUserId(login.getUserId());
        dao.setPassword(login.getPassword());
        dao.setUserType(toUserType(login.getUserTypeDto()));
        return dao;
    }

    public MedicineDetails toMedicineDetails(MedicineDetailsDto details)
    {
        MedicineDetails dao = new MedicineDetails();
        dao.setMaxAge(details.getMaxAge());
        dao.setMedicineId(details.getMedicineId());
        dao.setMedicineName(details.getMedicineName());
        dao.setMinAge(details.getMinAge());
        dao.setPrice(details.getPrice());
        return dao;
    }

    public MedicineDiseaseMap toMedicineDiseaseMap(MedicineDiseaseMapDto map)
    {
    	MedicineDiseaseMap dao = new MedicineDiseaseMap();
    	dao.setDiseaseName(map.getDiseaseName());
    	dao.setEntryId(map.getEntryId());
    	dao.setMedicineId(map.getMedicineDetails().getMedicineId());
    	return dao;
    }

    public MedicineDiseaseRating toMedicineDiseaseRating(MedicineDiseaseRatingDto rating)
    {
    	MedicineDiseaseRating dao = new MedicineDiseaseRating();
        dao.setRatingId(rating.getRatingId());
        dao.setUserId(rating.getUserId());
    	dao.setEntryId(rating.getDiseaseMap().getEntryId());
    	dao.setRating(rating.getRating());
    	return dao;
    }


}
