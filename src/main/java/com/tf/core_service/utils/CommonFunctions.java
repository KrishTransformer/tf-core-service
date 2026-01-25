package com.tf.core_service.utils;


import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonFunctions {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

    public static String[] decodeBasicAuthorizationToken(String authorization) {
        return decodeBasicAuthorizationToken(authorization,  2);
    }
    public static String[] decodeBasicAuthorizationToken(String authorization, int size) {
        String[] values = new String[size];
        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            values = credentials.split(":", size);
        }
        return values;
    }

    public static Map<String, String> splitQuery(String url) {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url;
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }
        return query_pairs;
    }

    public static String checkNullAndReturnString(Object object) {
        if (object != null) {
            return object.toString();
        } else {
            return "";
        }
    }

    public static String checkNullAndReturnNull(Object object) {
        if (object != null) {
            return object.toString();
        } else {
            return "Null";
        }
    }

    public static boolean checkNullOrEmptyString(String input) {
        if (input != null && !input.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static float checkNullAndReturnZero(Object object) {
        if (object != null) {
            try {
                Float value = Float.parseFloat(object.toString());
                if (!Float.isNaN(value)) {
                    return value;
                }
            } catch(Exception e) {
            }
        }
        return 0.0f;
    }

    public static String generateRandomString(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static double toDouble (Object object) {
        if (object != null) {
            return (double) object;
        } else {
            return 0.0;
        }
    }

    public static Integer toInteger (Object object) {
        if (object != null) {
            return (int) object;
        } else {
            return 0;
        }
    }

    public static String toString (Object object) {
        if (object != null) {
            return (String) object;
        } else {
            return "";
        }
    }
    public static Integer nullCheckReturnInteger (Integer input) {
        if (input != null) {
            return input;
        } else {
            return 0;
        }
    }

    public static Boolean nullCheckReturnBollean (Boolean input) {
        if (input != null) {
            return input;
        } else {
            return false;
        }
    }

    public static String nullCheckReturnEmpty (String input) {
        if (input != null) {
            return input;
        } else {
            return "";
        }
    }

    public static Double nullCheckReturnDouble (Double input) {
        if (input != null) {
            return input;
        } else {
            return 0.0;
        }
    }

    public static Boolean nullCheckReturnBooleanTrue(Boolean input){
        if (input!=null){
            return input;
        }else {
            return true;
        }
    }

}