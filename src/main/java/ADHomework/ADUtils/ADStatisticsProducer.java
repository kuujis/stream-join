package ADHomework.ADUtils;

import ADHomework.ADEntities.ADStatistic;
import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;
import ADHomework.ADUtils.ADCollectors.ADClicksCollector;
import ADHomework.ADUtils.ADCollectors.ADViewsCollector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ADStatisticsProducer {

    private final String views;
    private final String clicks;
    private final String viewableViews;
    private final String viewsWithClicks;
    private final String filteredViews;
    private final String statistics;
    Map<Integer, ADStatistic> stats;

    public ADStatisticsProducer(String[] args) {
        this.views = args[0];
        this.clicks = args[1];
        this.viewableViews = args[2];
        this.viewsWithClicks = args[3];
        this.filteredViews = args[4];
        this.statistics = args[5];
        stats = Collections.synchronizedMap(new HashMap<Integer, ADStatistic>());
    }

    public void generate() {
        try {
            Stream<String> viewStream = Files.newBufferedReader(Paths.get(this.views)).lines();
            boolean collectingViews = true;
            stats = viewStream.map(ADUtils.lineToADView)
                    .filter(adView -> adView != null)
                    .parallel()
                    .collect(Collectors.groupingBy(ADView::getCampaignId, ADViewsCollector.campaignCollector(collectingViews)));

            System.out.println(stats);

            Stream<String> filteredViews = Files.newBufferedReader(Paths.get(this.viewsWithClicks)).lines();
            stats = filteredViews.map(ADUtils.lineToADViewWithClick)
                    .filter(vv -> vv != null)
                    .parallel()
                    .collect(Collectors.groupingBy(ADViewWithClick::getCampaignId, ADClicksCollector.clicksCollector()));

            System.out.println(stats);

            collectingViews = false;
            Stream<String> viewableViews = Files.newBufferedReader(Paths.get(this.filteredViews)).lines();
            stats = viewableViews.map(ADUtils.lineToADView)
                    .filter(adView -> adView != null)
                    .parallel()
                    .collect(Collectors.groupingBy(ADView::getCampaignId, ADViewsCollector.campaignCollector(collectingViews)));

            System.out.println(stats);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void createOrUpdateStats(ADView adView, Map<Integer, ADStatistic> stats) {
//        ADStatistic stat = stats.get(adView.getCampaignId());
//        stats.
//        if (stat != null) {
//            stat.setViews(stat.getViews() + 1);
//        } else {
//            stat = new ADStatistic(adView.getCampaignId());
//            stat.setViews(1);
//            stats.put(adView.getCampaignId(), stat);
//        }
//    }
}
