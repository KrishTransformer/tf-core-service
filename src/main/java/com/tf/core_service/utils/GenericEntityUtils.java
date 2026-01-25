package com.tf.core_service.utils;

public class GenericEntityUtils {

    public static String getDatabaseName (String input) {
        input = input.replace(".riverworld.io", "");
        input = input.replace("-admin", "");
        if (input.contains(".")) {
            input = input.replace(".", "-");
        }
        if (!input.contains("-")) {
            input = input + "-rw";
        }
        return input;
    }

    public static String getUsernameForSignIn (String input) {
        if (input != null && input.contains("@")) {
            input = input.replace("@","__at__");
        }
        return input;
    }

    public static String getUserDomainNameForSignIn (String input) {
        if (input != null) {
            input = getDatabaseName(input);
            if (input.contains("-")){
                input = input.replace("-", ".");
            }
        }
        return input;
    }

    public static String getEmailPrefix(String input) {
        if (input != null && input.contains("@")) {
            String[] inputArray = input.split("@");
            return inputArray[0];
        }
        return input;
    }


}
