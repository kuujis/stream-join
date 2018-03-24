package ADHomework.ADUtils;

import ADHomework.ADEntities.ADStatistic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ADStatisticsProducerTest {

    private ADStatisticsProducer sp = null;
    private ADViewWithClicksProducer vwClicks = null;
    private ADViewableViewsProducer vvS = null;

    @BeforeAll
    void onSetup() throws IOException {
        int timeWindow = 300000;
        int bufferSize = 3;
        String[] args = new String[]{"/ViewsS.csv",
                "/ClicksS.csv",
                "/ViewableViewsS.csv",
                "/ViewsWithClicksS.csv",
                "/FilteredViewsS.csv",
                "/statisticsS.csv",
                String.valueOf(timeWindow),
                String.valueOf(bufferSize)};

        args = ADTestCommons.getTestFilesAbsolutePaths(this, args);
        sp = new ADStatisticsProducer(args);
        vwClicks = new ADViewWithClicksProducer(args);
        vvS = new ADViewableViewsProducer(args);
    }

    @Test
    void testConstructor() {

        assertTrue(sp.stats != null);

    }

    @Test
    void generate() throws IOException {
        vwClicks.generateViewsWithClicks();

        vvS.generateViewableViews();

        sp.generate();

        //get 1199166 entry and check that it is according to the test resource data
        ADStatistic stat = sp.stats.get(1199166);
        assertNotNull(stat);
        assertEquals(7, stat.getViews());
        assertEquals(5, stat.getClicks());
        assertEquals(4, stat.getViewableViews());
        assertEquals(5d / 7d * 100d, stat.getClickThrough());

    }

    @Test
    void printStats() {
        //TODO: implement?
    }
}