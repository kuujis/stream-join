package ADHomework.ADUtils;

import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewableView;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class ADViewableViewsProducer {

    public void generateViewableViews(String[] args) throws IOException {
        ;

        BufferedReader viewableViews = Files.newBufferedReader(Paths.get(args[2]));
        Supplier<Stream<String>> views = () -> {
            try {
                return Files.newBufferedReader(Paths.get(args[0])).lines();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        };

        Writer writer = new FileWriter("ViewsFiltered.csv");
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

        viewableViews.lines()
                .map(ADUtils.lineToADViewableView)
                .filter(adViewableView -> adViewableView != null)
                //.flatMap(getFilteredViews(Paths.get(args[0])))
                .flatMap(adViewableView -> views.get()
                        .map(ADUtils.lineToADView)
                        .takeWhile(adView -> adView.getLogTime().before(adViewableView.getLogTime()) &&
                                adView.getLogTime().getTime() - adViewableView.getLogTime().getTime() + ADConstants.timeWindow >= 0)
                        .filter(adView -> adView != null)
                        .filter(adView -> adViewableView.getInteractionId() == adView.getId())
                )
                .forEach(adView -> ADUtils.writeToCsv(beanToCsv));

        writer.close();
    }

    public void generateViewableViews2(String[] args) throws IOException {
        List<ADViewableView> vvList = Collections.synchronizedList(new ArrayList<ADViewableView>());
        BufferedReader views = Files.newBufferedReader(Paths.get(args[0]));
        Supplier<Stream<String>> viewableViews = () -> {
            try {
                return Files.newBufferedReader(Paths.get(args[2])).lines();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        };

        views.lines()
                .map(ADUtils.lineToADView)
                .filter(adView -> adView != null)
                //flatMap for possible ViewableViews
                .flatMap(adView -> viewableViews.get()
                        .map(ADUtils.lineToADViewableView)
                        //takeWhile vv.logTime <= adView.logTime + timewindow
                        .takeWhile(adVV -> ADUtils.logTimeInRange(adView.getLogTime(),adVV.getLogTime(),ADConstants.timeWindow)
                        )
                );
    }

    private Function<ADViewableView, Stream<? extends ADView>> getFilteredViews(Path file) {
        return adViewableView -> {
            BufferedReader views = null;
            try {
                views = Files.newBufferedReader(file);
                System.out.printf("Handling ViewableView.id %s \n", adViewableView.getId().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return views.lines()
                    .map(ADUtils.lineToADView)
                    .filter(adView -> adView != null)
                    .filter(adView -> adView.getLogTime().after(new Date(adViewableView.getLogTime().getTime() - ADConstants.timeWindow)) &&
                            adView.getLogTime().before(adViewableView.getLogTime()))
                    ;
        };
    }
}
