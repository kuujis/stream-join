package ADHomework.ADUtils;

import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewableView;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ADViewableViewsProducer {

    private final String outputFile;
    private final String vvfile;
    private List<ADViewableView> vvCache;
    private BufferedReader views;
    private StatefulBeanToCsv beanToCsv;
    private Supplier<Stream<String>> viewableViews;

    public ADViewableViewsProducer(String[] args) throws IOException {
        this.vvCache = Collections.synchronizedList(new ArrayList<ADViewableView>());
        this.views = Files.newBufferedReader(Paths.get(args[0]));
        this.vvfile = args[2];
        this.outputFile = args.length == 4 ? args[3] : "FilteredViews.csv";
    }

    public void generateViewableViews() throws IOException {
        Writer writer = new FileWriter(this.outputFile);
        this.beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

        views.lines()
                .map(ADUtils.lineToADView)
                .filter(adView -> adView != null)
                //.peek(adView -> System.out.printf("#flatening %s \n", adView.toString()))
                .flatMap(adView -> getMatchingVVs(adView))
                //.peek(adView -> System.out.printf("#####will write %s \n", adView.toString()))
                .forEachOrdered(o -> ADUtils.writeToCsv(o, this.beanToCsv));

        writer.close();
    }

    public Stream<? extends ADViewableView> getMatchingVVs(ADView adView) {

        boolean cacheOK = isCacheOK(adView);
        System.out.printf("Cache is OK: %s \n", cacheOK);
        //if vvCache is NOT OK re-fill from file
        List<ADViewableView> matchingVVs;
        if (!cacheOK) {
            try {
                matchingVVs = Files.newBufferedReader(Paths.get(this.vvfile)).lines()
                        .map(ADUtils.lineToADViewableView)
                        .filter(adViewableView -> adViewableView != null)
                        //.peek(adVV -> System.out.printf("##adVV %s \n", adVV.toString()))
                        .dropWhile(adVV -> isTooOld(adView, adVV))
                        //.peek(adVV -> System.out.printf("###past drop adVV %s \n", adVV.toString()))
                        .takeWhile(adVV -> ADUtils.logTimeInRange(adView.getLogTime(),
                                new Date(adVV.getLogTime().getTime() + ADConstants.timeWindow * ADConstants.bufferSize),
                                ADConstants.timeWindow * (ADConstants.bufferSize + 1)))
                        //.peek(adVV -> System.out.printf("#### for collect adVV %s\n", adVV.toString()))
                        .collect(Collectors.toList());
                //TODO: consider adding one by one instead of en masse, no need to recreate vv stream maybe?
                this.getVvCache().clear();
                this.getVvCache().addAll(matchingVVs);
                System.out.printf("Refreshing cache for %s , size of cache %s \n", adView.getId(), matchingVVs.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getVvCacheStream().get().filter(cachedVV -> cachedVV.getInteractionId() == adView.getId());
    }

    private boolean isCacheOK(ADView adView) {
        //vvCache is OK if it is not empty (which results in min_value)
        //and there is at least one vv later than adView
        //and cache is young = adView - timeWindow
        long maxTime = getVvCacheStream().get().parallel()
                .peek(vv -> System.out.printf("**Element before reduce: %s \n", ADConstants.df.format(vv.getLogTime())))
                .map(vv -> vv.getLogTime().getTime())
                .reduce(Long::max)
                .orElse(Long.MIN_VALUE);
        long minTime = getVvCacheStream().get().parallel()
                .peek(vv -> System.out.printf("**Element before reduce: %s \n", ADConstants.df.format(vv.getLogTime())))
                .map(vv -> vv.getLogTime().getTime())
                .reduce(Long::min)
                .orElse(Long.MAX_VALUE);
        System.out.printf("**Element before reduce: %s \n", maxTime);
        return minTime < adView.getLogTime().getTime() - ADConstants.timeWindow ^ adView.getLogTime().getTime() < maxTime;

    }

    private Supplier<Stream<ADViewableView>> getVvCacheStream() {
        return () -> {
            //must supply new object
            return new ArrayList<ADViewableView>(this.getVvCache()).stream();
        };
    }

    public boolean isTooOld(ADView adView, ADViewableView adVV) {
        // return true if adVV is older than adView
        return adView.getLogTime().getTime() > adVV.getLogTime().getTime();
    }

    public List<ADViewableView> getVvCache() {
        return vvCache;
    }

    public void setVvCache(List<ADViewableView> vvCache) {
        this.vvCache = vvCache;
    }

    public Supplier<Stream<String>> getViewableViews() {
        return () -> {
            try {
                return Files.newBufferedReader(Paths.get(this.vvfile)).lines();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    public void setViewableViews(Supplier<Stream<String>> viewableViews) {
        this.viewableViews = viewableViews;
    }
}
