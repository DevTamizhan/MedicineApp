package com.medicine.medicineapp.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medicine.medicineapp.dto.MedicineDiseaseRatingDto;
import com.medicine.medicineapp.service.MedicineDiseaseRatingService;

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
@RequestMapping("/rate")
public class MedicineDiseaseRatingController {

    @Autowired
    MedicineDiseaseRatingService service;

    @PostMapping("/add")
    public ResponseEntity<MedicineDiseaseRatingDto> addRating(@RequestBody MedicineDiseaseRatingDto dto,
            HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(service.insertMedicineDiseaseRating(dto), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MedicineDiseaseRatingDto> updateRating(@RequestBody MedicineDiseaseRatingDto dto,
            HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(service.updateMedicineDiseaseRating(dto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MedicineDiseaseRatingDto> deleteRating(@RequestBody MedicineDiseaseRatingDto dto,
            HttpServletRequest request, HttpServletResponse response) {
        
        return new ResponseEntity<>(service.deleteMedicineDiseaseRating(dto), HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAll/{userid}")
    public ResponseEntity<List<MedicineDiseaseRatingDto>> getAllRatingByUser(@PathVariable("userid") String user,
            HttpServletRequest request, HttpServletResponse response) throws SQLException {
        return new ResponseEntity<>(service.getAllRatingByUser(user), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<MedicineDiseaseRatingDto> getByRatingId(@PathVariable("id") int id,
            HttpServletRequest request, HttpServletResponse response) {
        MedicineDiseaseRatingDto data = service.getRating(id);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicineDiseaseRatingDto>> getAll(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
            return new ResponseEntity<>(service.getAllRatings(), HttpStatus.OK);
    }

}
