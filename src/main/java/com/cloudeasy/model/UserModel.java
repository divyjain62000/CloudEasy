package com.cloudeasy.model;

import com.cloudeasy.domain.user.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * StudentModel class contains all DS related to Student
 */
public class UserModel {
    public static Map<String,User> userEmailIdMap;
    public static Map<String, User> userMobileNumberMap;

    static
    {
        userEmailIdMap=new HashMap<>();
        userMobileNumberMap=new HashMap<>();
    }
}