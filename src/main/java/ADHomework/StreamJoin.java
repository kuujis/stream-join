package ADHomework;

import ADHomework.ADEntities.ADViewableView;
import ADHomework.ADUtils.ADStatisticsProducer;
import ADHomework.ADUtils.ADUtils;
import ADHomework.ADUtils.ADViewWithClicksProducer;
import ADHomework.ADUtils.ADViewableViewsProducer;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Kazys on 2018-03-10.
 */
public class StreamJoin {

    public static void main(String[] args) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        if (args.length < 6) {
            args = new String[]{"D:/Projects/stream-join/Views.csv",
                    "D:/Projects/stream-join/Clicks.csv",
                    "D:/Projects/stream-join/ViewableViews.csv",
                    "D:/Projects/stream-join/ViewsWithClicks.csv",
                    "D:/Projects/stream-join/FilteredViews.csv",
                    "D:/Projects/stream-join/statistics.csv"};
            System.out.printf("Serious lack of args detected, defaulting to %s \n", Arrays.toString(args));
        }
        //join Views with Clicks
        //ADViewWithClicksProducer clicks = new ADViewWithClicksProducer(args,300000, 3);
        //clicks.generateViewsWithClicks();

        //Join Views with ViewableViews
        //ADViewableViewsProducer prod = new ADViewableViewsProducer(args, 300000, 3);
        //prod.generateViewableViews();

        //Generate statistics
        ADStatisticsProducer stat = new ADStatisticsProducer(args);
        stat.generate();

    }

}
