package ADHomework.ADUtils;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ADVVwithCmpgnProducer {


    public void generateVVwithCmpgn(String[] args) throws IOException {
        BufferedReader views = Files.newBufferedReader(Paths.get(args[0]));
        //BufferedReader clicks = Files.newBufferedReader(Paths.get(args[1]));
        Path clicks = Paths.get(args[1]);
        BufferedReader viewableClicks = Files.newBufferedReader(Paths.get(args[2]));

        Writer writer = new FileWriter("ViewsWithClicks.csv");
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

        //Create ViewsWithClicks file
        //getADViewsWithClicks(views.lines(), clicks, beanToCsv);

        writer.close();
    }
}
