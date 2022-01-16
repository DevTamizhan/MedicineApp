package com.medicine.medicineapp.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.medicine.medicineapp.dao.MedicineDiseaseMap;
import com.medicine.medicineapp.dto.MedicineDiseaseMapDto;
import com.medicine.medicineapp.exception.DataInsertionException;
import com.medicine.medicineapp.repo.MedicineDiseaseMapRepository;
import com.medicine.medicineapp.util.Constants;
import com.medicine.medicineapp.util.DaoToDtoConvertor;
import com.medicine.medicineapp.util.DtoToDaoConvertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineDiseaseMapService {

    @Autowired
    MedicineDiseaseMapRepository repository;

    DaoToDtoConvertor dtoConvertor = new DaoToDtoConvertor();

    DtoToDaoConvertor daoConvertor = new DtoToDaoConvertor();

    public MedicineDiseaseMapDto getMedicineDiseaseMap(int id) throws SQLException
    {
        return dtoConvertor.toMedicineDiseaseMapDto(repository.getById(id));
    }

    public MedicineDiseaseMapDto addMedicineDiseaseMap(MedicineDiseaseMapDto data)
    {
        try
        {
            MedicineDiseaseMap mapData = daoConvertor.toMedicineDiseaseMap(data);
            return dtoConvertor.toMedicineDiseaseMapDto(repository.insert(mapData));
        }
        catch(Exception e)
        {
            throw new DataInsertionException("MedicineDiseaseMap data cannot be inserted",e);
        }
    }

    public MedicineDiseaseMapDto updateMedicineDiseaseMap(MedicineDiseaseMapDto data)
    {
        try
        {
            MedicineDiseaseMap mapData = daoConvertor.toMedicineDiseaseMap(data);
            return dtoConvertor.toMedicineDiseaseMapDto(repository.update(mapData));
        }
        catch(Exception e)
        {
            throw new DataInsertionException("MedicineDiseaseMap data cannot be updated",e);
        }
    }

    public MedicineDiseaseMapDto deleteMedicineDiseaseMap(MedicineDiseaseMapDto data)
    {
        try
        {
            MedicineDiseaseMap mapData = daoConvertor.toMedicineDiseaseMap(data);
            return dtoConvertor.toMedicineDiseaseMapDto(repository.delete(mapData));
        }
        catch(Exception e)
        {
            throw new DataInsertionException("MedicineDiseaseMap data cannot be deleted",e);
        }
    }

    public List<MedicineDiseaseMapDto> getAllMedicineDiseaseMap() throws SQLException
    {
        return convertToList(repository.getAll());
    }

    public List<MedicineDiseaseMapDto> getAllMedicineForDisease(String diseaseName) throws SQLException
    {
        List<MedicineDiseaseMap> recieved = repository.getAllByField(Constants.MEDICINE_DISEASE_MAP_DISEASE_NAME, diseaseName);
        return convertToList(recieved);
    }

    public List<MedicineDiseaseMapDto> getAllDiseasesForMedicine(int medicineId) throws SQLException
    {
        List<MedicineDiseaseMap> recieved = repository.getAllByField(Constants.MEDICINE_DISEASE_MAP_MEDICINE_ID, medicineId);
        return convertToList(recieved);
    }

    private List<MedicineDiseaseMapDto> convertToList(List<MedicineDiseaseMap> maps)
    {
        List<MedicineDiseaseMapDto> _result = new ArrayList<>();
        for (MedicineDiseaseMap medicineDiseaseMap : maps) {
            _result.add(dtoConvertor.toMedicineDiseaseMapDto(medicineDiseaseMap));
        }
        return _result;
    }

}