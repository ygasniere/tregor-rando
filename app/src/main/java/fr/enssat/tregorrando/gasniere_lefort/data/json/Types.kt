package fr.enssat.tregorrando.gasniere_lefort.data.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Types(
    val type: String,
    val properties: Properties,
    val geometry: Geometry?,
) {
    enum class Type {
        @Json(name = "Pédestre") PEDESTRE,
        @Json(name = "VTT") VTT,
        @Json(name = "Vélo") VELO,
        @Json(name = "Equestre") EQUESTRE
    }

    data class Properties(
        @Json(name= "id__")        val idKey: String,
        @Json(name= "iti_id")      val id: Int,
        @Json(name= "iti_com_li")  val lieu: String,
        @Json(name= "iti_long")    val longueur: Double,
        @Json(name= "iti_com_in")  val insee: Int,
        @Json(name= "iti_balisa")  val balisage: String?,
        @Json(name= "iti_sens_p")  val sensParcours: String?,
        @Json(name= "iti_vocati")  val vocation: Type?,
        @Json(name= "iti_nom")     val nom: String
    )

    data class Geometry(
        @Json(name= "type") val type: String,
        @Json(name= "coordinates") val coordinates: List<List<Any>>
    )

}