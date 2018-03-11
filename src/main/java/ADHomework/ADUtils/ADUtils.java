package ADHomework.ADUtils;

import ADHomework.ADEntities.ADClick;
import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
                System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADClick.\n", line);
                return null;
            } catch (NumberFormatException e) {
                System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADClick.\n", line);
                return null;
            }
        }
    };

    public static void checkFiles(String[] args) {

    }

    public static void getADViewsWithClicks(Stream<String> views, Path clicksPath, StatefulBeanToCsv beanToCsv) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        views.map(lineToADView)
                .filter(adView -> adView != null)
                //TODO: need to filter all garbage then act on good adViews only
                .map(enrichADView(clicksPath))
                .filter(adViewWithClick -> adViewWithClick.getClickId() != null)
                .forEach(writeToCsv(beanToCsv));
    }

    private static Function<ADView, ADViewWithClick> enrichADView(Path clicks) {
        return adView -> new ADViewWithClick(adView.getId(), adView.getLogTime(), getClickId(clicks, adView.getId()));
    }

    private static Long getClickId(Path clicksPath, Long viewId)  {
        BufferedReader clicks = null;
        try {
            clicks = Files.newBufferedReader(clicksPath);
        } catch (IOException e) {
            System.out.printf("Failed to create new reader for Path %s \n", clicksPath.toString());
        }
        return clicks.lines()
                .map(lineToADClick)
                .filter(adClick -> adClick != null)
                .map(adClick -> adClick.getInteractionId())
                //.filter(compareLongWithMessedUpScale(viewId))
                .filter(interactionId -> {
                    if (interactionId == 4781245516087271927l) {System.out.printf("clickId %s viewId %s \n", interactionId, viewId);} //TODO: delete this later
                    return interactionId == viewId;
                })
                .findFirst()
                .orElse(null);
    }

    private static Predicate<Long> compareLongWithMessedUpScale(Long id) {
        return intId -> (new BigDecimal(id / 10000)).setScale(0, RoundingMode.UP)
                .compareTo(new BigDecimal(intId / 10000).setScale(0, RoundingMode.UP)) == 0;
    }

    private static Consumer<ADViewWithClick> writeToCsv(StatefulBeanToCsv beanToCsv) {
        return o -> {
            try {
                beanToCsv.write(o);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            ;
        };
    }
}
