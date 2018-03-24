package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;

import java.text.ParseException;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADView extends ADIdLogTimedCmpgn {

    public ADView(String line) throws ParseException {
        String[] chunks = line.split(",");

        this.logTime = ADConstants.df.parse(chunks[1].replaceAll("\"", ""));
        this.id = Long.parseLong(chunks[0].replaceAll("\"", ""));
        this.campaignId = Integer.parseInt(chunks[2].replaceAll("\"", ""));
    }

    public int getCampaignId() {
        return campaignId;
    }

}
