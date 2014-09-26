package cn.focus.dc.focuswap.utils;

/**
 * @author qiaowang
 *
 */
public final class CacheUtils {
    private CacheUtils(){
    }

    public static String toKey(Object... params) {
        if (params == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < params.length - 1; i++) {
            builder.append(params[i]).append(":");
        }
        builder.append(params[params.length - 1]);
        return builder.toString();
    }
}
