package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.text.ParseException;
import java.util.Date;

public class ADViewWithClick extends ADIdLogTimed{

    @CsvBindByName(column = "clickId")
    @CsvBindByPosition(position = 2)
    private final long clickId;// = Click.Id;


    @CsvBindByName(column = "campaignId")
    @CsvBindByPosition(position = 3)
    private final int campaignId;

    public ADViewWithClick(Long id, Date logTime, Long clickId, Integer campaignId){
        this.clickId = clickId;
        this.logTime = logTime;
        this.id = id;
        this.campaignId = campaignId;
    };

    public ADViewWithClick(String line) throws ParseException {
        String[] chunks = line.split(",");
        this.logTime = ADConstants.df.parse(chunks[1].replaceAll("\"", ""));
        this.id = Long.parseLong(chunks[0].replaceAll("\"", ""));
        this.clickId = Long.parseLong(chunks[2].replaceAll("\"", ""));
        this.campaignId = Integer.parseInt(chunks[3].replaceAll("\"", ""));
    }

    public long getClickId() {
        return clickId;
    }


    public int getCampaignId() {
        return campaignId;
    }

    @Override
    public String toString(){
        return super.toString() + " clickId: " + this.clickId;
    }

}
