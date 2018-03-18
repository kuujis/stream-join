package ADHomework.ADUtils;

import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ADViewWithClicksProducer {
    public void generateViewsWithClicks(String[] args) throws IOException {
        BufferedReader views = Files.newBufferedReader(Paths.get(args[0]));
        //BufferedReader clicks = Files.newBufferedReader(Paths.get(args[1]));
        Path clicks = Paths.get(args[1]);
        BufferedReader viewableClicks = Files.newBufferedReader(Paths.get(args[2]));

        Writer writer = new FileWriter("ViewsWithClicks.csv");
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

        //Create ViewsWithClicks file
        getADViewsWithClicks(views.lines(), clicks, beanToCsv);

        writer.close();
    }

    public void getADViewsWithClicks(Stream<String> views, Path clicksPath, StatefulBeanToCsv beanToCsv) {

        views.map(ADUtils.lineToADView)
                .filter(adView -> adView != null)
                //TODO: need to filter all garbage then act on good adViews only
                .flatMap(adView -> includeClicks(clicksPath, adView))
                .filter(adViewWithClick -> adViewWithClick.getClickId() != null)
                .forEach((Consumer<? super ADViewWithClick>) ADUtils.writeToCsv(beanToCsv));
    }

    private Stream<ADViewWithClick> includeClicks(Path clicksPath, ADView adView) {
        BufferedReader clicks = null;
        try {
            clicks = Files.newBufferedReader(clicksPath);
            System.out.printf("Handling ADView.id %s \n",adView.getId().toString());
            return clicks.lines()
                    .map(ADUtils.lineToADClick)
                    .filter(adClick -> adClick != null)
                    .filter(adClick -> adView.getId().compareTo(adClick.getInteractionId()) == 0)
                    .map(adClick -> new ADViewWithClick(adView.getId(), adView.getLogTime(), adClick.getId()));
        } catch (IOException e) {
            System.out.printf("Failed to create new reader for Path %s \n", clicksPath.toString());
        }
        return null;
    }

}
