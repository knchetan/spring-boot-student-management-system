package com.student.spring.util;

import java.sql.Date;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputUtil {
        private static final Logger logger = LoggerFactory.getLogger(InputUtil.class);
        private static Scanner scanner = new Scanner(System.in);

        /**
         * Prompts the user until a valid integer is provided.
         *
         * @return the integer value.
         */
        public static int getInteger()
        {
                String integerInput = scanner.nextLine();
                while (!Validator.isValidInteger(integerInput)) {
                        logger.info("Invalid input. Please enter a valid integer.");
                        integerInput = scanner.nextLine();
                }
                return Integer.parseInt(integerInput);
        }
            
        /**
         * Prompts the user until a valid non-empty string is provided.
         *
         * @return the validated string.
         */
        public static String getString()
        {
                String stringInput = scanner.nextLine();
                while (!Validator.isValidString(stringInput)) {
                        logger.info("Invalid input. Please enter a valid string.");
                        stringInput = scanner.nextLine();
                }
                return stringInput;
        }
            
        /**
         * Prompts the user until a valid phone number (10 digits) is provided.
         *
         * @return the validated phone number.
         */
        public static String getPhone()
        {
                String inputPhone = scanner.nextLine();
                while (!Validator.isValidPhone(inputPhone)) {
                        logger.info("Invalid phone number. Please enter 10 digits only.");
                        inputPhone = scanner.nextLine();
                }
                return inputPhone;
        }
            
        /**
         * Prompts the user until a valid email address is provided.
         *
         * @return the validated email address.
         */
        public static String getEmail()
        {
                String inputEmail = scanner.nextLine();
                while (!Validator.isValidEmail(inputEmail)) {
                        logger.info("Invalid email format. Please enter a valid email address (e.g. user@gmail.com).");
                        inputEmail = scanner.nextLine();
                }
                return inputEmail;
        }
            
        /**
         * Prompts the user until a valid date (yyyy-MM-dd) is provided.
         *
         * @return the validated Date.
         */
        public static Date getDate()
        {
                String inputDate = scanner.nextLine();
                Date date = Validator.parseDate(inputDate);
                while (date == null) {
                        logger.info("Invalid date format. Please enter a valid date (format: yyyy-MM-dd): ");
                        inputDate = scanner.nextLine();
                        date = Validator.parseDate(inputDate);
                }
                return date;
        }
}