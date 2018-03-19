package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ADViewableViewTest {

    private String badIdLine = "bad id,2018-02-22 00:00:03.863,2975826529651648264";
    private String badDateLine = "151925403000149618,bad date,2975826529651648264";
    private String properLine = "151925403000149618,2018-02-22 00:00:03.863,2975826529651648264";

    @Test
    public void testExceptionsForBadlines() {
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
    public void testProperLineIsParsed() throws ParseException {
        ADViewableView adViewableView = new ADViewableView(properLine);
        assertEquals(151925403000149618l, adViewableView.getId());
        assertEquals(ADConstants.df.parse("2018-02-22 00:00:03.863"), adViewableView.getLogTime());
        assertEquals(2975826529651648264l, adViewableView.getInteractionId());
    }
}