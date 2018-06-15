package com.mattcasanova.weatherreport.Utility;

/**
 * This constants class is uses for holding strings and values that aren't used in Activities.  Since
 * xml resources need to be loaded with a context, these static variables are a little more accessible
 * to classes that dont have direct access to a context
 */
public class Constants {
    //Errors
    public static final String ADD_CITY_ERROR                     = "Due to limitations on how many API requests can be made in a given time frame, the number of cities on the dashboard has been limited.";


    //Values
    public static final int MAX_CITIES                            = 20;

    //Strings
    public static final String ICON_PREFIX                        = "icon_";
    public static final String DRAWABLE_TYPE                      = "drawable";
    public static final String FAHRENHEIT_SYMBOL                  = " \u2109";
    public static final String HUMIDITY_SUFFIX                    = "%";
    public static final String PRESSURE_SUFFIX                    = " hPa";

    //JSON Keys
    public static final String NAME                               = "name";
    public static final String ID                                 = "id";
    public static final String COUNTRY_CODE                       = "country";
    public static final String HUMIDITY                           = "humidity";
    public static final String PRESSURE                           = "pressure";
    public static final String TEMP                               = "temp";
    public static final String TEMP_MIN                           = "temp_min";
    public static final String TEMP_MAX                           = "temp_max";
    public static final String ICON                               = "icon";
    public static final String DESCRIPTION                        = "description";
    public static final String SYSTEM                             = "sys";
    public static final String MAIN                               = "main";
    public static final String WEATHER                            = "weather";

}
