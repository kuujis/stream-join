package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;


/**
 * Created by Kazys on 2018-03-10.
 */
public class ADViewTest {


    private String line0 = "id,logtime,campaignid";
    private String badIdLine = "\"bad,number with comma\",2018-02-22 00:00:00.127,1221633";
    private String properLine = "\"5,76777668144944E+018\",2018-02-22 00:00:00.127,1221633";

    @Test (expected = ParseException.class)
    public void testFirstLineParsingReturnsThrowsException() throws ParseException {
        ADView adView = new ADView(line0);
    }

    @Test (expected = NumberFormatException.class)
    public void testNumberFormatExceptionThrownWhenBadId() throws ParseException {
        ADView adView = new ADView(badIdLine);
    }

    @Test
    public void testObjectCreatedWithProperValues() throws ParseException {
        ADView adView = new ADView(properLine);
        Assert.assertEquals(1221633, adView.getCampaignId());
        Assert.assertEquals(new BigDecimal("5.76777668144944E+018"), adView.getId());
        Assert.assertEquals(ADConstants.df.parse("2018-02-22 00:00:00.127"),adView.getLogTime());
    }
}