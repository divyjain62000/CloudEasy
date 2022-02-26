package com.cloudeasy.services.utils.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static Boolean isValidMobileNumber(String mobileNumber)
    {
        if(mobileNumber==null) return false;
        Pattern pattern = Pattern.compile("(0|91)?[6-9][0-9]{9}");
        Matcher matcher = pattern.matcher(mobileNumber);
        return (matcher.find() && matcher.group().equals(mobileNumber));
    }

    public static Boolean isValidEmail(String email) {
        Pattern pattern=Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher=pattern.matcher(email);
        return (matcher.find() && matcher.group().equals(email));
    }

}

