package com.medicine.medicineapp.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private MedicineDiseaseMapService service;

    DaoToDtoConvertor dtoConvertor = new DaoToDtoConvertor();

    DtoToDaoConvertor daoConvertor = new DtoToDaoConvertor();

    public MedicineDiseaseRatingDto insertMedicineDiseaseRating(MedicineDiseaseRatingDto data)
    {
        try
        {
            MedicineDiseaseRating rating = daoConvertor.toMedicineDiseaseRating(data);
            MedicineDiseaseRating inserted = repository.insert(rating);
            MedicineDiseaseRatingDto returnVal = dtoConvertor.toMedicineDiseaseRatingDto(inserted);
            returnVal.setDiseaseMap(service.getMedicineDiseaseMap(inserted.getEntryId()));
            return returnVal;
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
            MedicineDiseaseRating inserted = repository.update(rating);
            MedicineDiseaseRatingDto returnVal = dtoConvertor.toMedicineDiseaseRatingDto(inserted);
            returnVal.setDiseaseMap(service.getMedicineDiseaseMap(inserted.getEntryId()));
            return returnVal;
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
            MedicineDiseaseRating inserted = repository.delete(rating);
            MedicineDiseaseRatingDto returnVal = dtoConvertor.toMedicineDiseaseRatingDto(inserted);
            returnVal.setDiseaseMap(service.getMedicineDiseaseMap(inserted.getEntryId()));
            return returnVal;
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
            MedicineDiseaseRating rating = repository.getById(id);
            MedicineDiseaseRatingDto dto = dtoConvertor.toMedicineDiseaseRatingDto(rating)           ;
            dto.setDiseaseMap(service.getMedicineDiseaseMap(rating.getEntryId()));
            return dto;

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

    public List<MedicineDiseaseRatingDto> getAllRatingsByRatingValue(int value) throws SQLException
    {
        List<MedicineDiseaseRating> _all = repository.getAllByField(Constants.MEDICINE_DISEASE_RATING_RATING_FILED, value);
        return convertToList(_all);
    }

    public List<MedicineDiseaseRatingDto> getAllRatingsByUserWithRating(String user, int value) throws SQLException
    {
        List<MedicineDiseaseRatingDto> _all = this.getAllRatingByUser(user);
        Stream<MedicineDiseaseRatingDto> s = _all.stream();
        return s.filter(item -> Math.round(item.getRating()) == value).collect(Collectors.toList());
        
    }

    private List<MedicineDiseaseRatingDto> convertToList(List<MedicineDiseaseRating> all) throws SQLException
    {
        List<MedicineDiseaseRatingDto> result = new ArrayList<>();
        for (MedicineDiseaseRating each : all) {
            MedicineDiseaseRatingDto returnVal = dtoConvertor.toMedicineDiseaseRatingDto(each);
            returnVal.setDiseaseMap(service.getMedicineDiseaseMap(each.getEntryId()));
            result.add(returnVal);
        }
        return result;
    }

}
