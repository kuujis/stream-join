package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;

import java.text.ParseException;
import java.util.Date;

/**
 * Could be an extension of ADVIewableView, but then need to manipulate constructor parameters,
 * leads to more memory usage. Probably :)
 */
public class ADClick {

    private final Long id;
    private final Date logTime;
    private final Integer campaignId;
    private final Long interactionId;

    public ADClick(String line) throws ParseException {
        String[] chunks = line.split(",");

        this.id = Long.parseLong(chunks[0]);
        this.campaignId = Integer.parseInt(chunks[2]);
        this.interactionId = Long.parseLong(chunks[3]);
        this.logTime = ADConstants.df.parse(chunks[1]);
    }

    public Long getId() {
        return id;
    }

    public Date getLogTime() {
        return logTime;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public Long getInteractionId() {
        return interactionId;
    }

}
