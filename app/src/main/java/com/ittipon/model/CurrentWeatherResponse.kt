package com.ittipon.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(

    @SerializedName("weather")
    val weather: List<Weather>?,
    @SerializedName("base")
    val base: String?,
    @SerializedName("main")
    val main: Main?,
    @SerializedName("wind")
    val wind: Wind?,
)

data class Weather(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("main")
    val main: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?
)

data class Main(
    @SerializedName("temp")
    val temp: Double?,
    @SerializedName("feels_like")
    val feels_like: Double?,
    @SerializedName("temp_min")
    val temp_min: Double?,
    @SerializedName("temp_max")
    val temp_max: Double?,
    @SerializedName("pressure")
    val pressure: Int?,
    @SerializedName("humidity")
    val humidity: Int?,
    @SerializedName("sea_level")
    val sea_level: Int? ,
    @SerializedName("grnd_level")
    val grnd_level: Int?
)

data class Wind(
    @SerializedName("speed")
    val speed: Double?,
    @SerializedName("deg")
    val deg: Int?,
    @SerializedName("gust")
    val gust: Double?
)
