package ADHomework.ADUtils;

import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewableView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ADViewableViewsProducerTest {

    @Test
    void generateViewableViews() {
    }

    @Test
    void getMatchingVVs() throws IOException {
        String[] args = new String[]{"/ViewsS.csv", "/ClicksS.csv", "/ViewableViewsS.csv", "/FilteredViews.csv"};
        args = ADTestCommons.getTestFilesAbsolutePaths(this, args);

        ADViewableViewsProducer vvPrdcr = new ADViewableViewsProducer(args, 300000, 3);

        vvPrdcr.generateViewableViews();

    }

    @Test
    public void cacheForFirstadViewShouldContain3entries() throws IOException, ParseException {
//        adView
//        4564671159070995313,2018-02-22 00:03:05.815,1199166

//        VV's
//        151925403000315809,2018-02-22 00:05:07.216,4564671159070995313
//        151925403000630610,2018-02-22 00:08:13.869,3565493259205775418
//        151925412000204915,2018-02-22 00:14:34.500,7443884296972096163
//  NOT ->151927758000493436,2018-02-22 00:18:41.298,498587225004261687

        //only ViewableViewsST2 is important for this test
        String[] args = new String[]{"/ViewsS.csv", "/ClicksS.csv", "/ViewableViewsST2.csv", "/FilteredViewsT2.csv"};
        args = ADTestCommons.getTestFilesAbsolutePaths(this, args);
        ADViewableViewsProducer vvPrdcr = new ADViewableViewsProducer(args, 300000, 3);

        assertTrue(vvPrdcr.isMatchingVVAvailable(new ADView("4564671159070995313,2018-02-22 00:03:05.815,1199166")));

        assertEquals(3, vvPrdcr.getAmCache().getCache().size());

    }

}