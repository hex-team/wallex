package com.hex.abstractandroidutils.tools;

import java.util.regex.Pattern;

/**
 * Created by alireza on 3/13/17.
 */

public class StringUtils {

    public static String convertToFarsi(String number) {
        char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<number.length();i++)
        {
            if(Character.isDigit(number.charAt(i)))
            {
                builder.append(arabicChars[(int)(number.charAt(i))-48]);
            }
            else
            {
                builder.append(number.charAt(i));
            }
        }
        return builder.toString();
    }



    public static boolean isValidEmail(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }



}
