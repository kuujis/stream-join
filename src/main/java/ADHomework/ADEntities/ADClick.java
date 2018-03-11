package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADClick {
    public ADClick(String line) throws ParseException {
        String[] chunks = line.split(",");

        this.id = Long.parseLong(chunks[0]);
        this.campaignId = Integer.parseInt(chunks[2]);
        this.interactionId = Long.parseLong(chunks[3]);
        this.logTime = ADConstants.df.parse(chunks[1]);
    }

    public long getId() {
        return id;
    }

    public Date getLogTime() {
        return logTime;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public long getInteractionId() {
        return interactionId;
    }

    private long id;
    private Date logTime;
    private int campaignId;
    private long interactionId;
    /**Click:
    *Id
    * Logtime
    * Campaign Id
    * Interaction Id (View.Id)*/


}
