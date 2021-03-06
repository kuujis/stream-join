package ADHomework.ADUtils;

import ADHomework.ADEntities.ADStatistic;
import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ADStatisticsProducer {

    private final String views;
    private final String viewsWithClicks;
    private final String filteredViews;
    private final String statistics;
    public Map<Integer, ADStatistic> stats;

    public ADStatisticsProducer(String[] args) {
        this.views = args[0];
        this.viewsWithClicks = args[3];
        this.filteredViews = args[4];
        this.statistics = args[5];
        stats = Collections.synchronizedMap(new HashMap<Integer, ADStatistic>());
    }

    public void generate() {
        try {
            calculateViewsStatistics();

            //System.out.println("Views summary: \n" + stats);

            includeClickInformationToStats();

            //System.out.println("With clicks: \n" + stats);

            includeViewableViewInformation();

            stats.entrySet().stream().parallel().map(Map.Entry::getValue).forEach(ADStatistic::refreshClickthrough);

           // System.out.println("With viewable views: \n" + stats);

            printStats();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void includeViewableViewInformation() throws IOException {
        Map<Integer, ADStatistic> additionalInfo;
        Stream<String> viewableViews = Files.newBufferedReader(Paths.get(this.filteredViews)).lines();
        additionalInfo = Collections.synchronizedMap(viewableViews.map(ADUtils.lineToADView)
                .filter(Objects::nonNull)
                .parallel()
                //false == collecting viewable views
                .collect(Collectors.groupingBy(ADView::getCampaignId, ADStatisticsCollector.statisticsCollector(false))));

        additionalInfo.entrySet().stream()
                .parallel()
                .forEach(s -> {
                    if (stats.containsKey(s.getKey())) {
                        stats.get(s.getKey()).setViewableViews(s.getValue().getViewableViews());
                    } else {
                        //should not happen
                        stats.put(s.getKey(), s.getValue());
                        System.out.println("Strange statistic found, no corresponding campaign in views file:" + s);
                    }
                });
    }

    private void includeClickInformationToStats() throws IOException {
        Stream<String> filteredViews = Files.newBufferedReader(Paths.get(this.viewsWithClicks)).lines();
        Map<Integer, ADStatistic> additionalInfo = Collections.synchronizedMap(filteredViews.map(ADUtils.lineToADViewWithClick)
                .filter(Objects::nonNull)
                .parallel()
                .collect(Collectors.groupingBy(ADViewWithClick::getCampaignId, ADStatisticsCollector.statisticsCollector(false))));

        additionalInfo.entrySet().stream()
                .parallel()
                .forEach(s -> {
                    if (stats.containsKey(s.getKey())) {
                        stats.get(s.getKey()).setClicks(s.getValue().getClicks());
                    } else {
                        //should not happen
                        stats.put(s.getKey(), s.getValue());
                        System.out.println("Strange statistic found, no corresponding campaign in views file:" + s);
                    }
                });
    }

    private void calculateViewsStatistics() throws IOException {
        Stream<String> viewStream = Files.newBufferedReader(Paths.get(this.views)).lines();
        stats = viewStream.map(ADUtils.lineToADView)
                .filter(Objects::nonNull)
                .parallel()
                //true == collecting views, not filtered viewable views
                .collect(Collectors.groupingBy(ADView::getCampaignId, ADStatisticsCollector.statisticsCollector(true)));

    }

    private void printStats() throws IOException {
        Writer writer = null;
        try {
            File f = new File(statistics);
            writer = new FileWriter(f);

            ADMappingStrategy<ADStatistic> strategy = new ADMappingStrategy<>();
            strategy.setType(ADStatistic.class);
            String[] header = {"campaignId", "views", "clicks", "viewableViews", "clickThrough"};
            strategy.setColumnMapping(header);
            strategy.setHeader(header);

            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder<ADStatistic>(writer)
                    .withMappingStrategy(strategy)
                    .build();

            stats.forEach((key, value) -> ADUtils.writeToCsv(value, beanToCsv));

            System.out.println("Statistics file created: " + f.getAbsolutePath());

        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

}
