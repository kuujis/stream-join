package ADHomework;

import ADHomework.ADEntities.ADView;
import ADHomework.ADEntities.ADViewWithClick;
import ADHomework.ADUtils.ADUtils;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kazys on 2018-03-10.
 */
public class StreamJoin {

    public static void main(String[] args) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        ADUtils.checkFiles(args);

        List<ADView> list = new ArrayList<ADView>();
        try {
            BufferedReader views = Files.newBufferedReader(Paths.get(args[0]));
            BufferedReader clicks = Files.newBufferedReader(Paths.get(args[1]));
            BufferedReader viewableClicks = Files.newBufferedReader(Paths.get(args[2]));

            Writer writer = new FileWriter("ViewsWithClicks.csv");
            StatefulBeanToCsvBuilder<ADViewWithClick> beanToCsv = new StatefulBeanToCsvBuilder(writer);

            beanToCsv.build().write(
                    ADUtils.getADViewsWithClicks(views.lines(), clicks));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(list);

        //File views = new File(args[0]);
        //File clicks = new File(args[1]);
        //File viewableClicks = new File(args[2]);

    }

}
