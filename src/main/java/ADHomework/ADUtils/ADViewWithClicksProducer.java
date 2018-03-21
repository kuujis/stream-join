package ADHomework.ADUtils;

import ADHomework.ADEntities.ADClick;
import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;
import com.opencsv.bean.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ADViewWithClicksProducer {
    private final int timeWindow;
    private final int bufferSize;
    private final BufferedReader views;
    private final String clicks;
    private final String outputFile;
    private final ADAmateurishCache<ADClick> clCache;

    public ADViewWithClicksProducer(String[] args, int timeWindow, int bufferSize) throws IOException {
        this.timeWindow = timeWindow;
        this.bufferSize = bufferSize;
        this.views = Files.newBufferedReader(Paths.get(args[0]));
        this.clicks = args[1];
        this.outputFile = args.length <= 4 ? args[3] : "ViewsWithClicks.csv";
        this.clCache = new ADAmateurishCache<>(timeWindow, bufferSize);
    }

    public void generateViewsWithClicks() throws IOException {
        Writer writer = new FileWriter(outputFile);

        ADMappingStrategy<ADViewWithClick> strategy = new ADMappingStrategy<>();
        strategy.setType(ADViewWithClick.class);
        String[] header = {"id", "logTime", "clickId", "campaignId"};
        strategy.setColumnMapping(header);
        strategy.setHeader(header);

        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withMappingStrategy(strategy)
                .build();

        //Create ViewsWithClicks file
        getADViewsWithClicks(views.lines(), beanToCsv);

        writer.close();
    }

    public void getADViewsWithClicks(Stream<String> views, StatefulBeanToCsv beanToCsv) {

        views.map(ADUtils.lineToADView)
                .filter(adView -> adView != null)
                //TODO: need to filter all garbage then act on good adViews only
                .flatMap(adView -> includeClicks(adView))
                .filter(adViewWithClick -> adViewWithClick != null)
                .forEachOrdered(adViewWithClick ->  ADUtils.writeToCsv(adViewWithClick, beanToCsv));
    }

    private Stream<ADViewWithClick> includeClicks( ADView adView) {
        boolean cacheOK = this.clCache.isCacheOK(adView);
        if(!cacheOK) {
            this.clCache.refreshCache(adView, clicks, ADUtils.lineToADClick);
        }
        return this.clCache.cache().get()
                .filter(adClick -> adView.getId() == adClick.getInteractionId())
                .map(adClick -> new ADViewWithClick(adView.getId(), adView.getLogTime(), adClick.getId(), adView.getCampaignId()));
    }

}
