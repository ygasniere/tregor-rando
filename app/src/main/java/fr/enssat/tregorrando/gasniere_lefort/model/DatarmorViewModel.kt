package fr.enssat.tregorrando.gasniere_lefort.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import fr.enssat.tregorrando.gasniere_lefort.data.DataFetcher
import fr.enssat.tregorrando.gasniere_lefort.data.json.Types

class DatarmorViewModel: ViewModel() {
    val randos = mutableStateListOf<Types>()

    init {
        DataFetcher().apply {
            fetchRandos { list -> randos.addAll(list) }
        }
    }

    fun getRandoWithCoord() {
        DataFetcher().apply {
            fetchRandoCoordinates { list -> randos.addAll(list) }
        }
    }
}