package ADHomework.ADUtils;

import ADHomework.ADEntities.ADClick;
import ADHomework.ADEntities.ADView;

import java.text.ParseException;
import java.util.function.Function;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADUtils {
    public static Function<String, ADView> lineToADView = new Function<String, ADView>() {
        public ADView apply(String line) {
            try {
                return new ADView(line);
            } catch (ParseException e) {
                System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADView.", line);
                return null;
            }
        }
    };

    public static Function<String, ADClick> lineToADClick = new Function<String, ADClick>() {
        public ADClick apply(String line) {
            try {
                return new ADClick(line);
            } catch (ParseException e) {
                System.out.printf("Could not parse line \"%s\" to ADHomework.ADEntities.ADClick.", line);
                return null;
            }
        }
    };

    public static void checkFiles(String[] args) {

    }
}
