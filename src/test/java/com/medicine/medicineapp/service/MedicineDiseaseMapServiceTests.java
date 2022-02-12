package com.medicine.medicineapp.service;

import java.sql.SQLException;

import com.medicine.medicineapp.dto.MedicineDetailsDto;
import com.medicine.medicineapp.dto.MedicineDiseaseMapDto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class MedicineDiseaseMapServiceTests {
    
    @Autowired
    MedicineDiseaseMapService service;

    @Test
    @Order(1)
    public void getAll()
    {
        try {
            Assertions.assertNotNull(service.getAllMedicineDiseaseMap());
        } catch (SQLException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void getAllByName()
    {
        try
        {
            Assertions.assertNotNull(service.getAllMedicineForDisease("name"));
        }
        catch(Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void checkExceptionOnAdd()
    {
        MedicineDiseaseMapDto dto = new MedicineDiseaseMapDto();
        MedicineDetailsDto disease = new MedicineDetailsDto();
        disease.setMedicineId(1000);
        dto.setMedicineDetails(disease);
        try
        {
            service.addMedicineDiseaseMap(dto);
            Assertions.fail();
        }
        catch(Exception e)
        {
            Assertions.assertTrue(true);
        }
        
    }

    @Test
    @Order(4)
    public void checkExceptionOnUpdate()
    {
        MedicineDiseaseMapDto dto = new MedicineDiseaseMapDto();
        MedicineDetailsDto disease = new MedicineDetailsDto();
        disease.setMedicineId(1000);
        dto.setMedicineDetails(disease);
        try
        {
            service.updateMedicineDiseaseMap(dto);
            Assertions.fail();
        }
        catch(Exception e)
        {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(5)
    public void checkExceptionOnDelete()
    {
        MedicineDiseaseMapDto dto = new MedicineDiseaseMapDto();
        MedicineDetailsDto disease = new MedicineDetailsDto();
        disease.setMedicineId(1000);
        dto.setMedicineDetails(disease);
        try
        {
            service.deleteMedicineDiseaseMap(dto);
            Assertions.fail();
        }
        catch(Exception e)
        {
            Assertions.assertTrue(true);
        }
    }

}
