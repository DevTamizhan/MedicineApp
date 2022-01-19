package com.medicine.medicineapp.service;

import java.util.ArrayList;
import java.util.List;

import com.medicine.medicineapp.dao.MedicineDetails;
import com.medicine.medicineapp.dto.MedicineDetailsDto;
import com.medicine.medicineapp.exception.DataInsertionException;
import com.medicine.medicineapp.exception.DataNotFoundException;
import com.medicine.medicineapp.repo.MedicineDetailsRepository;
import com.medicine.medicineapp.util.Constants;
import com.medicine.medicineapp.util.DaoToDtoConvertor;
import com.medicine.medicineapp.util.DtoToDaoConvertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineDetailsService {

    @Autowired
    MedicineDetailsRepository repository;

    DaoToDtoConvertor dtoConvertor = new DaoToDtoConvertor();

    DtoToDaoConvertor daoConvertor = new DtoToDaoConvertor();

    public MedicineDetailsDto getMedicineDetailFor(int id)
    {
        try
        {
            System.out.println(dtoConvertor == null);
            return dtoConvertor.toMedicineDetailsDto(repository.getById(id));
        }
        catch(Exception e)
        {
            System.out.println("not found");
            throw new DataNotFoundException("Medicine detail cannot be fetched for Id " + id, e);
        }
    }

    public MedicineDetailsDto insertMedicineDetail(MedicineDetailsDto dto)
    {
        try
        {
            MedicineDetails data = daoConvertor.toMedicineDetails(dto);
            return dtoConvertor.toMedicineDetailsDto(repository.insert(data));
        }
        catch(Exception e)
        {
            throw new DataInsertionException("Medicine detail cannot be inserted due to " + e.getMessage(), e);
        }
    }

    public MedicineDetailsDto deleteMedicineDetail(MedicineDetailsDto dto)
    {
        try
        {
            repository.delete(daoConvertor.toMedicineDetails(dto));
            return dto;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Deletion of MedicineDetails failed due to " + e.getMessage(),e);
        }
    }

    public List<MedicineDetailsDto> getAllMedicineDetails()
    {
        try
        {
            List<MedicineDetails> all = repository.getAll();
            List<MedicineDetailsDto> returnList = new ArrayList<>();
            for(MedicineDetails detail : all)
            {
                returnList.add(dtoConvertor.toMedicineDetailsDto(detail));
            }
            return returnList;
        }
        catch(Exception e)
        {
            throw new DataNotFoundException("All medicine details cannot be fetched due to " + e.getMessage(), e);
        }
    }

    public MedicineDetailsDto updateMedicineDetail(MedicineDetailsDto detail)
    {
        try
        {
            repository.update(daoConvertor.toMedicineDetails(detail));
            return detail;
        }
        catch(Exception e)
        {
            throw new DataInsertionException("Updation of MedicineDetails failed due to " + e.getMessage(),e);
        }   
    }

    public List<MedicineDetailsDto> getMedicineDetailsByMedicineName(String name)
    {
        try
        {
            List<MedicineDetails> all = repository.getAllByField(Constants.MEDICINE_DETAILS_MEDICINE_NAME, name);
            List<MedicineDetailsDto> returnList = new ArrayList<>();
            for(MedicineDetails detail : all)
            {
                returnList.add(dtoConvertor.toMedicineDetailsDto(detail));
            }
            return returnList;
        }
        catch(Exception e)
        {
            throw new DataNotFoundException("All medicine details filtered by name cannot be fetched due to " + e.getMessage(), e);
        }
    }

    public List<MedicineDetailsDto> getMedicineDetailsByPrice(Double price)
    {
        try
        {
            List<MedicineDetails> all = repository.getAllByField(Constants.MEDICINE_DETAILS_PRICE, price);
            List<MedicineDetailsDto> returnList = new ArrayList<>();
            for(MedicineDetails detail : all)
            {
                returnList.add(dtoConvertor.toMedicineDetailsDto(detail));
            }
            return returnList;
        }
        catch(Exception e)
        {
            throw new DataNotFoundException("All medicine details filtered by price cannot be fetched due to " + e.getMessage(), e);
        }
    }

    public List<MedicineDetailsDto> getMedicineDetailsByMinAge(int age)
    {
        try
        {
            List<MedicineDetails> all = repository.getAllByField(Constants.MEDICINE_DETAILS_MIN_AGE, age);
            List<MedicineDetailsDto> returnList = new ArrayList<>();
            for(MedicineDetails detail : all)
            {
                returnList.add(dtoConvertor.toMedicineDetailsDto(detail));
            }
            return returnList;
        }
        catch(Exception e)
        {
            throw new DataNotFoundException("All medicine details filtered by min age cannot be fetched due to " + e.getMessage(), e);
        }
    }

    public List<MedicineDetailsDto> getMedicineDetailsByMaxAge(int age)
    {
        try
        {
            List<MedicineDetails> all = repository.getAllByField(Constants.MEDICINE_DETAILS_MAX_AGE, age);
            List<MedicineDetailsDto> returnList = new ArrayList<>();
            for(MedicineDetails detail : all)
            {
                returnList.add(dtoConvertor.toMedicineDetailsDto(detail));
            }
            return returnList;
        }
        catch(Exception e)
        {
            throw new DataNotFoundException("All medicine details cannot be fetched due to " + e.getMessage(), e);
        }
    }

}
