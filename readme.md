#Data processing excercise

Java 9 and Gradle 4.6 was used to build the project. 

Execute *"gradle run"*  to run with default argument values, if missing - output files will be created: 
* "Views.csv" 
* "Clicks.csv" 
* "ViewableViews.csv" 
* "ViewsWithClicks.csv", 
* "FilteredViews.csv" 
* "statistics.csv" 
* "1200000" 
* "3"  

*"gradle jar"* will create a fat jar file with included needed dependencies in build/lib folder. 

Launching the jar file with "*java -jar stream-join-1.0.0-SNAPSHOT.jar*" will handle files in the same directory with default names or passed in as parameters

Launching the jar file "*java -jar stream-join-1.0.0-SNAPSHOT.jar "Views.csv" "Clicks.csv" "ViewableViews.csv"*" will expect that provided input csv files exist and are reachable.

The other three values are treated as 3 output file names (defaults being "ViewsWithClicks.csv", FilteredViews.csv" and "statistics.csv").

Finally - last two parameters are for handling timeWindow and bufferSize constants, defaults are 1200000 (20 minutes) and 3. Chosen after some experimentation to minimize execution time. Max differences found were about 2.4 minutes, so 300000 could be considered safe(?) lower boundary.
* timeWindow is an long value in milliseconds, which defines how far from a given view event Clicks and Viewable View events should be searched. It is also used when refreshing ADAmateurishCache to determine its size.
* bufferSize value is an integer multiplier, which defines how many timeWindows in advance should be included in ADAmateurishCache.

So, to specify all possible values for a jar file, one could use the below:  
_java -jar stream-join-1.0.0-SNAPSHOT.jar "Views.csv" "Clicks.csv" "ViewableViews.csv" "ViewsWithClicks.csv", FilteredViews.csv" "statistics.csv" "1200000" "3"_

".idea" folder is used by IntelliJ, had to update some settings manually to avoid coverage calculation output folder usage issues, can be safely ignored if building with gradle or running jar files.