package com.art.cheric.util;

public class ColorUtils {

    public static String rgbToHex(float red, float green, float blue) {
        int r = Math.round(red);
        int g = Math.round(green);
        int b = Math.round(blue);
        return String.format("#%02x%02x%02x", r, g, b);
    }
}