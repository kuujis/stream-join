package ADHomework.ADUtils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class ADTestCommons {
    static String[] getTestFilesAbsolutePaths(Object testClass, String[] args) throws IOException {
        File views = new File(testClass.getClass().getResource(args[0]).getPath());
        File clicks = new File(testClass.getClass().getResource(args[1]).getFile());
        File vviews = new File(testClass.getClass().getResource(args[2]).getFile());
        File fviews = new File(FilenameUtils.getPath(testClass.getClass().getResource(args[2]).getPath()) + args[3]);
        //ensure that output file exists
        fviews.createNewFile();

        args = new String[]{views.getAbsolutePath(), clicks.getAbsolutePath(), vviews.getAbsolutePath(), fviews.getAbsolutePath()};
        return args;
    }

    public static String advanceTimeInStringBy(String baseTime, int advanceBy) throws ParseException {
        DateFormat df = ADConstants.df;
        return df.format(new Date(df.parse(baseTime).getTime() + advanceBy));
    }
}
