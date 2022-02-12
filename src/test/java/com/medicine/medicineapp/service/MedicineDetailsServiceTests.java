package com.medicine.medicineapp.service;

import java.util.ArrayList;
import java.util.List;

import com.medicine.medicineapp.dto.MedicineDetailsDto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class MedicineDetailsServiceTests {

    private static List<MedicineDetailsDto> allMedicines = new ArrayList<>();

    private static List<MedicineDetailsDto> currentData = new ArrayList<>();

    @Autowired
    MedicineDetailsService service;

    public void initialize()
    {
        List<MedicineDetailsDto> all = service.getAllMedicineDetails();
        for(MedicineDetailsDto dto : all)
        {
            allMedicines.add(dto);
        }
    }

    @Test
    @Order(1)
    public void addMedicine()
    {
        initialize();
        System.out.println("Test1");
        MedicineDetailsDto dto = new MedicineDetailsDto();
        dto.setMedicineName("testMedicineName1");
        dto.setPrice(100);
        try
        {
            MedicineDetailsDto inserted = service.insertMedicineDetail(dto);
            Assertions.assertTrue(inserted.getMedicineId() > 0);
            allMedicines.add(inserted);
            currentData.add(inserted);
        }
        catch(Exception e)
        {
            Assertions.fail();
        }
    }

    @Test
    @Order(2)
    public void getMedicine()
    {
        try
        {
            System.out.println("Test2");
            MedicineDetailsDto dto = service.getMedicineDetailFor(allMedicines.get(allMedicines.size()-1).getMedicineId());
            Assertions.assertNotNull(dto);
            Assertions.assertEquals(dto.getMedicineId(), allMedicines.get(allMedicines.size()-1).getMedicineId());
        }
        catch(Exception e)
        {
            Assertions.fail();
        }
    }

    @Test
    @Order(3)
    public void updateMedicine()
    {
        try
        {
            System.out.println("Test3");
            MedicineDetailsDto toUpdate = currentData.get(0);
            toUpdate.setMaxAge(100);
            MedicineDetailsDto updated = service.updateMedicineDetail(toUpdate);
            Assertions.assertNotNull(updated);
            Assertions.assertEquals(100, updated.getMaxAge());
            allMedicines.remove(allMedicines.get(allMedicines.size() -1));
            allMedicines.add(updated);
        }
        catch(Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    @Order(4)
    @Test
    public void delete()
    {
        try
        {
            System.out.println("Test4");
            MedicineDetailsDto deleted = service.deleteMedicineDetail(currentData.get(0));
            Assertions.assertNotNull(deleted);
            Assertions.assertEquals(deleted.getMedicineId(), currentData.get(0).getMedicineId());
            currentData.remove(0);
            allMedicines.remove(allMedicines.get(allMedicines.size() -1));
        }
        catch(Exception e)
        {
            Assertions.fail(e.getMessage());
        }
    }

    public void dispose()
    {
        for (MedicineDetailsDto medicineDetailsDto : currentData) {
            service.deleteMedicineDetail(medicineDetailsDto);
        }
    }

    @Test
    @Order(5)
    public void checkAllData()
    {
        Assertions.assertNotNull(service.getAllMedicineDetails());
    }

    @Test
    @Order(6)
    public void checkAllByMaxAge()
    {
        Assertions.assertNotNull(service.getMedicineDetailsByMaxAge(0));
    }
    
}
