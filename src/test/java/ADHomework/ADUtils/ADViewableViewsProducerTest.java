package ADHomework.ADUtils;

import ADHomework.ADEntities.ADViewableView;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ADViewableViewsProducerTest {

    @Test
    void generateViewableViews() {
    }

    @Test
    void generateViewableViews2() {
    }

    @Test
    void getMatchingVVs() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File views = new File(this.getClass().getResource("/ViewsS.csv").getFile());
        File clicks = new File(this.getClass().getResource("/ClicksS.csv").getFile());
        File vviews = new File(this.getClass().getResource("/ViewableViewsS.csv").getFile());
        File fviews = new File(this.getClass().getResource("/FilteredViews.csv").getFile());

        String[] args = new String[]{views.getAbsolutePath(), clicks.getAbsolutePath(), vviews.getAbsolutePath(), fviews.getAbsolutePath()};

        ADViewableViewsProducer vvPrdcr = new ADViewableViewsProducer(args);

        vvPrdcr.generateViewableViews();

    }
}