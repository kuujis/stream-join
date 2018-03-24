#Data processing excercise

execute below to run with default input files "Views.csv" "Clicks.csv" "ViewableViews.csv", output files will be created
*gradle run  

"gradle jar" will create a fat jar file in build/lib folder. 

Launching the jar file with "java -jar stream-join-1.0.0-SNAPSHOT.jar" will handle files in the same directory with default names or passed in as parameters

Launching the jar file "java -jar stream-join-1.0.0-SNAPSHOT.jar "Views.csv" "Clicks.csv" "ViewableViews.csv"" will expect that provided files exist and are reachable.

".idea" folder is used by IntelliJ, had to update some settings manually to avoid coverage calculation output folder usage issues, can be safely ignored if building with gradle or running jar files.
