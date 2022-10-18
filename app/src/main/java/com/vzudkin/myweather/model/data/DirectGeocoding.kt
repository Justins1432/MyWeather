package com.vzudkin.myweather.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DirectGeocoding(
    @SerializedName("name") val name: String,
    @SerializedName("local_names") val localNames: LocalNames,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("country") val country: String,
    @SerializedName("state") val state: String?
) : Serializable

data class LocalNames(
    @SerializedName("be") val be: String?,
    @SerializedName("fr") val fr: String?,
    @SerializedName("pl") val pl: String?,
    @SerializedName("de") val de: String?,
    @SerializedName("da") val da: String?,
    @SerializedName("tt") val tt: String?,
    @SerializedName("ko") val ko: String?,
    @SerializedName("ro") val ro: String?,
    @SerializedName("uz") val uz: String?,
    @SerializedName("feature_name") val fName: String?,
    @SerializedName("ba") val ba: String?,
    @SerializedName("az") val az: String?,
    @SerializedName("ku") val ku: String?,
    @SerializedName("en") val en: String?,
    @SerializedName("ascii") val ascii: String?,
    @SerializedName("uk") val uk: String?,
    @SerializedName("mn") val mn: String?,
    @SerializedName("hy") val hy: String?,
    @SerializedName("hu") val hu: String?,
    @SerializedName("it") val it: String?,
    @SerializedName("ru") val ru: String?,
    @SerializedName("lt") val lt: String?,
    @SerializedName("kn") val kn: String?,
    @SerializedName("sk") val sk: String?,
    @SerializedName("pt") val pt: String?,
    @SerializedName("ca") val ca: String?,
    @SerializedName("ar") val ar: String?,
    @SerializedName("fi") val fi: String?,
    @SerializedName("zh") val zh: String?,
    @SerializedName("eu") val eu: String?,
    @SerializedName("ka") val ka: String?,
    @SerializedName("sl") val sl: String?,
    @SerializedName("hi") val hi: String?,
    @SerializedName("cs") val cs: String?,
    @SerializedName("hr") val hr: String?,
    @SerializedName("ja") val ja: String?,
    @SerializedName("oc") val oc: String?,
    @SerializedName("et") val et: String?,
    @SerializedName("cv") val cv: String?,
    @SerializedName("bg") val bg: String?,
    @SerializedName("es") val es: String?,
    @SerializedName("te") val te: String?,
    @SerializedName("he") val he: String?,
    @SerializedName("kk") val kk: String?,
    @SerializedName("fa") val fa: String?,
    @SerializedName("ml") val ml: String?
)


