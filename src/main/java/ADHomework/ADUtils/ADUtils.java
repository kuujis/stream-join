package ADHomework.ADUtils;

import ADHomework.ADEntities.*;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
            return "lineToADViewWithClick";
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

    public static synchronized void writeToCsv(Object o, StatefulBeanToCsv beanToCsv) {
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

    public static String[] updateFiles(String[] args) {
        String[] defaults = new String[]{"Views.csv",
                "Clicks.csv",
                "ViewableViews.csv",
                "ViewsWithClicks.csv",
                "FilteredViews.csv",
                "statistics.csv"};

        if (args.length < 3) {
            args = new String[]{"Views.csv",
                    "Clicks.csv",
                    "ViewableViews.csv",
                    "ViewsWithClicks.csv",
                    "FilteredViews.csv",
                    "statistics.csv"};
            System.out.printf("Serious case of lazy detected, defaulting to %s \n", Arrays.toString(args));
        }
        if (args.length < 6) {

            List<String> ar = new ArrayList<>();
            ar.addAll(Arrays.asList(args));
            switch (args.length){
                case 3:{
                    ar.add(defaults[3]);
                }
                case 4:{
                    ar.add(defaults[4]);
                }
                case 5:{
                    ar.add(defaults[5]);
                }
            }
            args = ar.toArray(new String[0]);

            System.out.printf("Lack of args detected, defaulting to %s \n", Arrays.toString(args));
        }
        return args;
    }
}
