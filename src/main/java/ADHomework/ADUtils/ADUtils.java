package ADHomework.ADUtils;

import ADHomework.ADEntities.*;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.text.ParseException;
import java.util.Date;
import java.util.function.Function;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADUtils {
    public static Function<String, ADView> lineToADView = new Function<String, ADView>() {
        public synchronized ADView apply(String line) {
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
        @Override
        public String toString() {
            return "lineToADView";
        }
    };

    public static Function<String, ADViewWithClick> lineToADViewWithClick = new Function<String, ADViewWithClick>() {
        public synchronized ADViewWithClick apply(String line) {
            try {
                return new ADViewWithClick(line);
            } catch (ParseException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADView.\n", line);
                return null;
            } catch (NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADView.\n", line);
                return null;
            }
        }
        @Override
        public String toString() {
            return "lineToADView";
        }
    };

    public static Function<String, ADClick> lineToADClick = new Function<String, ADClick>() {
        public synchronized ADClick apply(String line) {
            try {
                return new ADClick(line);
            } catch (ParseException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADClick.\n", line);
            } catch (NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADClick.\n", line);
            }
            return null;
        }

        @Override
        public String toString() {
            return "lineToADClick";
        }
    };

    public static Function<String, ADViewableView> lineToADViewableView = new Function<String, ADViewableView>() {
        public synchronized ADViewableView apply(String line) {
            try {
                return new ADViewableView(line);
            } catch (ParseException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADViewableView.\n", line);
            } catch (NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADViewableView.\n", line);
            }
            return null;
        }
        @Override
        public String toString() {
            return "lineToADViewableView";
        }
    };

    public static void checkFiles(String[] args) {

    }

    public static synchronized void writeToCsv(ADIdLogTimed o, StatefulBeanToCsv beanToCsv) {
        try {
            beanToCsv.write(o);
        } catch (CsvDataTypeMismatchException dtm) {
            System.out.printf("Data type mismatch for %s \n", o.toString());
        } catch (CsvRequiredFieldEmptyException rfe) {
            System.out.printf("Required field missing for %s \n", o.toString());
        }
    }

    public static
    synchronized boolean logTimeInRange(Date timeToCheck, Date rangeStartPoint, Date rangeEndPoint) {
        boolean ret = rangeStartPoint.getTime() < timeToCheck.getTime() & timeToCheck.getTime() < rangeEndPoint.getTime();
        return ret;
    }
}
