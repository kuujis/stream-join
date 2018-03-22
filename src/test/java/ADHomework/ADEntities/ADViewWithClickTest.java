package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import ADHomework.ADUtils.ADUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ADViewWithClickTest {

    @Test
    public void testConstructor() throws ParseException {
        long id = 1l;
        long clickId = 2l;
        Date logTime = ADConstants.df.parse("2018-02-22 00:00:00.127");
        int campaignId = 22;

        ADViewWithClick adViewWithClick = new ADViewWithClick(id, logTime, clickId, campaignId);

        assertTrue(id == adViewWithClick.getId());
        assertTrue(clickId  == adViewWithClick.getClickId());
        assertEquals(logTime, adViewWithClick.getLogTime());
        assertEquals(campaignId, adViewWithClick.getCampaignId());
    }

    private String line0 = "\"id\",\"logTime\",\"clickId\",\"campaignId\"";
    private String badIdLine = "\"bad number\",\"2018-02-22 00:01:26.639\",\"1232120\",\"151925412000204915\"";
    private String badDateLline = "\"bad number\",\"Z018-02-99 00:01:26.639\",\"1232120\",\"151925412000204915\"";


    @Test
    public void testExceptionsThrownOnBadLines() throws ParseException {

        assertThrows(ParseException.class,() -> {
            ADViewWithClick adView = new ADViewWithClick(line0);
        });

        assertThrows(ParseException.class,() -> {
            ADViewWithClick adView = new ADViewWithClick(badDateLline);
        });

        assertThrows(NumberFormatException.class,() -> {
            ADViewWithClick adView = new ADViewWithClick(badIdLine);
        });

    }

    @Test
    public void testStringConstructor() throws ParseException {

        String properLine = "\"7443884296972096163\",\"2018-02-22 00:01:26.639\",\"1232120\",\"151925412000204915\"";

        ADViewWithClick adViewWithClick = new ADViewWithClick(properLine);

        assertTrue(7443884296972096163l == adViewWithClick.getId());
        assertTrue(151925412000204915l  == adViewWithClick.getClickId());
        assertEquals(ADConstants.df.parse("2018-02-22 00:01:26.639"), adViewWithClick.getLogTime());
        assertEquals(1232120, adViewWithClick.getCampaignId());
    }

}