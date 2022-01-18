package com.medicine.medicineapp.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medicine.medicineapp.dto.MedicineDiseaseRatingDto;
import com.medicine.medicineapp.exception.NotAuthorizedException;
import com.medicine.medicineapp.service.MedicineDiseaseRatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(allowedHeaders = {"Access-Control-Allow-Credentials"})
public class MedicineDiseaseRatingController {
    
    @Autowired
    MedicineDiseaseRatingService service;

    private HttpServletResponse buildResponseFromCookie(HttpServletRequest request, HttpServletResponse response)
    {
        if(request.getCookies() == null)
            return response;
        String id = request.getCookies()[0].getValue();
        Cookie cookie = new Cookie(request.getCookies()[0].getName(),id);
        response.addCookie(cookie);
        return response;
    }

    @PostMapping("/add")
    public ResponseEntity<MedicineDiseaseRatingDto> addRating(@RequestBody MedicineDiseaseRatingDto dto, HttpServletRequest request, HttpServletResponse response)
    {
        SessionManager.validateAndGetSessionIdFromCookie(request.getCookies(), dto.getUserId());
        response = buildResponseFromCookie(request, response);
        return new ResponseEntity<>(service.insertMedicineDiseaseRating(dto),HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MedicineDiseaseRatingDto> updateRating(@RequestBody MedicineDiseaseRatingDto dto, HttpServletRequest request, HttpServletResponse response)
    {
        SessionManager.validateAndGetSessionIdFromCookie(request.getCookies(), dto.getUserId());
        response = buildResponseFromCookie(request, response);
        return new ResponseEntity<>(service.updateMedicineDiseaseRating(dto),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MedicineDiseaseRatingDto> deleteRating(@RequestBody MedicineDiseaseRatingDto dto, HttpServletRequest request, HttpServletResponse response)
    {
        SessionManager.validateAndGetSessionIdFromCookie(request.getCookies(), dto.getUserId());
        response = buildResponseFromCookie(request, response);
        return new ResponseEntity<>(service.deleteMedicineDiseaseRating(dto),HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAll/{userid}")
    public ResponseEntity<List<MedicineDiseaseRatingDto>> getAllRatingByUser(@PathVariable("userid")String user, HttpServletRequest request, HttpServletResponse response) throws SQLException
    {
        SessionManager.validateAndGetSessionIdFromCookie(request.getCookies(), user);
        response = buildResponseFromCookie(request, response);
        return new ResponseEntity<>(service.getAllRatingByUser(user),HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<MedicineDiseaseRatingDto> getByRatingId(@PathVariable("id")int id, HttpServletRequest request, HttpServletResponse response)
    {
        MedicineDiseaseRatingDto data = service.getRating(id);
        String userId = data.getUserId();
        SessionManager.validateAndGetSessionIdFromCookie(request.getCookies(), userId);
        response = buildResponseFromCookie(request, response);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicineDiseaseRatingDto>> getAll(HttpServletRequest request, HttpServletResponse response) throws SQLException
    {
        Cookie[] all = request.getCookies();
        if(all.length == 1 && all[0].getName().compareTo("sessionId") == 0)
        {
            SessionManager.validateAdminCookie(Integer.parseInt(all[0].getValue()));
            response = buildResponseFromCookie(request, response);
            return new ResponseEntity<>(service.getAllRatings(), HttpStatus.OK);
        }
        throw new NotAuthorizedException("You are not an admin to view all data");
        
    }

}
