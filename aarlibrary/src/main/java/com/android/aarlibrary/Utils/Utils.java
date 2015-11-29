package com.android.aarlibrary.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by HARSHAD on 29/11/2015.
 */
public class Utils {

    public static String getCurrentDateAndTime(){
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss").format(Calendar.getInstance().getTime());
        return formattedDate;
    }
}
