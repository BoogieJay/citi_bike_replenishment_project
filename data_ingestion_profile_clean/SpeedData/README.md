#Traffic-Speed Cleaning 
##What is this?
1. This is data cleaning program for Traffic-Speed data.
2. When we collect data from `http://dotsignals.org/nyc-links-cams/TrafficSpeed.php`, we have preprocessed raw data. The processing program is StoreData.java and we store processed data as `newData.txt`.

##How do we get this?
1. Then we use MapReduce to clean this Traffic-Speed data(`newData.txt`).
2. The result output is under `speed_10/ ` directory. 

##How to read this?
1. The output format is like `40.60759,-74.1409 Tue Morning	9.94`. It means at `40.60759,-74.1409` this location, the average traffic speed is 9.94 mph on `Tuesday` `Morning`.
2. According to our research, the peak time is 8am-11am and 17pm-19pm on weekdays and 13pm-15pm on weekends. So we define 8am-11am as `Morning`, 17pm-19pm as `Nignt` and 13pm-15pm as `Noon`.