package com.hanshu.utils;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

public class MyUtils {
    public static String cleanXSS(String value) {

        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\"'][\\s]*javascript:(.*)[\"']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

    public static HashMap<String, Object> backUpSession(HttpSession session) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Enumeration<String> enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            hashMap.put(name, session.getAttribute(name));
        }
        return hashMap;
    }

    public static String getExtName(String fileName) {
        String[] s = fileName.split("\\.");
        return s[s.length - 1];
    }
}
