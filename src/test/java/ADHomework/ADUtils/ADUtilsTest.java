package ADHomework.ADUtils;

import ADHomework.ADEntities.ADView;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ADUtilsTest {

    @Test
    void testStreamsWithMaxIDs() throws ParseException {

        //ADView adView = new ADView("\"5,76777668144944E+018\",2018-02-22 00:00:00.127,1221633");

    }
    @Test
    public void testLogTimeInRange() throws ParseException {
        Date timeBefore = ADConstants.df.parse("2018-02-22 00:00:00.127");
        Date timeIn = ADConstants.df.parse("2018-02-22 00:05:00.127");
        Date timeAfter = ADConstants.df.parse("2018-02-22 00:10:00.127");

        Date startPoint = ADConstants.df.parse("2018-02-22 00:01:02.127");
        Date endPoint = ADConstants.df.parse("2018-02-22 00:09:02.127");

        long range = 3000;
        assertFalse(ADUtils.logTimeInRange(timeBefore,startPoint,endPoint));
        assertTrue(ADUtils.logTimeInRange(timeIn, startPoint,endPoint));
        assertFalse(ADUtils.logTimeInRange(timeAfter, startPoint,endPoint));

    }
}