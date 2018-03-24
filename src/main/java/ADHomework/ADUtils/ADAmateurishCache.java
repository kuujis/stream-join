package ADHomework.ADUtils;

import ADHomework.ADEntities.ADIdLogTimedCmpgn;
import ADHomework.ADEntities.ADView;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ADAmateurishCache<T extends ADIdLogTimedCmpgn> {
    private final List<T> cache;
    private final int timeWindow;
    private final int bufferSize;

    ADAmateurishCache(int timeWindow, int bufferSize) {
        this.cache = Collections.synchronizedList(new ArrayList<T>());
        this.timeWindow = timeWindow;
        this.bufferSize = bufferSize;
    }

    synchronized public Supplier<Stream<T>> cache() {
        return () -> new ArrayList<>(this.cache).stream();
    }

    synchronized public List<T> getCache() {
        return cache;
    }

    synchronized public boolean isCacheOK(ADView adView) {
        //vvCache is OK if it is not empty (which results in min_value)
        //and adView + timeWindow is before (fits in) max time in vvCache
        long maxTime = this.cache().get().parallel()
                //.peek(vv -> System.out.printf("**Element before reduce: %s \n", ADConstants.df.format(vv.getLogTime())))
                .map(vv -> vv.getLogTime().getTime())
                .reduce(Long::max)
                .orElse(Long.MAX_VALUE);
        //System.out.printf("**adView time: %s  +timeWindow, maxtime: %s \n", adView.getLogTime(), ADConstants.df.format(new Date(maxTime)));
        return adView.getLogTime().getTime() + this.timeWindow < maxTime & this.getCache().size() > 0;

    }

    private synchronized void reload(List<T> refreshedCache) {
        this.cache.clear();
        this.cache.addAll(refreshedCache);
    }

    synchronized public boolean isTooOld(ADView adView, T idLogTimed) {
        return adView.getLogTime().getTime() - this.timeWindow * this.bufferSize > idLogTimed.getLogTime().getTime();
    }

    synchronized public boolean isTooFarAhead(ADView adView, T idLogTimed) {
        return adView.getLogTime().getTime() + this.timeWindow * this.bufferSize < idLogTimed.getLogTime().getTime();
    }

    synchronized public void refreshCache(ADView adView, String source, Function<String, T> parsingFunction) {
        try {
            BufferedReader clicks = Files.newBufferedReader(Paths.get(source));
            //System.out.printf("Refreshing cache adView.id %s; Parser %s. \n", adView.getId(), parsingFunction.toString());
            List<T> refreshedCache = clicks.lines()
                    .map(parsingFunction)
                    .filter(Objects::nonNull)
                    .dropWhile(adClick -> this.isTooOld(adView, adClick))
                    //.peek(adVV -> System.out.printf("###past drop adVV %s \n", adVV.toString()))
                    .takeWhile(adClick -> !this.isTooFarAhead(adView, adClick))
                    //.map(adClick -> new ADViewWithClick(adView.getId(), adView.getLogTime(), adClick.getId()))
                    .collect(Collectors.toList());
            this.reload(refreshedCache);
        } catch (IOException e) {
            System.out.printf("Failed to create new reader for Path %s \n", source);
        }
    }
}
