package com.medicine.medicineapp.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medicine.medicineapp.dto.MedicineDiseaseMapDto;
import com.medicine.medicineapp.service.MedicineDiseaseMapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mapDisease")
public class MedicineDetailsMapController {

    @Autowired
    MedicineDiseaseMapService service;

    @PostMapping("/add")
    public ResponseEntity<MedicineDiseaseMapDto> insertMap(@RequestBody MedicineDiseaseMapDto data,
            HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(service.addMedicineDiseaseMap(data), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MedicineDiseaseMapDto> updateMap(@RequestBody MedicineDiseaseMapDto data,
            HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(service.updateMedicineDiseaseMap(data), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MedicineDiseaseMapDto> deleteMapping(@RequestBody MedicineDiseaseMapDto data,
            HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(service.deleteMedicineDiseaseMap(data), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicineDiseaseMapDto>> getAllMaps(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        return new ResponseEntity<>(service.getAllMedicineDiseaseMap(), HttpStatus.OK);
    }

    @GetMapping("/getAll/disease/{disease}")
    public ResponseEntity<List<MedicineDiseaseMapDto>> getAllMedicineForDisease(@PathVariable("disease") String disease,
            HttpServletRequest request, HttpServletResponse response) throws SQLException {
        return new ResponseEntity<>(service.getAllMedicineForDisease(disease), HttpStatus.OK);
    }

    @GetMapping("/getAll/medicine/{medicine}")
    public ResponseEntity<List<MedicineDiseaseMapDto>> getAllMedicineForDisease(
            @PathVariable("medicine") int medicineId, HttpServletRequest request, HttpServletResponse response)
            throws SQLException {
        return new ResponseEntity<>(service.getAllDiseasesForMedicine(medicineId), HttpStatus.OK);
    }

}
