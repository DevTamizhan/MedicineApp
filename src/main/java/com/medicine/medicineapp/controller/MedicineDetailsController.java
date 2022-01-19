package com.medicine.medicineapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medicine.medicineapp.dto.MedicineDetailsDto;
import com.medicine.medicineapp.service.MedicineDetailsService;

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
@RequestMapping("/MedicineDetail")
public class MedicineDetailsController {

    @Autowired
    MedicineDetailsService service;

    @PostMapping("Add")
    public ResponseEntity<MedicineDetailsDto> insertMedicineDetail(@RequestBody MedicineDetailsDto dto,
            HttpServletRequest request, HttpServletResponse response) {
            return new ResponseEntity<>(service.insertMedicineDetail(dto), HttpStatus.CREATED);
    }

    @GetMapping("All")
    public ResponseEntity<List<MedicineDetailsDto>> getAllMedicineDetail(HttpServletRequest request,
            HttpServletResponse response) {
        return new ResponseEntity<>(service.getAllMedicineDetails(), HttpStatus.OK);
    }

    @GetMapping("All/Name/{name}")
    public ResponseEntity<List<MedicineDetailsDto>> getMedicineDetailsByName(@PathVariable("name") String name,
            HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(service.getMedicineDetailsByMedicineName(name), HttpStatus.OK);
    }

    @GetMapping("All/Price/{price}")
    public ResponseEntity<List<MedicineDetailsDto>> getMedicineDetailsByPrice(@PathVariable("price") Double price,
            HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(service.getMedicineDetailsByPrice(price), HttpStatus.OK);
    }

    @GetMapping("All/MinAge/{age}")
    public ResponseEntity<List<MedicineDetailsDto>> getMedicineDetailsByMinAge(@PathVariable("age") int age,
            HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(service.getMedicineDetailsByMinAge(age), HttpStatus.OK);
    }

    @GetMapping("All/MaxAge/{age}")
    public ResponseEntity<List<MedicineDetailsDto>> getMedicineDetailsByMaxAge(@PathVariable("age") int age,
            HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(service.getMedicineDetailsByMaxAge(age), HttpStatus.OK);
    }

    @DeleteMapping("Delete")
    public ResponseEntity<MedicineDetailsDto> delete(@RequestBody MedicineDetailsDto dto, HttpServletRequest request,
            HttpServletResponse response) {
            return new ResponseEntity<>(service.deleteMedicineDetail(dto), HttpStatus.OK);
    }

    @PutMapping(value = "Update")
    public ResponseEntity<MedicineDetailsDto> update(@RequestBody MedicineDetailsDto entity, HttpServletRequest request,
            HttpServletResponse response) {
            return new ResponseEntity<>(service.updateMedicineDetail(entity), HttpStatus.OK);
    }

    @GetMapping("Id/{id}")
    public ResponseEntity<MedicineDetailsDto> getMedicineDetailsById(@PathVariable("id") int id,
            HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(service.getMedicineDetailFor(id), HttpStatus.OK);
    }

}
