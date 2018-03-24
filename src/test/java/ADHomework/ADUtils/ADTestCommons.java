package ADHomework.ADUtils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class ADTestCommons {
    static String[] getTestFilesAbsolutePaths(Object testClass, String[] args) throws IOException {
        if (args.length < 3){
            throw new AssertionError("Serious lack of files from test method, dont be lazy.");
        }

        File views = new File(testClass.getClass().getResource(args[0]).getPath());
        File clicks = new File(testClass.getClass().getResource(args[1]).getFile());
        File vviews = new File(testClass.getClass().getResource(args[2]).getFile());

        //views file must be present, use its path to create output files if needed
        String pathToTestDir = FilenameUtils.getFullPath(views.getPath());
        File vwClicks = new File(pathToTestDir + args[3]);
        File fviews = new File(pathToTestDir + args[4]);
        File stats = new File(pathToTestDir + args[5]);

        //ensure that output files exists
        vwClicks.createNewFile();
        fviews.createNewFile();
        stats.createNewFile();

        args = new String[]{views.getAbsolutePath(), clicks.getAbsolutePath(), vviews.getAbsolutePath(), vwClicks.getAbsolutePath(), fviews.getAbsolutePath(), stats.getAbsolutePath()};
        return args;
    }

    public static String advanceTimeInStringBy(String baseTime, int advanceBy) throws ParseException {
        DateFormat df = ADConstants.df;
        return df.format(new Date(df.parse(baseTime).getTime() + advanceBy));
    }
}
