package hr.jjpietri.rocketlaunchinfo.api

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<SpaceCraft>
)