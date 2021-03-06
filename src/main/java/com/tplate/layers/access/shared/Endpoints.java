package com.tplate.layers.access.shared;

public class Endpoints {

    public static final String BASE_PATH = "/api/v1";

    public static final String AUTH = BASE_PATH + "/auth";
    public static final String AUTH_LOGIN = AUTH + "/login";
    public static final String AUTH_RESET_CODE = AUTH + "/password/reset-code";
    public static final String AUTH_UPDATE_PASS = AUTH + "/password";
    public static final String AUTH_SIGNUP = AUTH + "/sign-up";

    public static final String USER = BASE_PATH + "/users";
    public static final String USER_NEW = USER + "/new-user";
    public static final String USER_UPDATE = USER + "/{id}";
    public static final String USER_READ_ONE = USER + "/{id}";
    public static final String USER_DELETE = USER + "/{id}";
    public static final String USER_READ_ALL = USER + "/all";

    public static final String ROLE = BASE_PATH + "/roles";
    public static final String ROLE_NEW = ROLE + "/new-role";
    public static final String ROLE_UPDATE = ROLE + "/{id}";
    public static final String ROLE_READ_ONE = ROLE + "/{id}";
    public static final String ROLE_DELETE = ROLE + "/{id}";
    public static final String ROLE_READ_ALL = ROLE + "/all";

    public static final String PERMISSION = BASE_PATH + "/permissions";
    public static final String PERMISSION_READ_ALL = PERMISSION + "/all";


    public static final String BRAND = BASE_PATH + "/brands";
    public static final String BRAND_READ_ALL = BRAND + "/all";
    public static final String BRAND_NEW = BRAND + "/new-brand";
    public static final String BRAND_UPDATE = BRAND + "/{id}";
    public static final String BRAND_READ_ONE = BRAND + "/{id}";
    public static final String BRAND_DELETE = BRAND + "/{id}";

    public static final String CATEGORY = BASE_PATH + "/categories";
    public static final String CATEGORY_NEW = CATEGORY + "/new-category";
    public static final String CATEGORY_UPDATE = CATEGORY + "/{id}";
    public static final String CATEGORY_READ_ONE = CATEGORY + "/{id}";
    public static final String CATEGORY_DELETE = CATEGORY + "/{id}";
    public static final String CATEGORY_READ_ALL = CATEGORY + "/all";

    public static final String PRODUCT = BASE_PATH + "/products";
    public static final String PRODUCT_NEW = PRODUCT + "/new-product";
    public static final String PRODUCT_UPDATE = PRODUCT + "/{id}";
    public static final String PRODUCT_READ_ONE = PRODUCT + "/{id}";
    public static final String PRODUCT_DELETE = PRODUCT + "/{id}";
    public static final String PRODUCT_READ_ALL = PRODUCT + "/all";



}
