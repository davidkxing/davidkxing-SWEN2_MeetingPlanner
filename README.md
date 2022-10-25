SWEN2_MeetingPlanner

Layer Architecture:
-businesslayer (contains logic and maintains single manager class)
-dataaccesslayer (access to database and query execution)
-logger (logger interface)
-models	(contains meeting and note models)
-views (contains controller classes with access to FXML)

Creational Pattern:
Singelton Pattern -> manager class

Behavioral Pattern:
Observer Pattern -> Observerlist

Unit Test:
ConfigurationManagerTest -> check if properties are correctly accessed
DatabaseTest -> check if connection is correctly setup
MediaItemTest -> check correct model properties

Logging:
log4j implemented

PDF gen instructions:
1. select item from listview
2. generate PDF

generate a PDF for a single Meeting

Link to my git Repository: https://github.com/davidkxing/davidkxing-SWEN2_MeetingPlanner