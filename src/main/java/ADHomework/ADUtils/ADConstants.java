package ADHomework.ADUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADConstants {
    public static String separator = ",";
    public static DateFormat df = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss.SSS");
    public static Integer timeWindow = 50000;//in miliseconds
    public static Integer bufferSize = 100; //defines how much ADViewableViews should be put into cache

}
