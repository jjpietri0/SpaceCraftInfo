package hr.jjpietri.rocketlaunchinfo.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://ll.thespacedevs.com/2.2.0/config/spacecraft/"

interface SpaceCraftApi {

    @GET("?format=json&human_rated=true&manufacturer=&name=")
    fun fetchItems(): Call<ApiResponse>
}