package ADHomework.ADUtils;

import ADHomework.ADEntities.ADClick;
import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;

import java.io.BufferedReader;
import java.text.ParseException;
import java.util.List;
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
            }
            catch (NumberFormatException e) {
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
            }
            catch (NumberFormatException e) {
                System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADClick.\n", line);
                return null;
            }
        }
    };

    public static void checkFiles(String[] args) {

    }

    public static List<ADViewWithClick> getADViewsWithClicks(Stream<String> views, BufferedReader clicks) {
        return views.map(lineToADView)
                .filter(adViewWithClick -> adViewWithClick != null)
                .map((ADView adView) -> new ADViewWithClick(adView.getId(), adView.getLogTime(),
                        //click.id -> ADViewWithClick
                        clicks.lines()
                                .map(lineToADClick)
                                .filter(adClick -> adClick != null)
                                .filter(adClick -> adView.getId().equals(adClick.getInteractionId()))
                                .map(adClick -> adClick.getId())
                                .findFirst()
                                .orElse(null)))
                .filter(adcl -> (adcl.getId() != null & adcl.getLogTime() != null & adcl.getClickId() != null ) )
                .collect(Collectors.<ADViewWithClick>toList());
    }
}
