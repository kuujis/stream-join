package ADHomework.ADUtils;

import ADHomework.ADEntities.*;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ADStatisticsCollectorTest {

    @Test
    void testAccumulatorCollectsViewsProperly() throws ParseException {
        //collecting views at first == true
        ADStatisticsCollector scForViews = ADStatisticsCollector.statisticsCollector(true);
        ADStatistic statView = scForViews.supplier().get();
        ADIdLogTimedCmpgn v = new ADView("5767776681449439088,2018-02-22 00:00:00.127,1221633");

        scForViews.accumulator().accept(statView, v);

        assertEquals(1, statView.getViews());
        assertEquals((Integer)1221633, statView.getCampaignId());
        assertEquals(0, statView.getViewableViews());
        assertEquals(0, statView.getClicks());

        //collecting filtered viewable views == false
        ADStatisticsCollector scForViewableViews = ADStatisticsCollector.statisticsCollector(false);
        ADStatistic statViewableView = scForViews.supplier().get();
        scForViewableViews.accumulator().accept(statViewableView, v);

        assertEquals(0, statViewableView.getViews());
        assertEquals((Integer)1221633, statView.getCampaignId());
        assertEquals(1, statViewableView.getViewableViews());
        assertEquals(0, statView.getClicks());
    }

    @Test
    void testAccumulatorCollectsClicksProperly() throws ParseException {
        ADStatisticsCollector scForClicks = ADStatisticsCollector.statisticsCollector(true);
        ADStatistic statClicks = scForClicks.supplier().get();
        ADIdLogTimedCmpgn v = new ADViewWithClick("\"7443884296972096163\",\"2018-02-22 00:01:26.639\",\"1232120\",\"151925412000204915\"");

        scForClicks.accumulator().accept(statClicks, v);

        assertEquals(1, statClicks.getClicks());
        assertEquals((Integer)1232120, statClicks.getCampaignId());
        assertEquals(0, statClicks.getViews());
        assertEquals(0, statClicks.getViewableViews());

    }

    @Test
    void combinerTestForADStatistic() {
        ADStatisticsCollector scForClicks = ADStatisticsCollector.statisticsCollector(true);
        ADStatistic stat1 = scForClicks.supplier().get();
        ADStatistic stat2 = scForClicks.supplier().get();

        assertNotSame(stat1,stat2);

        int campaignId = 1000;
        int views = 100;
        int clicks = 10;
        int viewableViews = 1;

        updateStatistic(stat1, campaignId, views, clicks, viewableViews);
        updateStatistic(stat2, campaignId, views, clicks, viewableViews);


        scForClicks.combiner().apply(stat1,stat2);
        ADStatistic.refreshClickthrough(stat1);

        assertEquals((Integer)campaignId, stat1.getCampaignId());
        assertEquals(views * 2, stat1.getViews());
        assertEquals(clicks * 2, stat1.getClicks());
        assertEquals(viewableViews * 2, stat1.getViewableViews());
        assertEquals((double)stat1.getClicks() / (double)stat1.getViews() * 100d, stat1.getClickThrough() );
    }

    private void updateStatistic(ADStatistic stat, int campaignId, int views, int clicks, int viewableViews) {
        stat.setCampaignId(campaignId);
        stat.setViews(views);
        stat.setClicks(clicks);
        stat.setViewableViews(viewableViews);
    }
}