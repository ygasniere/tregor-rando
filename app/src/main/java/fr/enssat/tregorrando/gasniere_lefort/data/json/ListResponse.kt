package fr.enssat.tregorrando.gasniere_lefort.data.json

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListResponse(
    val type: String,
    val features: List<Types>
)