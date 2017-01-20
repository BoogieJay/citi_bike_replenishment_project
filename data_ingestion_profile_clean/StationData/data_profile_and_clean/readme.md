1.Input file
	
	the raw JSON data file from data ingestion

2. Mapper
	
	extract "day", "time", "id", "latitude", "longitude", "toalDocks" and calculate the number of available Bikes as "availableBikes".
	only chooes the data with statusKey = 1 (The station works well) and percent < 0.2 (The station is lack of bikes)
	So the output of Mapper is like:

	Key (day time id latitude longitude totalDocks) : Value(Percent)

3.Reducer
	
	Avarage the percent from the input sharing the same key

