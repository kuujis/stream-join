package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ADViewWithClickTest {

    @Test
    void testConstructor() throws ParseException {
        long id = 1L;
        long clickId = 2L;
        Date logTime = ADConstants.df.parse("2018-02-22 00:00:00.127");
        int campaignId = 22;

        ADViewWithClick adViewWithClick = new ADViewWithClick(id, logTime, clickId, campaignId);

        assertTrue(id == adViewWithClick.getId());
        assertTrue(clickId == adViewWithClick.getClickId());
        assertEquals(logTime, adViewWithClick.getLogTime());
        assertEquals(campaignId, adViewWithClick.getCampaignId());
    }

    private final String line0 = "\"id\",\"logTime\",\"clickId\",\"campaignId\"";
    private final String badIdLine = "\"bad number\",\"2018-02-22 00:01:26.639\",\"1232120\",\"151925412000204915\"";
    private final String badDateLine = "\"7443884296972096163\",\"Z018-02-99 00:01:26.639\",\"1232120\",\"151925412000204915\"";


    @Test
    void testExceptionsThrownOnBadLines() {

        assertThrows(NumberFormatException.class, () -> {
            ADViewWithClick adView = new ADViewWithClick(line0);
        });

        assertThrows(ParseException.class, () -> {
            ADViewWithClick adView = new ADViewWithClick(badDateLine);
        });

        assertThrows(NumberFormatException.class, () -> {
            ADViewWithClick adView = new ADViewWithClick(badIdLine);
        });

    }

    @Test
    void testStringConstructor() throws ParseException {

        String properLine = "\"7443884296972096163\",\"2018-02-22 00:01:26.639\",\"1232120\",\"151925412000204915\"";

        ADViewWithClick adViewWithClick = new ADViewWithClick(properLine);

        assertTrue(7443884296972096163L == adViewWithClick.getId());
        assertTrue(151925412000204915L == adViewWithClick.getClickId());
        assertEquals(ADConstants.df.parse("2018-02-22 00:01:26.639"), adViewWithClick.getLogTime());
        assertEquals(1232120, adViewWithClick.getCampaignId());
    }

    @Test
    void testToString() throws ParseException {
        ADViewWithClick vwc = new ADViewWithClick("\"7443884296972096163\",\"2018-02-22 00:01:26.639\",\"1232120\",\"151925412000204915\"");
        assertEquals("Id: 7443884296972096163 logTime: 2018-02-22 00:01:26.639 campaignId: 1232120 clickId: 151925412000204915",
                vwc.toString());
    }

}