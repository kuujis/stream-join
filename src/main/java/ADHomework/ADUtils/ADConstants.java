package ADHomework.ADUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADConstants {
    public static String separator = ",";
    public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static Integer timeWindow = 200000;//in miliseconds, max difference found by hand - ~160000
    public static Integer bufferSize = 2; //defines how much ADViewableViews should be put into cache

}
