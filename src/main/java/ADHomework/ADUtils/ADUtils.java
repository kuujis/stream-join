package ADHomework.ADUtils;

import ADHomework.ADEntities.ADClick;
import ADHomework.ADEntities.ADIdLogTimed;
import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewableView;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.text.ParseException;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADUtils {
    public static Function<String, ADView> lineToADView = new Function<String, ADView>() {
        public ADView apply(String line) {
            try {
                return new ADView(line);
            } catch (ParseException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADView.\n", line);
                return null;
            } catch (NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADView.\n", line);
                return null;
            }
        }
    };

    public static Function<String, ADClick> lineToADClick = new Function<String, ADClick>() {
        public ADClick apply(String line) {
            try {
                return new ADClick(line);
            } catch (ParseException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADClick.\n", line);
            } catch (NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADClick.\n", line);
            }
            return null;
        }
    };

    public static Function<String, ADViewableView> lineToADViewableView = new Function<String, ADViewableView>() {
        public ADViewableView apply(String line) {
            try {
                return new ADViewableView(line);
            } catch (ParseException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADViewableView.\n", line);
            } catch (NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADViewableView.\n", line);
            }
            return null;
        }
    };

    public static void checkFiles(String[] args) {

    }


    public synchronized static Consumer<ADIdLogTimed> writeToCsv(StatefulBeanToCsv beanToCsv) {
        return o -> {
            try {
                beanToCsv.write(o);
            } catch (CsvDataTypeMismatchException dtm) {
                System.out.printf("Data type mismatch for %s \n", o.toString());
            } catch (CsvRequiredFieldEmptyException rfe) {
                System.out.printf("Required field missing for %s \n", o.toString());
            }
            ;
        };
    }

    public synchronized static boolean logTimeInRange(Date timeToCheck, Date rangePoint, long range) {
        boolean ret = timeToCheck.getTime() >= rangePoint.getTime() - range &&
                timeToCheck.getTime() <= rangePoint.getTime();
        return ret;
    }
}
