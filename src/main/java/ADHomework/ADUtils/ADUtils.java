package ADHomework.ADUtils;

import ADHomework.ADEntities.ADClick;
import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADUtils {
    public static Function<String, ADView> lineToADView = new Function<String, ADView>() {
        public ADView apply(String line) {
            try {
                return new ADView(line);
            } catch (ParseException e) {
                System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADView.\n", line);
                return null;
            } catch (NumberFormatException e) {
                System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADView.\n", line);
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
                return null;
            } catch (NumberFormatException e) {
                //System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADClick.\n", line);
                return null;
            }
        }
    };

    public static void checkFiles(String[] args) {

    }

    public static void getADViewsWithClicks(Stream<String> views, Path clicksPath, StatefulBeanToCsv beanToCsv) {

        views.map(lineToADView)
                .filter(adView -> adView != null)
                //TODO: need to filter all garbage then act on good adViews only
                .flatMap(adView -> includeClicks(clicksPath, adView))
                .filter(adViewWithClick -> adViewWithClick.getClickId() != null)
                .forEach(writeToCsv(beanToCsv));
    }

    private static Stream<ADViewWithClick> includeClicks(Path clicksPath, ADView adView) {
        BufferedReader clicks = null;
        try {
            clicks = Files.newBufferedReader(clicksPath);
            System.out.printf("Handling ADView.id %s \n",adView.getId().toString());
            return clicks.lines()
                    .map(lineToADClick)
                    .filter(adClick -> adClick != null)
                    .filter(adClick -> adView.getId().compareTo(adClick.getInteractionId()) == 0)
                    .map(adClick -> new ADViewWithClick(adView.getId(), adView.getLogTime(), adClick.getId()));
        } catch (IOException e) {
            System.out.printf("Failed to create new reader for Path %s \n", clicksPath.toString());
        }
        return null;
    }

    private static Consumer<ADViewWithClick> writeToCsv(StatefulBeanToCsv beanToCsv) {
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
}
