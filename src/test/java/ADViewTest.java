import junit.framework.TestCase;
import org.junit.Assert;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADViewTest extends TestCase {


    private String line0 = "id,logtime,campaignid";
    private String properLine = "\"5,76777668144944E+018\",2018-02-22 00:00:00.127,1221633";

    public void testFirstLineParsingReturns0Null0() throws ParseException {
        ADView adView = new ADView(line0);
        Assert.assertTrue(adView.getCampaignId() == 0 && adView.getId().equals(BigDecimal.ZERO) && adView.getLogTime() == null);
    }

    public void testObjectCreatedWithProperValues() throws ParseException {
        ADView adView = new ADView(properLine);
        Assert.assertEquals(1221633, adView.getCampaignId());
        Assert.assertEquals(new BigDecimal("5.76777668144944E+018"), adView.getId());
        Assert.assertEquals(ADConstants.df.parse("2018-02-22 00:00:00.127"),adView.getLogTime());
    }


}