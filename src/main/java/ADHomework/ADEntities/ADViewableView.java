package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.text.ParseException;

public class ADViewableView extends ADIdLogTimed{

    @CsvBindByName(column = "interactionId")
    @CsvBindByPosition(position = 2)
    private final long interactionId;

    public ADViewableView(String line) throws ParseException {
        String[] chunks = line.split(",");
        this.id = Long.parseLong(chunks[0]);
        this.interactionId = Long.parseLong(chunks[2]);
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
