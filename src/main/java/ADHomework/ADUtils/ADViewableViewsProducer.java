package ADHomework.ADUtils;

import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewableView;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ADViewableViewsProducer {

    private List<ADViewableView> vvCache;
    private BufferedReader views;
    private StatefulBeanToCsv beanToCsv;

    public void setViewableViews(Supplier<Stream<String>> viewableViews) {
        this.viewableViews = viewableViews;
    }

    private Supplier<Stream<String>> viewableViews;

    public ADViewableViewsProducer(String[] args) throws IOException {
        this.vvCache = Collections.synchronizedList(new ArrayList<ADViewableView>());
        this.views = Files.newBufferedReader(Paths.get(args[0]));
        this.viewableViews = () -> {
            try {
                return Files.newBufferedReader(Paths.get(args[2])).lines();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        };

        Writer writer = new FileWriter(args.length == 4 ? args[3] : "FilteredViews.csv");
        this.beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

    }

    public void generateViewableViews() throws IOException {

        views.lines()
                .map(ADUtils.lineToADView)
                .filter(adView -> adView != null)
                .peek(adView -> System.out.printf("#flatening %s \n", adView.toString()))
                .flatMap(getMatchingVVs(this.getVvCache(), this.viewableViews))
                .forEach(adViewableView -> ADUtils.writeToCsv(this.beanToCsv));
    }

    public Function<ADView, Stream<? extends ADViewableView>> getMatchingVVs(List<ADViewableView> vvCache, Supplier<Stream<String>> viewableViews) {
        return (ADView adView) -> {
            //check if vvCache has suitable VV's:
            //adView.logTime + timeWindow * bufferSize < vvCache.max(logTime)
            boolean cacheOK = adView.getLogTime().getTime() + ADConstants.timeWindow <
                    this.vvCache.stream().map(vv -> vv.getLogTime().getTime()).reduce(Long::max).orElse(Long.MIN_VALUE);

            //if vvCache is NOT OK re-fill from file
            List<ADViewableView> matchingVVs;
            if (!cacheOK) {
                matchingVVs = viewableViews.get()
                        .map(ADUtils.lineToADViewableView)
                        .filter(adViewableView -> adViewableView != null)
                        .peek(adVV -> System.out.printf("##adVV %s \n", adVV.toString()))
                        .dropWhile(adVV -> dropVVsWhileTooOld(adView, adVV))
                        .peek(adVV -> System.out.printf("###past drop adView %s \n", adVV.toString()))
                        .takeWhile(adVV -> ADUtils.logTimeInRange(adView.getLogTime(),
                                //add new entries
                                new Date(adVV.getLogTime().getTime() + ADConstants.timeWindow * ADConstants.bufferSize),
                                ADConstants.timeWindow * ADConstants.bufferSize))
                        .peek(adVV -> System.out.printf("#### for collect adVV %s\n", adVV.toString()))
                        .collect(Collectors.toList());
                this.setVvCache(matchingVVs);
                System.out.printf("Refreshing cache for %s , #of matchingVVs %s \n", adView.getId(), matchingVVs.size());
            }
            return this.getVvCache().stream().filter(cachedVV -> cachedVV.getInteractionId() == adView.getId());
        };
    }

    public boolean dropVVsWhileTooOld(ADView adView, ADViewableView adVV) {
        return adView.getLogTime().getTime() < adVV.getLogTime().getTime() - ADConstants.timeWindow;
    }

    public List<ADViewableView> getVvCache() {
        return vvCache;
    }

    public void setVvCache(List<ADViewableView> vvCache) {
        this.vvCache = vvCache;
    }

    public Supplier<Stream<String>> getViewableViews() {
        return viewableViews;
    }
}
