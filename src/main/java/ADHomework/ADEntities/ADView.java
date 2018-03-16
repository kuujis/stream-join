package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADView extends ADIdLogTimed{

    private final Integer campaignId;

    public ADView(String line) throws ParseException {
        //shamefurr copy from https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes :)
        String[] chunks = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        this.logTime = ADConstants.df.parse(chunks[1]);
        this.id = new BigDecimal(chunks[0].replaceAll("\"", "").replace(",", ".")).longValue();
        this.campaignId = Integer.parseInt(chunks[2]);
    }

    public Integer getCampaignId() {
        return campaignId;
    }
}
