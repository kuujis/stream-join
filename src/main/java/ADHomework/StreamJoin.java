package ADHomework;

import ADHomework.ADUtils.ADStatisticsProducer;
import ADHomework.ADUtils.ADUtils;
import ADHomework.ADUtils.ADViewWithClicksProducer;
import ADHomework.ADUtils.ADViewableViewsProducer;

import java.io.IOException;

/**
 * Created by Kazys on 2018-03-10.
 */
class StreamJoin {

    /**
     * @param args - expected list of paths to files to work with:
     *             Views.csv - input
     *             Clicks.csv - input
     *             ViewableViews.csv - input
     *             ViewsWithClicks.csv - output - views joined with clicks
     *             FilteredViews.csv - output - views filtered if they were viewable
     *             statistics.csv - output - statistics
     *             timeWindow - input - time in milliseconds to check for matching Clicks and ViewableViews
     *             bufferSize - input - multiplier for determining how many timeWindows to include in ADAmateurishCache
     */
    public static void main(String[] args) {

        try {
            args = ADUtils.updateFiles(args);

            //join Views with Clicks
            ADViewWithClicksProducer clicks = new ADViewWithClicksProducer(args);
            clicks.generateViewsWithClicks();

            //Join Views with ViewableViews
            ADViewableViewsProducer prod = new ADViewableViewsProducer(args);
            prod.generateViewableViews();

            //Generate statistics
            ADStatisticsProducer stat = new ADStatisticsProducer(args);
            stat.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
