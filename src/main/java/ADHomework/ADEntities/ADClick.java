package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;

import java.text.ParseException;

/**
 * Could be an extension of ADVIewableView, but then need to manipulate constructor parameters,
 * leads to more memory usage. Probably :)
 */
public class ADClick extends ADIdLogTimedCmpgn {

    private final int campaignId;

    private final long interactionId;

    public ADClick(String line) throws ParseException {
        String[] chunks = line.split(",");

        this.id = Long.parseLong(chunks[0]);
        this.campaignId = Integer.parseInt(chunks[2]);
        this.interactionId = Long.parseLong(chunks[3]);
        this.logTime = ADConstants.df.parse(chunks[1]);
    }

    public long getInteractionId() {
        return interactionId;
    }

    @Override
    public String toString(){
        return super.toString() + " intId: " + this.interactionId;
    }
}
