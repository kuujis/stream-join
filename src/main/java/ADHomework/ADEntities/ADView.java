package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Created by Kazys on 2018-03-10.
 */
public class ADView extends ADIdLogTimed {

    @CsvBindByName(column = "campaignId")
    @CsvBindByPosition(position = 2)
    private final int campaignId;

    public ADView(String line) throws ParseException {
        //shamefurr copy from https://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes :)
        //String[] chunks = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        String[] chunks = line.split(",");

        this.logTime = ADConstants.df.parse(chunks[1].replaceAll("\"", ""));
        //this.id = new BigDecimal(chunks[0].replaceAll("\"", "").replace(",", ".")).longValue();
        this.id = Long.parseLong(chunks[0].replaceAll("\"", ""));
        this.campaignId = Integer.parseInt(chunks[2].replaceAll("\"", ""));
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    @Override
    public String toString() {
        return super.toString() + " cmpg: " + this.campaignId;
    }

}
