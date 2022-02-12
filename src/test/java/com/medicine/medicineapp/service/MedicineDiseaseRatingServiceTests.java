package com.medicine.medicineapp.service;

import com.medicine.medicineapp.dto.MedicineDetailsDto;
import com.medicine.medicineapp.dto.MedicineDiseaseMapDto;
import com.medicine.medicineapp.dto.MedicineDiseaseRatingDto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class MedicineDiseaseRatingServiceTests {
    
    @Autowired
    MedicineDiseaseRatingService service;

    @Test
    @Order(1)
    public void getAll()
    {
        try
        {
            Assertions.assertNotNull(service.getAllRatings());
        }
        catch(Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void getAllByuser()
    {
        try
        {
            Assertions.assertNotNull(service.getAllRatingByUser("user"));
        }
        catch(Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void getAllByMedicineDiseaseMap()
    {
        try
        {
            Assertions.assertNotNull(service.getAllRatingsByDiseaseAndMedicine(1));
        }
        catch(Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(4)
    public void getAllByRatingValue()
    {
        try
        {
            Assertions.assertNotNull(service.getAllRatingsByRatingValue(1));
        }
        catch(Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(5)
    public void getAllByUserAndRating()
    {
        try
        {
            Assertions.assertNotNull(service.getAllRatingsByUserWithRating("user", 1));
        }
        catch(Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(6)
    public void insertionException()
    {
        try
        {
            MedicineDiseaseRatingDto data = new MedicineDiseaseRatingDto();
            MedicineDiseaseMapDto dto = new MedicineDiseaseMapDto();
            MedicineDetailsDto disease = new MedicineDetailsDto();
            disease.setMedicineId(1000);
            data.setDiseaseMap(dto);
            service.insertMedicineDiseaseRating(data);
            Assertions.fail();
        }
        catch(Exception e)
        {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(7)
    public void updateException()
    {
        try
        {
            MedicineDiseaseRatingDto data = new MedicineDiseaseRatingDto();
            MedicineDiseaseMapDto dto = new MedicineDiseaseMapDto();
            MedicineDetailsDto disease = new MedicineDetailsDto();
            disease.setMedicineId(1000);
            data.setDiseaseMap(dto);
            service.updateMedicineDiseaseRating(data);
            Assertions.fail("update should not occur");
        }
        catch(Exception e)
        {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(8)
    public void deleteException()
    {
        try
        {
            MedicineDiseaseRatingDto data = new MedicineDiseaseRatingDto();
            MedicineDiseaseMapDto dto = new MedicineDiseaseMapDto();
            MedicineDetailsDto disease = new MedicineDetailsDto();
            disease.setMedicineId(1000);
            data.setDiseaseMap(dto);
            service.deleteMedicineDiseaseRating(data);
            Assertions.fail("Delete should not occur");
        }
        catch(Exception e)
        {
            Assertions.assertTrue(true);
        }
    }
}
