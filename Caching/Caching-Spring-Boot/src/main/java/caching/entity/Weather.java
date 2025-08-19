package caching.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "weather")
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String forecast;

    public Weather() {}

    public Weather(String city, String forecast) {
        this.city = city;
        this.forecast = forecast;
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getForecast() {
        return forecast;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }
}
