
# coding: utf-8

# In[2]:

from pyspark import SparkContext
from pyspark.sql import SQLContext
from math import sqrt
from pyspark.sql.functions import lit


# In[3]:

sc = SparkContext()
sqlContext = SQLContext(sc)


# In[14]:




# In[ ]:




# In[ ]:

# Read three dataSet


# In[4]:

station_rdd = sc.textFile("./result/station_result")
station_rdd = station_rdd.map(lambda line : line.split())


# In[5]:

station_rdd.take(3)


# In[6]:

station_df = station_rdd.toDF(["Day", "Time", "Id", "Latitude", "Longitude", "Total","Percent"])


# In[7]:

station_df.show(3)


# In[ ]:




# In[ ]:




# In[8]:

traffic_rdd = sc.textFile("./result/traffic_result_20")
traffic_rdd = traffic_rdd.map(lambda line : line.split())


# In[9]:

traffic_rdd.take(3)


# In[10]:

traffic_df = traffic_rdd.toDF(["Latitude", "Longitude", "Day", "Time", "Speed"])


# In[11]:

traffic_df.show(3)


# In[ ]:




# In[ ]:




# In[12]:

trip_rdd = sc.textFile("./result/trip_result")
trip_rdd = trip_rdd.map(lambda line : line.split())


# In[13]:

trip_rdd.take(3)


# In[14]:

trip_df = trip_rdd.toDF(["Id", "Day", "Time", "Rate"])


# In[15]:

trip_df.show(3)


# In[ ]:




# In[ ]:




# In[ ]:




# In[9]:

# Inner join station_data and trip_data


# In[16]:

bike_df = station_df.join(trip_df, on = ["Id", "Time", "Day"])


# In[17]:

bike_df.show(3)


# In[ ]:




# In[ ]:




# In[ ]:




# In[25]:

# Select Monday monrning to analyze


# In[124]:

bike_curr = bike_df.filter((bike_df["Day"] == "Mon") & (bike_df["Time"] == "Morning"))


# In[125]:

bike_curr.show(3)


# In[ ]:




# In[20]:

traffic_curr = traffic_df.filter((traffic_df["Day"] == "Mon") & (traffic_df["Time"] == "Morning"))


# In[22]:

traffic_curr.show(3)


# In[ ]:




# In[ ]:




# In[ ]:




# In[ ]:




# In[34]:




# In[39]:

# Combine three factors(percent, rate, distance)


# In[ ]:

# Get the distance score


# In[35]:

def getScoreFromPoint (bike_curr, traffic_curr):
    lst = []
    bike_collect = bike_curr.collect()
    traffic_collect = traffic_curr.collect()
    for i in range(bike_curr.count()):
        bike_row = bike_collect[i]
        bike_latitude = float(bike_row["Latitude"])
        bike_longtitude = float(bike_row["Longitude"])
        min_dis = 1
        score = 0
        for j in range(traffic_curr.count()):
            traffic_row = traffic_collect[j]
            traffic_latitude = float(traffic_row["Latitude"])
            traffic_longitude = float(traffic_row["Longitude"])
            if ((abs(bike_latitude - traffic_latitude) > 0.01) | (abs(bike_longtitude - traffic_longitude) > 0.01)):
                continue
            dis = sqrt((bike_latitude - traffic_latitude) ** 2 + (bike_longtitude - traffic_longitude) ** 2)
            min_dis = min(dis, min_dis)
        if min_dis >= 0.002:
            score = 0.2
        else:
            score = (min_dis / 0.002) * 0.2
        lst.append(score)
    return lst


# In[36]:

dist_score = getScoreFromPoint(bike_curr, traffic_curr)


# In[ ]:




# In[ ]:

# add another two factors and combine with distance score


# In[56]:

def findLestScore(lst):
    min_rate = 0
    for i in range(len(lst)):
        curr_rate = float(lst[i]["Rate"])
        min_rate = min(min_rate, curr_rate)
    return min_rate


# In[63]:

def combineThreeFactor(dist_score, bike_curr):
    score = []
    bike_collect = bike_curr.collect()
    min_rate = -1 * findLestScore(bike_collect) - 1
    for i in range(len(bike_collect)):
        bike_row = bike_collect[i]
        bike_percent = float(bike_row["Percent"])
        bike_rate = float(bike_row["Rate"])
        curr_score = ((0.2 - bike_percent) * 2) + (-bike_rate + 1) * (0.4 / min_rate) + dist_score[i]
        score.append(curr_score)
    return score


# In[64]:

final_score = combineThreeFactor(dist_score, bike_curr)


# In[ ]:




# In[ ]:




# In[ ]:




# In[ ]:




# In[66]:

# add final score to original table


# In[162]:

id_col = bike_curr.select("Id").collect()


# In[163]:

id_score = []
for i in range(len(final_score)):
    id_score.append([id_col[i]["Id"], final_score[i]])


# In[165]:

id_col_rdd = sc.parallelize(id_score)


# In[167]:

id_col_rdd.take(3)


# In[168]:

id_score_df = id_col_rdd.toDF(["Id", "Score"])


# In[181]:

bike_curr_with_score = bike_curr.join(id_score_df, on = ["Id"])


# In[ ]:




# In[ ]:




# In[ ]:

# Sort on the Score and show top 10 score


# In[182]:

bike_curr_with_score_sorted = bike_curr_with_score.sort("Score", ascending=False)


# In[183]:

bike_curr_with_score_sorted.show(10)


# In[185]:

bike_curr_with_score_sorted.toPandas().to_csv("Analyze_Result")


# In[ ]:



