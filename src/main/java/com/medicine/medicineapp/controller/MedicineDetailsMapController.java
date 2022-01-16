package com.medicine.medicineapp.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medicine.medicineapp.dto.MedicineDiseaseMapDto;
import com.medicine.medicineapp.exception.NotAuthorizedException;
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
    
    private HttpServletResponse buildResponseFromCookie(HttpServletRequest request, HttpServletResponse response)
    {
        if(request.getCookies() == null)
            return response;
        String id = request.getCookies()[0].getValue();
        Cookie cookie = new Cookie(request.getCookies()[0].getName(),id);
        response.addCookie(cookie);
        return response;
    }

    private boolean isValidAdmin(Cookie cookie)
    {
        return SessionManager.validateAdminCookie(Integer.parseInt(cookie.getValue()));
    }

    @PostMapping("/add")
    public ResponseEntity<MedicineDiseaseMapDto> insertMap(@RequestBody MedicineDiseaseMapDto data, HttpServletRequest request, HttpServletResponse response)
    {
        response = buildResponseFromCookie(request, response);
        if(isValidAdmin(request.getCookies()[0]))
        {
            return new ResponseEntity<>(service.addMedicineDiseaseMap(data),HttpStatus.CREATED);
        }
        throw new NotAuthorizedException("You are not an admin to add mapping");
    }

    @PutMapping("/update")
    public ResponseEntity<MedicineDiseaseMapDto> updateMap(@RequestBody MedicineDiseaseMapDto data, HttpServletRequest request, HttpServletResponse response)
    {
        response = buildResponseFromCookie(request, response);
        if(isValidAdmin(request.getCookies()[0]))
        {
            return new ResponseEntity<>(service.updateMedicineDiseaseMap(data),HttpStatus.CREATED);
        }
        throw new NotAuthorizedException("You are not an admin to update mapping");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<MedicineDiseaseMapDto> deleteMapping(@RequestBody MedicineDiseaseMapDto data, HttpServletRequest request, HttpServletResponse response)
    {
        response = buildResponseFromCookie(request, response);
        if(isValidAdmin(request.getCookies()[0]))
        {
            return new ResponseEntity<>(service.deleteMedicineDiseaseMap(data),HttpStatus.CREATED);
        }
        throw new NotAuthorizedException("You are not an admin to delete mapping");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicineDiseaseMapDto>> getAllMaps(HttpServletRequest request, HttpServletResponse response) throws SQLException
    {
        Cookie[] all = request.getCookies();
        if(all.length == 1 && all[0].getName().compareTo("sessionId") == 1)
        {
            int id = Integer.parseInt(all[0].getValue());
            if(SessionManager.isValidSession(id, "admin") || SessionManager.isValidSession(id, "buyer"))
            {
                response = buildResponseFromCookie(request, response);
                return new ResponseEntity<>(service.getAllMedicineDiseaseMap(),HttpStatus.OK);
            }
        }
        throw new NotAuthorizedException("You are not authorized");
    }

    @GetMapping("/getAll/disease/{disease}")
    public ResponseEntity<List<MedicineDiseaseMapDto>> getAllMedicineForDisease(@PathVariable("disease")String disease, HttpServletRequest request, HttpServletResponse response) throws SQLException
    {
        Cookie[] all = request.getCookies();
        if(all.length == 1 && all[0].getName().compareTo("sessionId") == 1)
        {
            int id = Integer.parseInt(all[0].getValue());
            if(SessionManager.isValidSession(id, "admin") || SessionManager.isValidSession(id, "buyer"))
            {
                response = buildResponseFromCookie(request, response);
                return new ResponseEntity<>(service.getAllMedicineForDisease(disease),HttpStatus.OK);
            }
        }
        throw new NotAuthorizedException("You are not authorized");
    }

    @GetMapping("/getAll/medicine/{medicine}")
    public ResponseEntity<List<MedicineDiseaseMapDto>> getAllMedicineForDisease(@PathVariable("medicine")int medicineId, HttpServletRequest request, HttpServletResponse response) throws SQLException
    {
        Cookie[] all = request.getCookies();
        if(all.length == 1 && all[0].getName().compareTo("sessionId") == 1)
        {
            int id = Integer.parseInt(all[0].getValue());
            if(SessionManager.isValidSession(id, "admin") || SessionManager.isValidSession(id, "buyer"))
            {
                response = buildResponseFromCookie(request, response);
                return new ResponseEntity<>(service.getAllDiseasesForMedicine(medicineId),HttpStatus.OK);
            }
        }
        throw new NotAuthorizedException("You are not authorized");
    }

}
