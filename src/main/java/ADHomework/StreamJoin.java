package ADHomework;

import ADHomework.ADEntities.ADView;
import ADHomework.ADUtils.ADUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kazys on 2018-03-10.
 */
public class StreamJoin {

    public static void main(String [] args){

        ADUtils.checkFiles(args);

        List<ADView> list = new ArrayList<ADView>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(args[0]))) {

            list = br.lines().map(ADUtils.lineToADView)
                    .filter(t -> t != null)
                    .collect(Collectors.<ADView>toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(list);

        //File views = new File(args[0]);
        //File clicks = new File(args[1]);
        //File viewableClicks = new File(args[2]);

    }
}
