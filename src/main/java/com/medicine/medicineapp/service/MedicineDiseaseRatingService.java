package com.medicine.medicineapp.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.medicine.medicineapp.dao.MedicineDiseaseRating;
import com.medicine.medicineapp.dto.MedicineDiseaseRatingDto;
import com.medicine.medicineapp.exception.DataInsertionException;
import com.medicine.medicineapp.exception.DataNotFoundException;
import com.medicine.medicineapp.repo.MedicineDiseaseRatingRepository;
import com.medicine.medicineapp.util.Constants;
import com.medicine.medicineapp.util.DaoToDtoConvertor;
import com.medicine.medicineapp.util.DtoToDaoConvertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineDiseaseRatingService {
    
    @Autowired
    private MedicineDiseaseRatingRepository repository;

    DaoToDtoConvertor dtoConvertor = new DaoToDtoConvertor();

    DtoToDaoConvertor daoConvertor = new DtoToDaoConvertor();

    public MedicineDiseaseRatingDto insertMedicineDiseaseRating(MedicineDiseaseRatingDto data)
    {
        try
        {
            MedicineDiseaseRating rating = daoConvertor.toMedicineDiseaseRating(data);
            return dtoConvertor.toMedicineDiseaseRatingDto(repository.insert(rating));
        }
        catch(Exception e)
        {
            throw new DataInsertionException("Rating cannot be added due to "+e.getMessage(), e);
        }
    }

    public MedicineDiseaseRatingDto updateMedicineDiseaseRating(MedicineDiseaseRatingDto data)
    {
        try
        {
            MedicineDiseaseRating rating = daoConvertor.toMedicineDiseaseRating(data);
            return dtoConvertor.toMedicineDiseaseRatingDto(repository.update(rating));
        }
        catch(Exception e)
        {
            throw new DataInsertionException("Rating cannot be updated due to "+e.getMessage(), e);
        }
    }

    public MedicineDiseaseRatingDto deleteMedicineDiseaseRating(MedicineDiseaseRatingDto data)
    {
        try
        {
            MedicineDiseaseRating rating = daoConvertor.toMedicineDiseaseRating(data);
            return dtoConvertor.toMedicineDiseaseRatingDto(repository.delete(rating));
        }
        catch(Exception e)
        {
            throw new DataInsertionException("Rating cannot be deleted due to "+e.getMessage(), e);
        }
    }

    public MedicineDiseaseRatingDto getRating(int id)
    {
        try
        {
            return dtoConvertor.toMedicineDiseaseRatingDto(repository.getById(id));
        }
        catch(Exception e)
        {
            throw new DataNotFoundException("Rating with id "+id + " not found",e);
        }
    }

    public List<MedicineDiseaseRatingDto> getAllRatings() throws SQLException
    {
        return convertToList(repository.getAll());
    }

    public List<MedicineDiseaseRatingDto> getAllRatingByUser(String user) throws SQLException
    {
        List<MedicineDiseaseRating> _all = repository.getAllByField(Constants.MEDICINE_DISEASE_RATING_USER_ID, user);
        return convertToList(_all);
    }

    public List<MedicineDiseaseRatingDto> getAllRatingsByDiseaseAndMedicine(int medicineDiseaseEntry) throws SQLException
    {
        List<MedicineDiseaseRating> _all = repository.getAllByField(Constants.MEDICINE_DISEASE_RATING_ENTRY_ID, medicineDiseaseEntry);
        return convertToList(_all);
    }

    private List<MedicineDiseaseRatingDto> convertToList(List<MedicineDiseaseRating> all) throws SQLException
    {
        List<MedicineDiseaseRatingDto> result = new ArrayList<>();
        for (MedicineDiseaseRating each : all) {
            result.add(dtoConvertor.toMedicineDiseaseRatingDto(each));
        }
        return result;
    }

}
