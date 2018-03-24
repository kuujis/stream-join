package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public abstract class ADIdLogTimedCmpgn {

    @CsvBindByName(column = "id")
    @CsvBindByPosition(position = 0)
    long id;

    @CsvBindByName(column = "logTime")
    @CsvBindByPosition(position = 1)
    @CsvDate(ADConstants.DATE_FORMAT)
    Date logTime;

    @CsvBindByName(column = "campaignId")
    @CsvBindByPosition(position = 2)
    int campaignId;

    public Date getLogTime() {
        return logTime;
    }
    
    public long getId(){
        return id;
    }

    public int getCampaignId(){
        return campaignId;
    }

    @Override
    public String toString(){
        return "Id: " + this.id + " logTime: " + ADConstants.df.format(this.logTime) + " campaignId: " + this.campaignId;
    }
}
