package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Created by Kazys on 2018-03-10.
 */
class ADViewTest {

    @Test // (expected = ParseException.class)
    void testExceptionsThrownOnBadLines() {
        String line0 = "id,logtime,campaignid";
        String badIdLine = "\"5767776681449439088999\",2018-02-22 00:00:00.127,1221633";

        assertThrows(ParseException.class,() -> {
            ADView adView = new ADView(line0);
        });

        assertThrows(NumberFormatException.class,() -> {
            ADView adView = new ADView(badIdLine);
        });

    }

    @Test
    void testObjectCreatedWithProperValues() throws ParseException {
        String properLine = "5767776681449439088,2018-02-22 00:00:00.127,1221633";
        ADView adView = new ADView(properLine);
        assertEquals(1221633, adView.getCampaignId());
        assertEquals(5767776681449439088L, adView.getId());
        assertEquals(ADConstants.df.parse("2018-02-22 00:00:00.127"),adView.getLogTime());
    }
}