function showMap(daytime){
    var points = [];
    var xobj = new XMLHttpRequest();
    xobj.overrideMimeType("application/json");
    xobj.open('GET', 'res.json', true);
    xobj.onreadystatechange = function () {
          if (xobj.readyState == 4 && xobj.status == "200") {
            var text = JSON.parse(xobj.responseText);
            for (i = 0; i < 10; i++) {
                var point = text[daytime][i] + '';
                points[i] = point.split(",").reverse();
            }
          }
    };
    xobj.send(null);  
    mapboxgl.accessToken = 'pk.eyJ1Ijoid2Vpc2hpIiwiYSI6ImNpbmc5cHV4bzFnOHJ1Zmx3ZGxpaGU0aGIifQ.L20RZ709ePgurmOeOYPwXg';
    var map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/streets-v9',
        center: [-73.951425, 40.746338],
        zoom: 11.7
    });

    map.on('load', function () {
        map.addSource("points", {
            "type": "geojson",
            "data": {
                "type": "FeatureCollection",
                "features": [{
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": points[0]
                    },
                    "properties": {
                        "title": "1",
                        "icon": "circle"
                    }
                }, {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": points[1]
                    },
                    "properties": {
                        "title": "2",
                        "icon": "circle"
                    }
                }, {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": points[2]
                    },
                    "properties": {
                        "title": "3",
                        "icon": "circle"
                    }
                }, {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": points[3]
                    },
                    "properties": {
                        "title": "4",
                        "icon": "circle"
                    }
                }, {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": points[4]
                    },
                    "properties": {
                        "title": "5",
                        "icon": "circle"
                    }
                }, {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": points[5]
                    },
                    "properties": {
                        "title": "6",
                        "icon": "circle"
                    }
                }, {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": points[6]
                    },
                    "properties": {
                        "title": "7",
                        "icon": "circle"
                    }
                }, {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": points[7]
                    },
                    "properties": {
                        "title": "8",
                        "icon": "circle"
                    }
                }, {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": points[8]
                    },
                    "properties": {
                        "title": "9",
                        "icon": "circle"
                    }
                }, {
                    "type": "Feature",
                    "geometry": {
                        "type": "Point",
                        "coordinates": points[9]
                    },
                    "properties": {
                        "title": "10",
                        "icon": "circle"
                    }
                }]
            }
        });
        map.addLayer({
            "id": "points",
            "type": "symbol",
            "source": "points",
            "layout": {
                "icon-size": 0.8,
                "icon-image": "{icon}-15",
                "text-field": "{title}",
                "text-font": ["Open Sans Semibold"],
                "text-offset": [0, 0.2],
                "text-anchor": "top"
            }
        });
    });
}


$("#submit-button").on("click", function(){
    var daytime;
    var day = $("#day option:selected").text();
    var time = $("#time option:selected").text();
    daytime = day + "_" + time;

    showMap(daytime);
    
});


$("#day").on("change", function(){
    if(this.value == "Sat" || this.value == "Sun"){
        $("#time").html("<option>Noon</option>");
    } else {
        $("#time").html("<option>Morning</option><option>Night</option>");
    }
});




showMap();