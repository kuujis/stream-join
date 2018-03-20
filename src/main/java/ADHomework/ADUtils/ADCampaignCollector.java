package ADHomework.ADUtils;

import ADHomework.ADEntities.ADStatistic;
import ADHomework.ADEntities.ADView;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CampaignCollector implements Collector<ADView, ADStatistic, ADStatistic> {
//    public interface Collector<T, A, R> {
//        Supplier<A> supplier();
//        BiConsumer<A, T> accumulator();
//        Function<A, R> finisher();
//        BinaryOperator<A> combiner();
//        Set<Characteristics> characteristics();
//    }

    @Override
    public Supplier<ADStatistic> supplier() {
        return ADStatistic::new;
    }

    @Override
    public BiConsumer<ADStatistic, ADView> accumulator() {
        return (stat, view) -> {
            if (stat.getCampaignId() == 0) {
                stat.setCampaignId(view.getCampaignId());
            }
            stat.setViews(stat.getViews() + 1);
        };
    }

    @Override
    public BinaryOperator<ADStatistic> combiner() {
        return (stat1, stat2) -> {
            stat1.setViews(stat1.getViews() + stat2.getViews());
            return stat1;
        };
    }

    @Override
    public Function<ADStatistic, ADStatistic> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
    }
}
