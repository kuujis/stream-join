package ADHomework.ADUtils;

import ADHomework.ADEntities.ADClick;
import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;
import ADHomework.ADEntities.ADViewableView;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ADUtilsTest {

    @Test
    void testLineToADView() throws ParseException, NumberFormatException {
        ADView view = ADUtils.lineToADView.apply("4564671159070995313,2018-02-22 00:03:05.815,1199166");

        assertEquals(4564671159070995313L,view.getId());
        assertEquals(ADConstants.df.parse("2018-02-22 00:03:05.815"), view.getLogTime());
        assertEquals(1199166, view.getCampaignId());

        assertNull(ADUtils.lineToADView.apply("4564671159070995313,201z8-09992-22222 00:03:05.815,1199166"));

        assertNull(ADUtils.lineToADView.apply("45646711590709953139999,2018-02-22222 00:03:05.815,1199166"));

        assertEquals("lineToADView",ADUtils.lineToADView.toString());
    }

    @Test
    void testLineToADClick() throws ParseException, NumberFormatException {
        ADClick click = ADUtils.lineToADClick.apply("151925412000204916,2018-02-22 00:14:35.388,1232120,4564671159070995313");

        assertEquals(151925412000204916L,click.getId());
        assertEquals(ADConstants.df.parse("2018-02-22 00:14:35.388"), click.getLogTime());
        assertEquals(1232120, click.getCampaignId());

        assertNull(ADUtils.lineToADClick.apply("151925412000204916,2ss018-02-22 00:14556:35.388,1232120,4564671159070995313"));

        assertNull(ADUtils.lineToADClick.apply("15192541200020491699999,2018-02-22 00:14:35.388,1232120,4564671159070995313"));

        assertEquals("lineToADClick",ADUtils.lineToADClick.toString());
    }


    @Test
    void testLineToADViewableView() throws ParseException, NumberFormatException {
        ADViewableView view = ADUtils.lineToADViewableView.apply("151925403000315809,2018-02-22 00:05:07.216,4564671159070995313");

        assertEquals(151925403000315809L,view.getId());
        assertEquals(ADConstants.df.parse("2018-02-22 00:05:07.216"), view.getLogTime());
        assertEquals(4564671159070995313L, view.getInteractionId());

        assertNull(ADUtils.lineToADViewableView.apply("151925403000315809,2018-02zx44-22 00:05:0744545.216,4564671159070995313"));

        assertNull(ADUtils.lineToADViewableView.apply("15192540300031580999999999,2018-02-22 00:05:07.216,4564671159070995313"));

        assertEquals("lineToADViewableView",ADUtils.lineToADViewableView.toString());
    }

    @Test
    void testLineToADViewWithClick() throws ParseException, NumberFormatException {
        ADViewWithClick view = ADUtils.lineToADViewWithClick.apply("\"4564671159070995313\",\"2018-02-22 00:03:05.815\",\"1199166\",\"151925412000204916\"");

        assertEquals(4564671159070995313L,view.getId());
        assertEquals(ADConstants.df.parse("2018-02-22 00:03:05.815"), view.getLogTime());
        assertEquals(1199166, view.getCampaignId());
        assertEquals(151925412000204916L, view.getClickId());

        assertNull(ADUtils.lineToADViewWithClick.apply("\"4564671159070995313\",\"2018-02-2eww2 00:03:05.815\",\"1199166\",\"151925412000204916\""));

        assertNull(ADUtils.lineToADViewWithClick.apply("\"4564671159070wooo995313\",\"2018-02-22 00:03:05.815\",\"1199166\",\"151925412000204916\""));

        assertEquals("lineToADViewWithClick",ADUtils.lineToADViewWithClick.toString());
    }

    @Test
    void testLogTimeInRange() throws ParseException {
        Date timeBefore = ADConstants.df.parse("2018-02-22 00:00:00.127");
        Date timeIn = ADConstants.df.parse("2018-02-22 00:05:00.127");
        Date timeAfter = ADConstants.df.parse("2018-02-22 00:10:00.127");

        Date startPoint = ADConstants.df.parse("2018-02-22 00:01:02.127");
        Date endPoint = ADConstants.df.parse("2018-02-22 00:09:02.127");

        assertFalse(ADUtils.logTimeInRange(timeBefore,startPoint,endPoint));
        assertTrue(ADUtils.logTimeInRange(timeIn, startPoint,endPoint));
        assertFalse(ADUtils.logTimeInRange(timeAfter, startPoint,endPoint));

    }

    @Test
    void updateFilesFullset() {

        String[] args = new String[]{"Views.csv",
                "Clicks.csv",
                "ViewableViews.csv",
                "ViewsWithClicks.csv",
                "FilteredViews.csv",
                "statistics.csv"};

        String[] updatedArgs = ADUtils.updateFiles(args);

        assertEquals(args, updatedArgs);
    }

    @Test
    void updateFilesPartialThings() {

        String[] partial = new String[]{"Views.csv",
                "Clicks.csv",
                "ViewableViews.csv"};

        String[] defaults = new String[]{"Views.csv",
                "Clicks.csv",
                "ViewableViews.csv",
                "ViewsWithClicks.csv",
                "FilteredViews.csv",
                "statistics.csv"};

        String[] updatedArgs = ADUtils.updateFiles(partial);

        assertEquals(defaults.length, updatedArgs.length);
        assertEquals(defaults[5], updatedArgs[5]);
    }

    @Test
    void updateCompleteLaziness() {

        String[] partial = new String[]{};

        String[] defaults = new String[]{"Views.csv",
                "Clicks.csv",
                "ViewableViews.csv",
                "ViewsWithClicks.csv",
                "FilteredViews.csv",
                "statistics.csv"};

        String[] updatedArgs = ADUtils.updateFiles(partial);

        assertEquals(defaults.length, updatedArgs.length);
        assertEquals(defaults[5], updatedArgs[5]);
    }

}