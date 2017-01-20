We using Spark to analyze our project based on Amazon Web Service.

In order to understand our analysis, please open ./code/Spark_CitiBike_Project.html

1. read three data output from reducers into spark as dataframe format

2. join station dataframe and trip dataframe on id and named it bike dataframe

3. Select which day(Mon - Sun) and Time (Morning or Night for weekday and Noon for weekend) to analyze.

4. calculate the influence of traffic by comparing two points (one is station and another one is speed point).

5. Combine three factors(percent, rate, distance_score) and calculate the final score for each station

6. Add the final score to the station dataframe and sort the table based on score

7. Choose the top 10 station which need immediately replenishment.