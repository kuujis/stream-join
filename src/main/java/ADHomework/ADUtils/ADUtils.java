package ADHomework.ADUtils;

import ADHomework.ADEntities.*;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.IOException;
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
    public static final Function<String, ADView> lineToADView = new Function<>() {
        public synchronized ADView apply(String line) {
            try {
                return new ADView(line);
            } catch (ParseException | NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADView.\n", line);
                return null;
            }
        }

        @Override
        public String toString() {
            return "lineToADView";
        }
    };

    public static final Function<String, ADViewWithClick> lineToADViewWithClick = new Function<>() {
        public synchronized ADViewWithClick apply(String line) {
            try {
                return new ADViewWithClick(line);
            } catch (ParseException | NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADView.\n", line);
                return null;
            }
        }

        @Override
        public String toString() {
            return "lineToADViewWithClick";
        }
    };

    public static final Function<String, ADClick> lineToADClick = new Function<>() {
        public synchronized ADClick apply(String line) {
            try {
                return new ADClick(line);
            } catch (ParseException | NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADClick.\n", line);
            }
            return null;
        }

        @Override
        public String toString() {
            return "lineToADClick";
        }
    };

    public static final Function<String, ADViewableView> lineToADViewableView = new Function<>() {
        public synchronized ADViewableView apply(String line) {
            try {
                return new ADViewableView(line);
            } catch (ParseException | NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADViewableView.\n", line);
            }
            return null;
        }

        @Override
        public String toString() {
            return "lineToADViewableView";
        }
    };


    @SuppressWarnings("unchecked")
    //otherwise - need to implement two methods for differnt Object types... not worth it.
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
        return rangeStartPoint.getTime() < timeToCheck.getTime() & timeToCheck.getTime() < rangeEndPoint.getTime();
    }

    public static String[] updateFiles(String[] args) throws IOException {
        String[] defaults = new String[]{"Views.csv",
                "Clicks.csv",
                "ViewableViews.csv",
                "ViewsWithClicks.csv",
                "FilteredViews.csv",
                "statistics.csv"};

        if (args.length < 3) {
            args = defaults;
            System.out.printf("Serious case of lazy detected, defaulting to %s \n", Arrays.toString(args));
        }

        checkFilesExist(args);

        if (args.length < 6) {

            List<String> ar = new ArrayList<>(Arrays.asList(args));
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

    static void checkFilesExist(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; i++ ) {
            File views = new File(args[i]);
            if(!views.exists()){
                sb.append("INPUT File " + args[i] + " could not be found.\n");
            }
        }
        if (!sb.toString().isEmpty()){
            throw new IOException(sb.toString());
        }
    }
}
