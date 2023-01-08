package fr.enssat.tregorrando.gasniere_lefort.ui.map

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import fr.enssat.tregorrando.gasniere_lefort.ui.map.MapDetails
import fr.enssat.tregorrando.gasniere_lefort.ui.theme.TregorRandoTheme
import org.osmdroid.config.Configuration

class MapActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuration pour Open Street Map
        Configuration.getInstance().load(this, getSharedPreferences("osm", Context.MODE_PRIVATE))

        setContent {
            TregorRandoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) { MapDetails() }
            }
        }
    }
}