package ADHomework.ADEntities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class ADStatistic {
    @CsvBindByName(column = "id")
    @CsvBindByPosition(position = 0)
    private int campaignId;
    @CsvBindByName(column = "views")
    @CsvBindByPosition(position = 1)
    private int views;
    @CsvBindByName(column = "clicks")
    @CsvBindByPosition(position = 2)
    private int clicks;
    @CsvBindByName(column = "viewableViews")
    @CsvBindByPosition(position = 2)
    private int viewableViews;
    @CsvBindByName(column = "clickThrough")
    @CsvBindByPosition(position = 2)
    private double clickThrough;

    public ADStatistic(){
        this.campaignId = 0;
        this.views = 0;
    }

    @Override
    public String toString() {
        return "campaignId " + this.campaignId +
                " , views  " + this.views +
                " , clicks  " + this.clicks +
                " , viewableViews  " + this.viewableViews +
                " , clickThrough  " + this.clickThrough;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getViewableViews() {
        return viewableViews;
    }

    public void setViewableViews(int viewableViews) {
        this.viewableViews = viewableViews;
    }

    public double getClickThrough() {
        return clickThrough;
    }

    private void setClickThrough(double clickThrough) {
        this.clickThrough = clickThrough;
    }

    public synchronized static void refreshClickthrough(ADStatistic v) {
        if (v.getClicks() > 0 & v.getViews() > 0 ){
            v.setClickThrough((double)v.getClicks() / (double) v.getViews() * 100d);
        } else {
            v.setClickThrough(0d);
        }
    }
}
