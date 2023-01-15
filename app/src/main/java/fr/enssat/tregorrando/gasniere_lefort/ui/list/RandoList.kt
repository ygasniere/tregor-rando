package fr.enssat.tregorrando.gasniere_lefort.ui.list

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import fr.enssat.tregorrando.gasniere_lefort.data.json.Types
import fr.enssat.tregorrando.gasniere_lefort.model.DatarmorViewModel
import fr.enssat.tregorrando.gasniere_lefort.ui.map.MapActivity
import fr.enssat.tregorrando.gasniere_lefort.ui.theme.Purple200
import fr.enssat.tregorrando.gasniere_lefort.ui.theme.Purple500
import fr.enssat.tregorrando.gasniere_lefort.ui.theme.Purple700
import java.util.*

@Composable
fun RandoList(viewModel: DatarmorViewModel) {
    val context = LocalContext.current

    val hikes: List<Types> = viewModel.hikes
    var displayedHikes: List<Types>

    val filterState = remember { mutableStateOf(TextFieldValue("")) }
    val vocationState = remember { mutableStateOf(listOf(
        Pair(Types.Type.PEDESTRE, true),
        Pair(Types.Type.VELO, true),
        Pair(Types.Type.VTT, true),
        Pair(Types.Type.EQUESTRE, true),
    )) }

    val vocations: List<Types.Type> = listOf(Types.Type.PEDESTRE, Types.Type.VELO, Types.Type.VTT, Types.Type.EQUESTRE)

    Column {
        val filter = filterState.value.text.lowercase(Locale.getDefault())
        displayedHikes = if (filter.isEmpty()) {
            hikes.filter { hike ->
                if(hike.properties.vocation != null) vocationState.value.first { hike.properties.vocation == it.first }.second
                else true
            }
        } else {
            hikes.filter { hike ->
                (hike.properties.nom.lowercase(Locale.getDefault()).contains(filter) || hike.properties.lieu.lowercase(Locale.getDefault()).contains(filter)) &&
                        vocationState.value.first { it.first == hike.properties.vocation }.second
            }
        }
        Text(text = "Randos du Trégor", modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.h4, color = Purple700)
        Divider(color = Purple700)
        FilterView(filterState)
        Divider(color = Purple700)
        LazyRow(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(vocations) { vocation: Types.Type ->
                TextButton(
                    onClick = { vocationState.value = vocationState.value.map {
                        if(it.first == vocation) Pair(it.first, !it.second)
                        else it
                    } },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = if (vocationState.value.first { it.first == vocation }.second) Purple500 else Purple200
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = vocation.name, color = if (vocationState.value.first { it.first == vocation }.second) Color.White else Purple700)
                }
            }
        }
        Divider(color = Purple700)
        LazyColumn {
            items(displayedHikes) { hike: Types ->
                Row(
                    modifier = Modifier
                        .clickable(onClick = {
                            context.startActivity(Intent(context, MapActivity::class.java).apply {
                                putExtra("selectedHikeIndex", displayedHikes.indexOf(hike))
                            })
                        })
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp, end = 20.dp), text = AnnotatedString(hike.properties.nom), color = Purple700)
                        Text(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp), text = AnnotatedString("${hike.properties.lieu}, ${hike.properties.longueur/1000} km"), color = Purple200)
                        Divider(color = Purple700, modifier = Modifier.padding(top = 20.dp))
                    }
                }
            }
        }
    }
}