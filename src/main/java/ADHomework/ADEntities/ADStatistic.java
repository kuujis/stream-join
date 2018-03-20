package ADHomework.ADEntities;

public class ADStatistic {
    int campaignId;
    int views;
    int clicks;
    int viewableViews;
    double clickThrough;

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

    public ADStatistic(int campaignId){
        this.campaignId = campaignId;
    }

    public int getCampaignId() {
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

    public void setClickThrough(double clickThrough) {
        this.clickThrough = clickThrough;
    }
}
