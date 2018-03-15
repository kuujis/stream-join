package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import ADHomework.ADUtils.ADUtils;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ADViewWithClickTest {

    @Test
    public void testConstructor() throws ParseException {
        Long id = 1l;
        Long clickId = 2l;
        Date logTime = ADConstants.df.parse("2018-02-22 00:00:00.127");

        ADViewWithClick adViewWithClick = new ADViewWithClick(id, logTime, clickId);

        assertEquals(id, adViewWithClick.getId());
        assertEquals(clickId, adViewWithClick.getClickId());
        assertEquals(logTime, adViewWithClick.getLogTime());
    }

}