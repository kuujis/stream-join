package ADHomework;

import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;
import ADHomework.ADUtils.ADUtils;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kazys on 2018-03-10.
 */
public class StreamJoin {

    public static void main(String[] args) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        ADUtils.checkFiles(args);

        try {
            BufferedReader views = Files.newBufferedReader(Paths.get(args[0]));
            //BufferedReader clicks = Files.newBufferedReader(Paths.get(args[1]));
            Path clicks = Paths.get(args[1]);
            BufferedReader viewableClicks = Files.newBufferedReader(Paths.get(args[2]));

            Writer writer = new FileWriter("ViewsWithClicks.csv");
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

            //beanToCsv.build().write(ADUtils.getADViewsWithClicks(views.lines(), clicks);
            ADUtils.getADViewsWithClicks(views.lines(), clicks, beanToCsv);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
