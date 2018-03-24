package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Created by Kazys on 2018-03-10.
 */

class ADClickTest {
    private final String badIdLine = "bad id,2018-02-22 00:01:34.388,1232120,7443884296972096163";
    private final String badDateLine = "151925412000204915,bad date,1232120,7443884296972096163";
    private final String properLine = "151925412000204915,2018-02-22 00:01:34.388,1232120,7443884296972096163";

    @Test //(expected = NumberFormatException.class)
    void testExceptionsThrownOnBadLines() {
        assertThrows(NumberFormatException.class, () -> {
            ADClick adClick = new ADClick(badIdLine);
        });

        assertThrows(ParseException.class, () -> {
            ADClick adClick = new ADClick(badDateLine);
        });

    }

    @Test
    void testProperLineIsParsedIntoADClick() throws ParseException {
        ADClick adClick = new ADClick(properLine);
        //151925412000204915,2018-02-22 00:01:34.388,1232120,7443884296972096163
        assertEquals(151925412000204915L, adClick.getId());
        assertEquals(ADConstants.df.parse("2018-02-22 00:01:34.388"), adClick.getLogTime());
        assertEquals(1232120, adClick.getCampaignId());
        assertEquals(7443884296972096163L, adClick.getInteractionId());
    }

    @Test
    void testToString() throws ParseException {
        ADClick adClick = new ADClick(properLine);
        String stringyStringOfStringTribe = adClick.toString();
        assertEquals("Id: 151925412000204915 logTime: 2018-02-22 00:01:34.388 campaignId: 1232120 intId: 7443884296972096163"
                , stringyStringOfStringTribe);
    }
}