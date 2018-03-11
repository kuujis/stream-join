package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;

import java.text.ParseException;
import java.util.Date;

public class ADViewableView {

    private final Date logTime;
    private final long interactionId;
    private final long id;

    public ADViewableView(String line) throws ParseException {
        String[] chunks = line.split(",");
        this.id = Long.parseLong(chunks[0]);
        this.interactionId = Long.parseLong(chunks[2]);
        this.logTime = ADConstants.df.parse(chunks[1]);
    }

    public Date getLogTime() {
        return logTime;
    }

    public long getInteractionId() {
        return interactionId;
    }

    public long getId() {
        return id;
    }
}
