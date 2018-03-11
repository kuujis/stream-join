package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;


/**
 * Created by Kazys on 2018-03-10.
 */
public class ADClickTest {
    private String badIdLine = "bad id,2018-02-22 00:01:34.388,1232120,7443884296972096163";
    private String badDateLine = "151925412000204915,bad date,1232120,7443884296972096163";
    private String properLine = "151925412000204915,2018-02-22 00:01:34.388,1232120,7443884296972096163";

    @Test(expected = NumberFormatException.class)
    public void testBadDateResultsInNumberFormatException() throws ParseException {
        ADClick adClick = new ADClick(badIdLine);
    }

    @Test(expected = ParseException.class)
    public void testBadDateResultInParseException() throws ParseException {
        ADClick adClick = new ADClick(badDateLine);
    }

    public void testProperLineIsParsedIntoADClick() throws ParseException {
        ADClick adClick = new ADClick(properLine);
        //151925412000204915,2018-02-22 00:01:34.388,1232120,7443884296972096163
        Assert.assertEquals(151925412000204915l, adClick.getId());
        Assert.assertEquals(ADConstants.df.parse("2018-02-22 00:01:34.388"), adClick.getLogTime());
        Assert.assertEquals(1232120, adClick.getCampaignId());
        Assert.assertEquals(7443884296972096163l, adClick.getInteractionId());
    }
}