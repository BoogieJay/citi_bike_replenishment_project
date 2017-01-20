#StationData 
##Data Ingestion
1. The raw data are ingested from [https://feeds.citibikenyc.com/stations/stations.json]
2. The result(txt format) of raw ingestion could be download at [https://drive.google.com/open?id=0B2eD1kXA8I9xdk43M2k4LTVsb3c]
3. Code for ingestion are in station_data_ingestion folder

##Data Clean and Profile
1. Mapper 
	* extract "day", "time", "id", "latitude", "longitude", "toalDocks" and calculate the number of available Bikes as "availableBikes". only chooes the data with statusKey = 1 (Means the station works well) and percent < 0.2 (Means the station is lack of bikes)
	* The output of mapper is like "Key (day time id latitude longitude totalDocks) : Value(Percent)"
2. Reducer
	* Avarage the percent from the Mapper outputs sharing the same key value