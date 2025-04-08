package com.student.spring.util;

import java.sql.Date;

/**
 *    This class provides a collection of static methods for validating user inputs.
 */

public class Validator 
{

    /**
     * This method validates whether the input string is an integer.
     *
     * @param input
     *        The input string.
     * @return
     *        true if the input is a valid integer, false otherwise.
     */

    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch(NumberFormatException se) {
            return false;
        }
    }

    
    /**
     * This method validates that the input string is not null or empty.
     *
     * @param input
     *        The input string.
     * @return
     *        true if the input is non-empty, false otherwise.
     */

    public static boolean isValidString(String input) 
    {
        return input != null && !input.trim().isEmpty();
    }
    

    /**
     * 	This method validates that the phone number contains only digits with an optional '+' at the beginning.
     *
     * 	@param phone
     *        The phone number input.
     * 	@return
     *        true if the phone number is valid, false otherwise.
     */

    public static boolean isValidPhone(String phone) {
        String validatedPhone = phone.startsWith("+") ? phone.substring(1) : phone;
        return validatedPhone.matches("^[0-9]{10}$");
    }

    

    /**
     * 	This method validates that the email follows a basic email format (e.g., user@gmail.com, user@yahoo.com).
     *
     * 	@param email
     *        The email input.
     * 	@return
     *        true if the email is valid, false otherwise.
     */

    public static boolean isValidEmail(String email) 
    {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
    

    /**
     * 	This method validates that the input string is in yyyy-MM-dd format.
     *
     * 	@param dateStr
     *        The date input.
     * 	@return
     *        A validated date, null otherwise.
     */

    public static Date parseDate(String dateStr) {
        if (dateStr == null || !dateStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return null;
        }
        String[] field = dateStr.split("-");
        int year, month, day;
        try {
            year = Integer.parseInt(field[0]);
            month = Integer.parseInt(field[1]);
            day = Integer.parseInt(field[2]);
        } catch (NumberFormatException se) {
            return null;
        }
        
        // Validate month.
        if (month < 1 || month > 12) {
            return null;
        }
        
        // Determine the maximum day for the month.
        int maxDay;
        switch (month) {
            case 2:
                // Check for leap year.
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    maxDay = 29;
                } else {
                    maxDay = 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDay = 30;
                break;
            default:
                maxDay = 31;
        }
        
        // Validate day.
        if (day < 1 || day > maxDay) {
            return null;
        }
        
        try {
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            java.util.Date utilDate = format.parse(dateStr);
            return new java.sql.Date(utilDate.getTime());
        } catch (java.text.ParseException se) {
            return null;
        }
    }
}
