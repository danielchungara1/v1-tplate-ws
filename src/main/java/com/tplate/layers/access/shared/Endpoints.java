package com.tplate.layers.access.shared;

public class Endpoints {

    public static final String BASE_PATH = "/api/v1";

    public static final String AUTH = BASE_PATH + "/auth";
    public static final String AUTH_LOGIN = AUTH + "/login";
    public static final String AUTH_RESET_CODE = AUTH + "/password/reset-code";
    public static final String AUTH_UPDATE_PASS = AUTH + "/password";

    public static final String USER = BASE_PATH + "/users";
    public static final String USER_NEW = USER + "/new-user";
    public static final String USER_UPDATE = USER + "/{id}";
    public static final String USER_READ_ONE = USER + "/{id}";
    public static final String USER_READ_MANY = USER;
    public static final String USER_READ_ALL = USER + "/all";
    public static final String USER_DELETE = USER + "/{id}";

    public static final String ROLES = BASE_PATH + "/roles";
    public static final String ROLE_READ_ALL =  ROLES + "/all";
    public static final String ROLE_NEW = ROLES + "/new-role";
    public static final String ROLE_READ_ONE = ROLES + "/{id}";
    public static final String ROLE_UPDATE = ROLES + "/{id}";


    public static final String PERMISSIONS = BASE_PATH + "/permissions";
    public static final String PERMISSIONS_READ_ALL =  PERMISSIONS + "/all";


}
