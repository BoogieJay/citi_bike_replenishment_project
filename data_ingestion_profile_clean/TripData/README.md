#Bike Trip Data Processing
##What is this?
1. This is data processing program for Citi bike trip data.
2. My raw data is static and it is in .csv format. I ingested it by simply downloading it from the website `https://s3.amazonaws.com/tripdata/index.html`.

##How do we get this?
1. We use MapReduce to clean and process the Citi bike trip data.

##How to read this?
1. The output format is like `116 Mon Morning -2`. It means at location `116`, the average number of bikes going out from the station per hour is `2` on `Mon``Morning`.
2. According to our analysis, the peak time is 8am-10am and 17pm-19pm on weekdays and 13pm-15pm on weekends. So we define 8am-10am as `Morning`, 17pm-19pm as `Nignt` and 13pm-15pm as `Noon`.
