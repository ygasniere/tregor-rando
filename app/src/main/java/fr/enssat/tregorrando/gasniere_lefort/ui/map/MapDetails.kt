package fr.enssat.tregorrando.gasniere_lefort.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

@Composable
fun MapDetails() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        val enssatCoord = GeoPoint(48.72973296156721, -3.462353271164552)
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
                        title = "Enssat"
                    }
                    overlays.add(startPoint)
                    val polyline = Polyline(this)
                    val line: List<GeoPoint> =
                        listOf(GeoPoint(48.7287, -3.4631), GeoPoint(enssatCoord.latitude,enssatCoord.longitude-0.0001))
                    polyline.setPoints(line)
                    overlays.add(polyline)
                }
            })
    }
}