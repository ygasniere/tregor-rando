package fr.enssat.tregorrando.gasniere_lefort.ui.list

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import fr.enssat.tregorrando.gasniere_lefort.data.json.Types
import fr.enssat.tregorrando.gasniere_lefort.model.DatarmorViewModel
import fr.enssat.tregorrando.gasniere_lefort.ui.map.MapActivity

@Composable
fun RandoList(viewModel: DatarmorViewModel) {
    val context = LocalContext.current

    Column {
        Text(text = "Randos du Trégor", modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.h4)
        Divider(color = Color.Black)
        val hikes: List<Types> = viewModel.hikes
        LazyColumn {
            items(hikes) { hike: Types ->
                ClickableText(
                    text = AnnotatedString(hike.properties.nom),
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    onClick = {
                        Log.d("SELECT", "selected: $hike")
                        context.startActivity(Intent(context, MapActivity::class.java))
                    }
                )
                Divider(color = Color.Black)
            }
        }
    }
}