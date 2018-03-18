package ADHomework;

import ADHomework.ADEntities.ADViewableView;
import ADHomework.ADUtils.ADUtils;
import ADHomework.ADUtils.ADViewWithClicksProducer;
import ADHomework.ADUtils.ADViewableViewsProducer;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Kazys on 2018-03-10.
 */
public class StreamJoin {

    public static void main(String[] args) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        ADUtils.checkFiles(args);

        try {
            //new ADViewWithClicksProducer().generateViewsWithClicks(args);
            ADViewableViewsProducer prod = new ADViewableViewsProducer(args);
            prod.generateViewableViews();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
