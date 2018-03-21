package ADHomework.ADUtils.ADCollectors;

import ADHomework.ADEntities.ADStatistic;
import ADHomework.ADEntities.ADView;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ADViewsCollector implements Collector<ADView, ADStatistic, ADStatistic> {
    //    public interface Collector<T, A, R> {
//        Supplier<A> supplier();
//        BiConsumer<A, T> accumulator();
//        Function<A, R> finisher();
//        BinaryOperator<A> combiner();
//        Set<Characteristics> characteristics();
//    }
    private boolean collectingViews = true;

    private ADViewsCollector(boolean b) {
        super();
        collectingViews = b;
    }

    @Override
    public synchronized Supplier<ADStatistic> supplier() {
        return ADStatistic::new;
    }

    @Override
    public synchronized BiConsumer<ADStatistic, ADView> accumulator() {
        return (stat, view) -> {
            if (stat.getCampaignId() == 0) {
                stat.setCampaignId(view.getCampaignId());
            }
            if (collectingViews) {
                stat.setViews(stat.getViews() + 1);
            } else {
                stat.setViewableViews(stat.getViewableViews() + 1);
            }

        };
    }

    @Override
    public synchronized BinaryOperator<ADStatistic> combiner() {
        return (stat1, stat2) -> {
            if (collectingViews) {
                stat1.setViews(stat1.getViews() + stat2.getViews());
            } else {
                stat1.setViewableViews(stat1.getViewableViews() + stat2.getViewableViews());
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

    public static synchronized ADViewsCollector campaignCollector(boolean b) {
        return new ADViewsCollector(b);
    }
}
