#Data processing excercise

execute below to run with default input files, output files will be created
*gradle run  "Views.csv Clicks.csv ViewableViews.csv"* 

"gradle run" will execute with default parameters expecting the first three and outputing the last 3 files, all in current folder.
* Views.csv
* Clicks.csv
* ViewableViews.csv
* ViewsWithClicks.csv
* FilteredViews.csv
* statistics.csv

"gradle jar" will create a fat jar file in build/lib folder. 

Launching the jar file with "java -jar stream-join-1.0.0-SNAPSHOT.jar" will handle files in the same directory with default names or passed in as parameters


