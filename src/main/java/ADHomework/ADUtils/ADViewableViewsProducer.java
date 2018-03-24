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
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ADViewableViewsProducer {

    private String outputFile;
    private String vvfile;
    private BufferedReader views;
    private StatefulBeanToCsv<ADView> beanToCsv;
    private final ADAmateurishCache<ADViewableView> amCache;

    public ADViewableViewsProducer(String[] args) throws IOException {
        this.views = Files.newBufferedReader(Paths.get(args[0]));
        this.vvfile = args[2];
        this.outputFile = args[4];
        this.amCache = new ADAmateurishCache<>(Integer.parseInt(args[6]), Integer.parseInt(args[7]));
    }

    public void generateViewableViews() throws IOException {
        Writer writer = new FileWriter(this.outputFile);

        ADMappingStrategy<ADView> strategy = new ADMappingStrategy<>();
        strategy.setType(ADView.class);
        String[] columnMapping = {"id", "logTime", "campaignId"};
        strategy.setColumnMapping(columnMapping);
        strategy.setHeader(columnMapping);

        this.beanToCsv = new StatefulBeanToCsvBuilder<ADView>(writer)
                .withMappingStrategy(strategy)
                .build();

        views.lines()
                .map(ADUtils.lineToADView)
                .filter(Objects::nonNull)
                .filter(this::isMatchingVVAvailable)
                .forEachOrdered(o -> ADUtils.writeToCsv(o, this.beanToCsv));

        System.out.printf("Views with clicks written to %s \n", outputFile);

        writer.close();
    }

    public boolean isMatchingVVAvailable(ADView adView) {
        boolean cacheOK = this.amCache.isCacheOK(adView);
        //System.out.printf("Cache is OK?: %s for %s \n", cacheOK, adView);
        //if vvCache is NOT OK re-fill from file
        if (!cacheOK) {
            amCache.refreshCache(adView, this.vvfile, ADUtils.lineToADViewableView);
        }
        //System.out.printf("Returning for adView %s , cacheOk: %s , size of cache %s \n", adView, cacheOK, this.amCache.getCache().size());
        return this.amCache.cache().get()
                .filter(cachedVV -> cachedVV.getInteractionId() == adView.getId()).
                collect(Collectors.toList()).size() > 0;
    }

    public ADAmateurishCache<ADViewableView> getAmCache() {
        return amCache;
    }
}
