package com.system.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SysUtil {

	/**
     * Return the encrypted string based on the passed in string.
     * 
     * @param text
     * @return
     */
    public static String MD5Digest(String text) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
