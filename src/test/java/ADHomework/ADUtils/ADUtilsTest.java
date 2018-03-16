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
        Date timeA = ADConstants.df.parse("2018-02-22 00:00:00.127");
        Date timeB = ADConstants.df.parse("2018-02-22 00:00:02.127");
        long range = 3000;
        assertTrue(ADUtils.logTimeInRange(timeA,timeB,range));

        timeB = ADConstants.df.parse("2018-02-22 00:00:08.127");
        assertFalse(ADUtils.logTimeInRange(timeA,timeB,range));
    }
}