package ADHomework.ADEntities;

import java.math.BigDecimal;
import java.util.Date;

public class ADViewWithClick extends ADIdLogTimed{

    private final Long clickId;// = Click.Id;

    public ADViewWithClick(Long id, Date logTime, Long clickId){
        this.clickId = clickId;
        this.logTime = logTime;
        this.id = id;
    };

    public Long getClickId() {
        return clickId;
    }

    @Override
    public String toString(){
        return super.toString() + " clickId: " + this.clickId.toString();
    }

}
