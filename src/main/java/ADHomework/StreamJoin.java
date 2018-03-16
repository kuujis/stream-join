package ADHomework;

import ADHomework.ADUtils.ADUtils;
import ADHomework.ADUtils.ADViewWithClicksProducer;
import ADHomework.ADUtils.ADViewableViewsProducer;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;

/**
 * Created by Kazys on 2018-03-10.
 */
public class StreamJoin {

    public static void main(String[] args) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        ADUtils.checkFiles(args);

        try {
            //new ADViewWithClicksProducer().generateViewsWithClicks(args);
            new ADViewableViewsProducer().generateViewableViews(args);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
