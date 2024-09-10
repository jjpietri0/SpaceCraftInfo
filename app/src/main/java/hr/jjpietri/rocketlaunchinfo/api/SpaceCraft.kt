package hr.jjpietri.rocketlaunchinfo.api

import com.google.gson.annotations.SerializedName

data class SpaceCraft(

    @SerializedName("id") val id : Long?,
    @SerializedName("url") val url : String,
    @SerializedName("name") val name : String,
    @SerializedName("agency") val agency : Agency,
    @SerializedName("in_use") val in_use : Boolean,
    @SerializedName("capability") val capability : String,
    @SerializedName("maiden_flight") val maiden_flight : String,
    @SerializedName("human_rated") val human_rated : Boolean,
    @SerializedName("crew_capacity") val crew_capacity : Int,
    @SerializedName("image_url") val image_url : String,

    )

data class Agency(
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("country_code") val country_code : String,
    @SerializedName("description") val description : String,
    @SerializedName("administrator") val administrator : String,
    @SerializedName("founding_year") val founding_year : Int,
    @SerializedName("launchers") val launchers : String,
    @SerializedName("image_url") val image_url : String,
)