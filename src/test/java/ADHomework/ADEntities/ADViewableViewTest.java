package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ADViewableViewTest {

    private final String badIdLine = "bad id,2018-02-22 00:00:03.863,2975826529651648264";
    private final String badDateLine = "151925403000149618,bad date,2975826529651648264";
    private final String properLine = "151925403000149618,2018-02-22 00:00:03.863,2975826529651648264";

    @Test
    void testExceptionsForBadlines() {
        assertThrows(NumberFormatException.class,
                () -> {
                    ADViewableView adViewableView = new ADViewableView(badIdLine);
                });
        assertThrows(ParseException.class,
                () -> {
                    ADViewableView adViewableView = new ADViewableView(badDateLine);
                }
        );
    }

    @Test
    void testProperLineIsParsed() throws ParseException {
        ADViewableView adViewableView = new ADViewableView(properLine);
        assertEquals(151925403000149618L, adViewableView.getId());
        assertEquals(ADConstants.df.parse("2018-02-22 00:00:03.863"), adViewableView.getLogTime());
        assertEquals(2975826529651648264L, adViewableView.getInteractionId());
    }

    @Test
    void setProperLine() throws ParseException {
        ADViewableView vv = new ADViewableView(properLine);
        assertEquals("Id: 151925403000149618 logTime: 2018-02-22 00:00:03.863 campaignId: 0 intId: 2975826529651648264",
                vv.toString());
    }
}