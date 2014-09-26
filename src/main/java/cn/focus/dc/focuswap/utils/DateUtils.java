package cn.focus.dc.focuswap.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA. User: yunguangwang Date: 13-9-7 Time: 下午12:09 To change this template use File | Settings
 * | File Templates.
 */
public final class DateUtils {

    private static Log logger = LogFactory.getLog(DateUtils.class);

    private DateUtils() {
        
    }
    
    /**
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
     * 
     * @param date String 想要格式化的日期
     * @param oldPattern String 想要格式化的日期的现有格式
     * @param newPattern String 想要格式化成什么格式
     * @return String
     */
    public static String stringPattern(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null) {
            return "";
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern);
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);
        Date d = null;
        try {
            d = sdf1.parse(date);
        } catch (Exception e) {
            logger.error("", e);
        }
        return sdf2.format(d);
    }
    
    /**
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
     * 
     * @param date String 想要格式化的日期
     * @param oldPattern String 想要格式化的日期的现有格式
     * @param newPattern String 想要格式化成什么格式
     * @return String
     * @throws ParseException 
     */
    public static String stringPatternThrow(String date, String oldPattern, String newPattern) throws ParseException {
        if (date == null || oldPattern == null || newPattern == null) {
            return "";
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern);
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);
        Date d = null;
        d = sdf1.parse(date);

        return sdf2.format(d);
    }

    /**
     * 判断参数是否为本月日期
     * 
     * @param long 想要格式化的日期毫秒
     * @return String 满足返回日期,不满足返回""
     */
    public static String currMonth(long times) {
        String result = "";
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = ca.getTime();
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = ca.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (firstDate.getTime() <= times && times <= lastDate.getTime()) {
            Date date = new Date(times);
            result = sdf.format(date);
        }
        return result;
    }

    /**
    * 获得当前的时间戳，单位：秒
    * @return
    */
   public static int getTimeStamp(){
       return (int)(System.currentTimeMillis()/1000);
   }
   
   /**
    * 获取当前时间与第二天零点时间差，单位:秒
    */
   public static int getTodayRestTimeStamp(){
       Calendar curDate = Calendar.getInstance();
       Calendar tommorowDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),
       curDate.get(Calendar.DATE) + 1, 0, 0, 0);
       return (int)(tommorowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
   }
}
