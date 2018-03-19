package ADHomework.ADEntities;

import ADHomework.ADUtils.ADConstants;

import java.util.Date;

public class ADIdLogTimed {

    protected Date logTime;
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
