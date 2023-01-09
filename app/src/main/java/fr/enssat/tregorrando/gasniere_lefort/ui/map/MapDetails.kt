package fr.enssat.tregorrando.gasniere_lefort.ui.map

import android.graphics.Point
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import fr.enssat.tregorrando.gasniere_lefort.data.json.Types
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

@Composable
fun MapDetails(rando: Types) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        val enssatCoord = GeoPoint(48.6447666768803, -3.621320067061617)
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                MapView(context).apply {
                    val rotationGestureOverlay = RotationGestureOverlay(this)
                    rotationGestureOverlay.isEnabled
                    setMultiTouchControls(true)
                    overlays.add(rotationGestureOverlay)

                    controller.setCenter(enssatCoord)
                    controller.setZoom(18.0)

                    val startPoint = Marker(this).apply {
                        position = enssatCoord
                        title = "Départ"
                    }
                    overlays.add(startPoint)
                    val polyline = Polyline(this)
                    /*val line: List<GeoPoint> =
                        listOf(GeoPoint(48.7287, -3.4631), GeoPoint(enssatCoord.latitude,enssatCoord.longitude-0.0001))
                    polyline.setPoints(line)
                    overlays.add(polyline)*/


                    val points: ArrayList<GeoPoint> = arrayListOf()
                    if (rando.geometry?.type === "MultiLineString") {
                        rando.geometry.coordinates as List<List<List<Double>>>
                        for (x in rando.geometry.coordinates) {
                            for (y in x) {
                                points.add(GeoPoint(y[1], y[0]))
                            }
                            polyline.setPoints(points)
                        }
                        overlays.add(polyline)
                    } else if (rando.geometry?.type === "LineString") {
                        rando.geometry.coordinates as List<List<Double>>
                        for (z in rando.geometry.coordinates) {
                            points.add(GeoPoint(z[1], z[0]))
                        }
                        polyline.setPoints(points)
                        overlays.add(polyline)
                    }
                }
            })
    }
}