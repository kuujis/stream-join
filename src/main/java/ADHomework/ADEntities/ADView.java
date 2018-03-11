package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADView {

    private BigDecimal id;
    private Date logTime;
    private long campaignId;

    public ADView (String line) throws ParseException {
        //shamefurr copy from https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes :)
        String[] chunks = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        this.logTime = ADConstants.df.parse(chunks[1]);
        this.id = new BigDecimal(chunks[0].replaceAll("\"", "").replace(",","."));
        this.campaignId = Long.parseLong(chunks[2]);
    }

    public BigDecimal getId() {
        return id;
    }

    public Date getLogTime() {
        return logTime;
    }

    public long getCampaignId() {
        return campaignId;
    }
}
