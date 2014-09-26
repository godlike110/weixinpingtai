package cn.focus.dc.focuswap.utils;

import java.security.MessageDigest;
import org.apache.commons.logging.Log;

/**
 * <p>MD5Util class.</p>
 *
 * @author xingtaoshi
 * @version $Id: $Id
 */
public class MD5Util {

    private static Log logger = org.apache.commons.logging.LogFactory.getLog(MD5Util.class);

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * <p>md5.</p>
     *
     * @param arg a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String md5(String arg) {
        if (arg == null) {
            arg = "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(arg.getBytes("UTF-8"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (null != md5) {
            return toHex(md5.digest());
        }
        return "";
    }
    
    /**
     * @param arg
     * @param charset
     * @return
     */
    public static String md5(String arg, String charset) {
        if (arg == null) {
            arg = "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(arg.getBytes(charset));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (null != md5) {
            return toHex(md5.digest());
        }
        return "";
    }


    private static String toHex(byte[] bytes) {
        StringBuffer str = new StringBuffer(32);
        for (byte b : bytes) {
            str.append(HEX_DIGITS[(b & 0xf0) >> 4]);
            str.append(HEX_DIGITS[(b & 0x0f)]);
        }
        return str.toString();
    }


    /**
     * <p>range.</p>
     *
     * @param value a {@link java.lang.Object} object.
     * @param max   a int.
     * @param min   a int.
     * @return a int.
     */
    public static int range(Object value, int max, int min) {
        if (value instanceof String) {
            return Math.min(max, Math.max(intval((String) value), min));
        } else {
            return Math.min(max, Math.max((Integer) value, min));
        }
    }

    /**
     * <p>intval.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @return a int.
     */
    public static int intval(String s) {
        return intval(s, 10);
    }

    /**
     * <p>intval.</p>
     *
     * @param s     a {@link java.lang.String} object.
     * @param radix a int.
     * @return a int.
     */
    public static int intval(String s, int radix) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (radix == 0) {
            radix = 10;
        } else if (radix < Character.MIN_RADIX) {
            return 0;
        } else if (radix > Character.MAX_RADIX) {
            return 0;
        }
        int result = 0;
        int i = 0, max = s.length();
        int limit;
        int multmin;
        int digit;
        boolean negative = false;
        if (s.charAt(0) == '-') {
            negative = true;
            limit = Integer.MIN_VALUE;
            i++;
        } else {
            limit = -Integer.MAX_VALUE;
        }
        if (i < max) {
            digit = Character.digit(s.charAt(i++), radix);
            if (digit < 0) {
                return 0;
            } else {
                result = -digit;
            }
        }
        multmin = limit / radix;
        while (i < max) {
            digit = Character.digit(s.charAt(i++), radix);
            if (digit < 0) {
                break;
            }
            if (result < multmin) {
                result = limit;
                break;
            }
            result *= radix;
            if (result < limit + digit) {
                result = limit;
                break;
            }
            result -= digit;
        }
        if (negative) {
            if (i > 1) {
                return result;
            } else {
                return 0;
            }
        } else {
            return -result;
        }
    }
}
