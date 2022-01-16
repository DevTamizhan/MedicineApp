package com.medicine.medicineapp.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.medicine.medicineapp.dao.UserLogin;
import com.medicine.medicineapp.dto.UserLoginDto;
import com.medicine.medicineapp.exception.DataInsertionException;
import com.medicine.medicineapp.repo.UserLoginRepository;
import com.medicine.medicineapp.util.DaoToDtoConvertor;
import com.medicine.medicineapp.util.DtoToDaoConvertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    
    @Autowired
    UserLoginRepository repository;

    DaoToDtoConvertor dtoConvertor = new DaoToDtoConvertor();

    DtoToDaoConvertor daoConvertor = new DtoToDaoConvertor();

    public UserLoginDto createUserLogin(UserLoginDto data)
    {
        try
        {
            UserLogin dataInserted = repository.insert(daoConvertor.toUserLogin(data));
            return dtoConvertor.toUserLoginDto(dataInserted);
        }
        catch(Exception e)
        {
            throw new DataInsertionException("UserLogin cannot be created due to "+ e.getMessage(),e);
        }
    }

    public UserLoginDto updateUserLogin(UserLoginDto data)
    {
        try
        {
            UserLogin dataInserted = repository.update(daoConvertor.toUserLogin(data));
            return dtoConvertor.toUserLoginDto(dataInserted);
        }
        catch(Exception e)
        {
            throw new DataInsertionException("UserLogin cannot be updated due to "+ e.getMessage(),e);
        }
    }

    public UserLoginDto deleteUser(UserLoginDto data)
    {
        try
        {
            UserLogin dataDeleted = repository.delete(daoConvertor.toUserLogin(data));
            return dtoConvertor.toUserLoginDto(dataDeleted);
        }
        catch(Exception e)
        {
            throw new DataInsertionException("UserLogin cannot be deleted due to "+ e.getMessage(),e);
        }
    }

    public UserLoginDto getUserLoginInfo(String userId) throws SQLException
    {
        return dtoConvertor.toUserLoginDto(repository.getById(userId));
    }

    public List<UserLoginDto> allUsers() throws SQLException
    {
        List<UserLogin> _all = repository.getAll();
        List<UserLoginDto> result = new ArrayList<>();
        for (UserLogin each : _all) {
            result.add(dtoConvertor.toUserLoginDto(each));
        }
        return result;
    }
}
