package ADHomework.ADEntities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ADStatisticTest {

    @Test
    void refreshClickthrough() {

        ADStatistic stat = new ADStatistic();
        stat.setViews(0);
        stat.setClicks(0);
        ADStatistic.refreshClickthrough(stat);
        assertEquals(0d, stat.getClickThrough());

        stat.setViews(0);
        stat.setClicks(1);
        ADStatistic.refreshClickthrough(stat);
        assertEquals(0d, stat.getClickThrough());

        stat.setViews(1);
        stat.setClicks(0);
        ADStatistic.refreshClickthrough(stat);
        assertEquals(0d, stat.getClickThrough());

        stat.setViews(1);
        stat.setClicks(1);
        ADStatistic.refreshClickthrough(stat);
        assertEquals(100d, stat.getClickThrough());

    }
}