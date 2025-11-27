package com.Ilearn.journalApp.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {

    private Current current;


    @Getter
    @Setter
    public class Current {

        public int temperature;

        @JsonProperty("weather_descriptions")
        public List<String> weather_descriptions;

        public int feelslike;
    }

}





