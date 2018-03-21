package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class ADIdLogTimed {

    @CsvBindByName(column = "logTime")
    @CsvBindByPosition(position = 1)
    @CsvDate(ADConstants.DATE_FORMAT)
    protected Date logTime;

    @CsvBindByName(column = "id")
    @CsvBindByPosition(position = 0)
    protected long id;


    public Date getLogTime() {
        return logTime;
    }
    
    public long getId(){
        return id;
    };

    @Override
    public String toString(){
        return "Id: " + this.id + " logTime: " + ADConstants.df.format(this.logTime);
    }
}
