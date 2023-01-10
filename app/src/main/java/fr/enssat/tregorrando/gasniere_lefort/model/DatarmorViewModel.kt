package fr.enssat.tregorrando.gasniere_lefort.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import fr.enssat.tregorrando.gasniere_lefort.data.DataFetcher
import fr.enssat.tregorrando.gasniere_lefort.data.json.Types

class DatarmorViewModel: ViewModel() {
    val hikes = mutableStateListOf<Types>()

    init {
        DataFetcher().apply {
            fetchHikes { list -> hikes.addAll(list) }
        }
    }
}