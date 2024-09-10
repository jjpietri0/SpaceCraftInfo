package hr.jjpietri.rocketlaunchinfo.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.jjpietri.rocketlaunchinfo.ROCKET_LAUNCH_PROVIDER_CONTENT_URI
import hr.jjpietri.rocketlaunchinfo.RocketLaunchReceiver
import hr.jjpietri.rocketlaunchinfo.framework.sendBroadcast
import hr.jjpietri.rocketlaunchinfo.handler.downloadAndStore
import hr.jjpietri.rocketlaunchinfo.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpaceCraftFetcher(private val context: Context) {

    private val spaceCraftApi: SpaceCraftApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        spaceCraftApi = retrofit.create(SpaceCraftApi::class.java)
    }

    fun fetchItems() {
        val request = spaceCraftApi.fetchItems()
        request.enqueue(object: Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                response.body()?.results?.let { spaceCrafts ->
                    val items = spaceCrafts.map { spaceCraft ->
                        Item(
                            _id = spaceCraft.id,
                            name = spaceCraft.name,
                            inUse = spaceCraft.in_use,
                            capability = spaceCraft.capability,
                            maidenFlight = spaceCraft.maiden_flight,
                            humanRated = spaceCraft.human_rated,
                            crewCapacity = spaceCraft.crew_capacity,
                            imageUrl = spaceCraft.image_url,
                            picturePath = ""
                        )
                    }
                    populateItems(items)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e(javaClass.name, t.toString(), t)
            }
        })
    }

    private fun populateItems(items: List<Item>) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            items.forEach {
                val picturePath = downloadAndStore(context, it.imageUrl)
                val values = ContentValues().apply {
                    put(Item::name.name, it.name)
                    put(Item::crewCapacity.name, it.crewCapacity)
                    put(Item::capability.name, it.capability)
                    put(Item::imageUrl.name, it.imageUrl)
                    put(Item::picturePath.name, picturePath)
                    put(Item::humanRated.name, it.humanRated)
                    put(Item::maidenFlight.name, it.maidenFlight)
                    put(Item::inUse.name, it.inUse)
                    put(Item::read.name, false)
                }
                context.contentResolver.insert(ROCKET_LAUNCH_PROVIDER_CONTENT_URI, values)
            }
            context.sendBroadcast<RocketLaunchReceiver>()
        }
    }
}