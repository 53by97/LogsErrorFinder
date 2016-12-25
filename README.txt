Contents:
-------------------
* src - source files
* server_log_file.log - actual log file which holds dummy data of ~10 MB size
* server_log_file.output - actual output copied from console logs
* sample.log - sample log file (with less data)
* sample.output - sample output copied from console for sample.log

How to Run:
-----------------------------------
* Extract the contents of zip file
* Open CMD/Shell terminal
* Navigate to extracted folder
* Build the project with command: 
	mvn clean install
* Run the following command:
	java -jar target/LogsErrorFinder-0.1-SNAPSHOT-jar-with-dependencies.jar
									or
	java -cp target/LogsErrorFinder-0.1-SNAPSHOT-jar-with-dependencies.jar com.redbus.assignment.logserrorfinder.main.LogsErrorFinderMain
