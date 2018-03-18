package ADHomework.ADEntities;

import java.util.Date;

public class ADIdLogTimed {

    protected Date logTime;
    protected Long id;

    public Date getLogTime() {
        return logTime;
    }
    
    public Long getId(){
        return id;
    };

    @Override
    public String toString(){
        return "Id: " + this.id.toString() + " logTime: " + this.logTime.toString();
    }
}
