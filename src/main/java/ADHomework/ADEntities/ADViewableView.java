package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;

import java.text.ParseException;
import java.util.Date;

public class ADViewableView extends ADIdLogTimed{

    private final long interactionId;

    public ADViewableView(String line) throws ParseException {
        String[] chunks = line.split(",");
        this.id = Long.parseLong(chunks[0]);
        this.interactionId = Long.parseLong(chunks[2]);
        this.logTime = ADConstants.df.parse(chunks[1]);
    }

    public Long getInteractionId() {
        return interactionId;
    }

    @Override
    public String toString(){
        return super.toString() + " intId: " + this.getInteractionId().toString();
    }

}
