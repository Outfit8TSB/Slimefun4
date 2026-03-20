package com.xzavier0722.mc.plugin.slimefun4.storage.util;

public class NumUtils {
    public static double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
