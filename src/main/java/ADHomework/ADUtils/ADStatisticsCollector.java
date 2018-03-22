package ADHomework.ADUtils;

import ADHomework.ADEntities.ADIdLogTimedCmpgn;
import ADHomework.ADEntities.ADStatistic;
import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ADStatisticsCollector implements Collector<ADIdLogTimedCmpgn, ADStatistic, ADStatistic> {
    //    public interface Collector<T, A, R> {
//        Supplier<A> supplier();
//        BiConsumer<A, T> accumulator();
//        Function<A, R> finisher();
//        BinaryOperator<A> combiner();
//        Set<Characteristics> characteristics();
//    }
    private boolean collectingViews = true;

    private ADStatisticsCollector(boolean b) {
        super();
        collectingViews = b;
    }

    @Override
    public synchronized Supplier<ADStatistic> supplier() {
        return () -> {
            return new ADStatistic();
        };
    }

    @Override
    public synchronized BiConsumer<ADStatistic, ADIdLogTimedCmpgn> accumulator() {
        return (stat, obj) -> {
            if (stat.getCampaignId() == 0) {
                stat.setCampaignId(obj.getCampaignId());
            }

            if (obj instanceof ADView) {
                if (collectingViews) {
                    stat.setViews(stat.getViews() + 1);
                } else {
                    stat.setViewableViews(stat.getViewableViews() + 1);
                }
            }

            if (obj instanceof ADViewWithClick) {
                stat.setClicks(stat.getClicks() + 1);
            }

            if (stat.getViews() > 0 & stat.getClicks() > 0) {
                stat.setClickThrough(stat.getClicks() / stat.getViews());
            }

        };
    }

    @Override
    public synchronized BinaryOperator<ADStatistic> combiner() {
        return (stat1, stat2) -> {
            stat1.setViews(stat1.getViews() + stat2.getViews());
            stat1.setViewableViews(stat1.getViewableViews() + stat2.getViewableViews());

            stat1.setClicks(stat1.getClicks() + stat2.getClicks());

            if (stat1.getViews() > 0 & stat1.getClicks() > 0) {
                ADStatistic.refreshClickthrough(stat1);
            }

            return stat1;
        };
    }

    @Override
    public synchronized Function<ADStatistic, ADStatistic> finisher() {
        return Function.identity();
    }

    @Override
    public synchronized Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
    }

    public static synchronized ADStatisticsCollector statisticsCollector(Boolean b) {
        return new ADStatisticsCollector(b);
    }
}
