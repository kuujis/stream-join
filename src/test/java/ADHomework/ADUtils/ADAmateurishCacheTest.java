package ADHomework.ADUtils;

import ADHomework.ADEntities.ADIdLogTimedCmpgn;
import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewableView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

import static ADHomework.ADUtils.ADTestCommons.advanceTimeInStringBy;
import static org.junit.jupiter.api.Assertions.*;

class ADAmateurishCacheTest {

    @Test
    void testNewStreamReturnedBySupplier() {
        ADAmateurishCache<ADIdLogTimedCmpgn> cache = new ADAmateurishCache<>(300000, 3);

        assertNotSame(cache.getCache().stream(), cache.cache().get());
    }

    @Test
    void cacheIsOk() throws IOException, ParseException {
        int timeWindow = 300000;
        int bufferSize = 3;

        //only ViewableViewsST3 is important for this test
        String[] args = new String[]{"/ViewsS.csv",
                "/ClicksS.csv",
                "/ViewableViewsST3.csv",
                "/ViewsWithClicksS.csv",
                "/FilteredViewsST3.csv",
                "/statisticsS.csv",
                String.valueOf(timeWindow),
                String.valueOf(bufferSize)};

        args = ADTestCommons.getTestFilesAbsolutePaths(this, args);

        ADViewableViewsProducer vvPrdcr = new ADViewableViewsProducer(args);

        String firstADViewTime = "2018-02-22 00:03:01.000";
        String pointInTimeWindow = advanceTimeInStringBy(firstADViewTime, timeWindow / 2);
        String pointInTimeWinXBuffer = advanceTimeInStringBy(firstADViewTime, timeWindow * bufferSize / 2);
        String pointAfterTimeWinXBuffer = advanceTimeInStringBy(firstADViewTime, timeWindow * (bufferSize + 1));

        ADView beforeCacheInitialized = new ADView("4564671159070995313," + firstADViewTime + ",1199166");
        assertFalse(vvPrdcr.getAmCache().isCacheOK(beforeCacheInitialized));

        //vvCache is initialized during getMatchingVVs
        vvPrdcr.isMatchingVVAvailable(new ADView("4564671159070995313," + firstADViewTime + ",1199166"));

        //ADViews in different time situations
        ADView inTimeWindowAfterFirst = new ADView("4564671159070995313," + pointInTimeWindow + ",1199166");
        ADView inTimeWinXBufferAfterFirst = new ADView("4564671159070995313," + pointInTimeWinXBuffer + ",1199166");
        ADView beyondTimeWinXBufferWindowAfterFirst = new ADView("4564671159070995313," + pointAfterTimeWinXBuffer + ",1199166");

        assertTrue(vvPrdcr.getAmCache().isCacheOK(inTimeWindowAfterFirst));
        assertTrue(vvPrdcr.getAmCache().isCacheOK(inTimeWinXBufferAfterFirst));
        assertFalse(vvPrdcr.getAmCache().isCacheOK(beyondTimeWinXBufferWindowAfterFirst));

    }

    @Test
    void isTooOldTest() throws ParseException {

        ADAmateurishCache<ADIdLogTimedCmpgn> cache = new ADAmateurishCache<>(300000, 3);

        ADView adView = new ADView("4564671159070995313,2018-02-22 00:15:10.815,1199166");
        ADViewableView tooOldVV = new ADViewableView("151925403000315809,2018-02-22 00:00:09.216,4564671159070995313");
        ADViewableView oldButBearableVV = new ADViewableView("151925403000315809,2018-02-22 00:11:07.216,4564671159070995313");
        ADViewableView normalVV = new ADViewableView("151925403000315809,2018-02-22 00:20:07.216,4564671159070995313");

        assertTrue(cache.isTooOld(adView, tooOldVV));
        assertFalse(cache.isTooOld(adView, oldButBearableVV));
        assertFalse(cache.isTooOld(adView, normalVV));
    }

    @Test
    void isTooFarAheadTest() throws ParseException {

        ADAmateurishCache<ADIdLogTimedCmpgn> cache = new ADAmateurishCache<>(300000, 3);

        ADView adView = new ADView("4564671159070995313,2018-02-22 00:00:00.815,1199166");
        ADViewableView inTimeWindow = new ADViewableView("151925403000315809,2018-02-22 00:00:09.216,4564671159070995313");
        ADViewableView inTimeWindowXBuffer = new ADViewableView("151925403000315809,2018-02-22 00:14:00.216,4564671159070995313");
        ADViewableView beyondTimeWindowXBuffer = new ADViewableView("151925403000315809,2018-02-22 00:16:07.216,4564671159070995313");

        assertFalse(cache.isTooFarAhead(adView, inTimeWindow));
        assertFalse(cache.isTooFarAhead(adView, inTimeWindowXBuffer));
        assertTrue(cache.isTooFarAhead(adView, beyondTimeWindowXBuffer));
    }
}