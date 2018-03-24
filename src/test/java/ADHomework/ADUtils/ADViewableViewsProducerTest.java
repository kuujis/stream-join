package ADHomework.ADUtils;

import ADHomework.ADEntities.ADView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ADViewableViewsProducerTest {

    @Test
    void getMatchingVVs() throws IOException {
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

        ADViewableViewsProducer vvPrdcr = new ADViewableViewsProducer(args);

        vvPrdcr.generateViewableViews();

    }

    @Test
    void cacheForFirstadViewShouldContain3entries() throws IOException, ParseException {
//        adView
//        4564671159070995313,2018-02-22 00:03:05.815,1199166

//        ViewableViewsST2
//        151925403000315809,2018-02-22 00:05:07.216,4564671159070995313
//        151925403000630610,2018-02-22 00:08:13.869,3565493259205775418
//        151925412000204915,2018-02-22 00:14:34.500,7443884296972096163
//  NOT ->151927758000493436,2018-02-22 00:18:41.298,498587225004261687

        //only ViewableViewsST2 and FilteredViewsST2 (output) is important for this test
        int timeWindow = 300000;
        int bufferSize = 3;
         String[] args = new String[]{"/ViewsS.csv",
                "/ClicksS.csv",
                "/ViewableViewsST2.csv",
                "/ViewsWithClicksS.csv",
                "/FilteredViewsST2.csv",
                "/statisticsS.csv",
                 String.valueOf(timeWindow),
                 String.valueOf(bufferSize)};
        args = ADTestCommons.getTestFilesAbsolutePaths(this, args);
        ADViewableViewsProducer vvPrdcr = new ADViewableViewsProducer(args);

        assertTrue(vvPrdcr.isMatchingVVAvailable(new ADView("4564671159070995313,2018-02-22 00:03:05.815,1199166")));

        assertEquals(3, vvPrdcr.getAmCache().getCache().size());

    }

}