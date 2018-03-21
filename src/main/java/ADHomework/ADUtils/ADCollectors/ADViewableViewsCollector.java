package ADHomework.ADUtils.ADCollectors;

import ADHomework.ADEntities.ADStatistic;
import ADHomework.ADEntities.ADViewableView;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ADViewableViewsCollector implements Collector<ADViewableView, ADStatistic, ADStatistic> {
//    public interface Collector<T, A, R> {
//        Supplier<A> supplier();
//        BiConsumer<A, T> accumulator();
//        Function<A, R> finisher();
//        BinaryOperator<A> combiner();
//        Set<Characteristics> characteristics();
//    }

    @Override
    public synchronized Supplier<ADStatistic> supplier() {
        return ADStatistic::new;
    }

    @Override
    public synchronized BiConsumer<ADStatistic, ADViewableView> accumulator() {
        return (stat, view) -> {
            if (stat.getCampaignId() == 0) {
                //TODO: nonsense here \/
                stat.setCampaignId((int) view.getId());
            }
            stat.setClicks(stat.getClicks() + 1);
        };
    }

    @Override
    public synchronized BinaryOperator<ADStatistic> combiner() {
        return (stat1, stat2) -> {
            stat1.setClicks(stat1.getClicks() + stat2.getClicks());
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

    public static synchronized ADViewableViewsCollector clicksCollector() {
        return new ADViewableViewsCollector();
    }
}
