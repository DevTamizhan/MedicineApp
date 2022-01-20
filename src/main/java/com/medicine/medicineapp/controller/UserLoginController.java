package com.medicine.medicineapp.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medicine.medicineapp.dto.UserLoginDto;
import com.medicine.medicineapp.service.UserLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserLoginController {

    @Autowired
    UserLoginService service;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> createUser(@RequestBody UserLoginDto user) {
        service.createUserLogin(user);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody UserLoginDto user, HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        UserLoginDto dto = service.getUserLoginInfo(user.getUserId());
        if (dto.equals(user)) {
            int session;
            if (user.getUserTypeDto().name().compareToIgnoreCase("admin") == 0)
                session = SessionManager.CreateAdminSession(user.getUserId());
            else
                session = SessionManager.createSession(user.getUserId());
            Cookie cookie = new Cookie("sessionId", session + "");
            response.addCookie(cookie);
            System.out.println("Logged in");
            System.out.println(response);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody UserLoginDto user, HttpServletRequest request,
            HttpServletResponse response) {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/updatePass")
    public ResponseEntity<Boolean> changePassword(@RequestBody UserLoginDto user, HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        UserLoginDto dto = service.getUserLoginInfo(user.getUserId());
        if (user.getUserTypeDto().name().compareTo(dto.getUserTypeDto().name()) == 0) {
            service.updateUserLogin(user);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Boolean> deleteUser(@RequestBody UserLoginDto user, HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        String sessionId = SessionManager.validateAndGetSessionIdFromCookie(request.getCookies(), user.getUserId());
        if (ValidateUser(user)) {
            service.deleteUser(user);
            SessionManager.destroySession(Integer.parseInt(sessionId), user.getUserId());
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("ViewAllUsers")
    public ResponseEntity<List<UserLoginDto>> viewAllUsers(@RequestBody UserLoginDto user, HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        return new ResponseEntity<>(service.allUsers(), HttpStatus.OK);
    }

    private boolean ValidateUser(UserLoginDto user) throws SQLException {
        UserLoginDto data = service.getUserLoginInfo(user.getUserId());
        return data.getPassword().compareTo(user.getPassword()) == 0 &&
                data.getUserTypeDto().name().compareTo(user.getUserTypeDto().name()) == 0;
    }

}
