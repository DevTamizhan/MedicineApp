package com.medicine.medicineapp.util;

import com.mysql.cj.exceptions.OperationCancelledException;

public final class Constants {

    private Constants()
    {
        throw new OperationCancelledException("Cannot instantiate Constants");
    }

    public static final String MEDICINE_DETAILS_TABLE = "medicine_details";

    public static final String USER_LOGIN_TABLE = "user_login";

    public static final String MEDICINE_DISEASE_MAP_TABLE = "medicine_disease_map";

    public static final String MEDICINE_DISEASE_RATING_TABLE = "medicine_disease_rating";

    public static final String MEDICINE_DETAILS_MEDICINE_ID = "medicine_id";

    public static final String MEDICINE_DETAILS_MEDICINE_NAME = "medicine_name";

    public static final String MEDICINE_DETAILS_PRICE = "price";

    public static final String MEDICINE_DETAILS_MIN_AGE = "min_age";

    public static final String MEDICINE_DETAILS_MAX_AGE = "max_age";

    public static final String USER_LOGIN_USER_ID = "user_id";

    public static final String USER_LOGIN_PASSWORD = "password";

    public static final String USER_LOGIN_TYPE = "user_type";

    public static final String MEDICINE_DISEASE_MAP_ENTRY_ID = "entry_id";

    public static final String MEDICINE_DISEASE_MAP_MEDICINE_ID = "medicine_id";

    public static final String MEDICINE_DISEASE_MAP_DISEASE_NAME = "disease_name";

    public static final String MEDICINE_DISEASE_RATING_USER_ID = "user_id";

    public static final String MEDICINE_DISEASE_RATING_ENTRY_ID = "entry_id";

    public static final String MEDICINE_DISEASE_RATING_RATING_FILED = "rating";

    public static final String MEDICINE_DISEASE_RATING_RATING_ID = "rating_id";

    
    
}
