package com.medicine.medicineapp.controller;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.medicine.medicineapp.exception.NotAuthorizedException;

public class SessionManager {
    
    private static Map<Integer,String> sessionMap = new Hashtable<>();

    private static Map<Integer,String> adminSessionMap = new Hashtable<>();

    private static int loginCounter = 0;

    public static boolean isValidSession(int id, String type)
    {
        if(type.compareToIgnoreCase("admin") == 0)
        {
            return adminSessionMap.containsKey(id);
        }
        else if(type.compareToIgnoreCase("buyer") == 0)
        {
            return sessionMap.containsKey(id);
        }
        return false;
    }

    public static int createSession(String user)
    {
        sessionMap.put(loginCounter, user);
        ++loginCounter;
        return loginCounter-1;
    }

    public static int CreateAdminSession(String user)
    {
        adminSessionMap.put(loginCounter, user);
        ++loginCounter;
        return loginCounter -1;
    }

    public static void destroySession(int id, String user)
    {
        if(sessionMap.containsKey(id) && sessionMap.get(id).compareTo(user) == 0)
        {
            sessionMap.remove(id);
        }
        else if(adminSessionMap.containsKey(id) && adminSessionMap.get(id).compareTo(user) == 0)
        {
            sessionMap.remove(id);
        }
    }

    public static String validateAndGetSessionIdFromCookie(Cookie[] allCookies, String user)
    {
        if(allCookies.length != 1)
            throw new  NotAuthorizedException("Not a valid request");
        int sessionId = Integer.parseInt(allCookies[0].getValue());
        if((sessionMap.containsKey(sessionId) && sessionMap.get(sessionId).compareTo(user) == 0
            || (validateAdminCookie(sessionId) && user.compareTo(adminSessionMap.get(sessionId)) == 0 )))
        {
            return sessionId+"";
        }
        else
            throw new NotAuthorizedException("Not a valid request");
    }

    public static boolean validateAdminCookie(int id)
    {
        return adminSessionMap.containsKey(id);
    }
}
