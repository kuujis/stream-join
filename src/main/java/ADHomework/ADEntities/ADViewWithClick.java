package ADHomework.ADEntities;

import java.math.BigDecimal;
import java.util.Date;

public class ADViewWithClick {
    private final Long id;//=> View.Id;
    private final Date logTime;  //=> View.Logtime;

    private final Long clickId;// = Click.Id;

    public ADViewWithClick(Long id, Date logTime, Long clickId){

        this.clickId = clickId;
        this.logTime = logTime;
        this.id = id;
    };

    public Long getId() {
        return id;
    }

    public Date getLogTime() {
        return logTime;
    }

    public Long getClickId() {
        return clickId;
    }

}
