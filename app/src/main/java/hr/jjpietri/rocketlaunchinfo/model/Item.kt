package hr.jjpietri.rocketlaunchinfo.model

data class Item(
    var _id: Long?,
    val name: String,
    val inUse: Boolean,
    val capability: String,
    val maidenFlight: String,
    val humanRated: Boolean,
    val crewCapacity: Int,
    val picturePath: String,
    val imageUrl: String,
    var read: Boolean = false
)