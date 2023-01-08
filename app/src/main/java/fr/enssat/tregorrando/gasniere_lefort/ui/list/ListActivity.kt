package fr.enssat.tregorrando.gasniere_lefort.ui.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.enssat.tregorrando.gasniere_lefort.model.DatarmorViewModel
import fr.enssat.tregorrando.gasniere_lefort.ui.theme.TregorRandoTheme

class ListActivity : ComponentActivity() {
    companion object {
        val factory = object: ViewModelProvider.Factory {
            val randosViewModel = DatarmorViewModel()

            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return randosViewModel as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: DatarmorViewModel by viewModels { factory }
        setContent {
            TregorRandoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RandoList(viewModel = viewModel)
                }
            }
        }
    }
}