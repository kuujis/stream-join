import junit.framework.TestCase;
import org.junit.Test;

import java.text.ParseException;
import java.lang.NumberFormatException;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADClickTest extends TestCase {
    private String badIdLine = "bad id,2018-02-22 00:01:34.388,1232120,7443884296972096163";
    private String badDateLline = "151925412000204915,bad date,1232120,7443884296972096163";
    private String properLine = "151925412000204915,2018-02-22 00:01:34.388,1232120,7443884296972096163";

    @Test(expected = ParseException.class)
    public void testBadDateResultsInNumberFormatException() throws ParseException {
        ADClick adClick = new ADClick(badIdLine);
    }

    @Test(expected = NumberFormatException.class)
    public void testBadDateResultInParseException() throws ParseException {
        ADClick adClick = new ADClick(badDateLline);
    }

}