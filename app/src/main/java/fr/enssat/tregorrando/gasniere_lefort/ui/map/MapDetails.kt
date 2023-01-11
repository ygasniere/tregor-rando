package fr.enssat.tregorrando.gasniere_lefort.ui.map

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
fun MapDetails(hike: Types) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                MapView(context).apply {
                    val rotationGestureOverlay = RotationGestureOverlay(this)
                    rotationGestureOverlay.isEnabled
                    setMultiTouchControls(true)
                    overlays.add(rotationGestureOverlay)
                    controller.setZoom(18.0)

                    var polyline = Polyline(this)
                    var points: ArrayList<GeoPoint> = arrayListOf()
                    var startingCoordinates = GeoPoint(0.0, 0.0)

                    if (hike.geometry?.type == "MultiLineString") {
                        hike.geometry.coordinates as List<List<List<Double>>>
                        startingCoordinates = GeoPoint(hike.geometry.coordinates[0][0][1], hike.geometry.coordinates[0][0][0])
                        controller.setCenter(startingCoordinates)
                        for (segment in hike.geometry.coordinates) {
                            polyline = Polyline()
                            points = arrayListOf()
                            for (point in segment) {
                                points.add(GeoPoint(point[1], point[0]))
                            }
                            polyline.setPoints(points)
                            overlays.add(polyline)
                        }
                    } else if (hike.geometry?.type == "LineString") {
                        hike.geometry.coordinates as List<List<Double>>
                        startingCoordinates = GeoPoint(hike.geometry.coordinates[0][1], hike.geometry.coordinates[0][0])
                        for (point in hike.geometry.coordinates) {
                            points.add(GeoPoint(point[1], point[0]))
                        }
                        polyline.setPoints(points)
                        overlays.add(polyline)
                    }

                    val startingPoint = Marker(this).apply {
                        position = startingCoordinates
                        title = "Start"
                    }
                    overlays.add(startingPoint)
                    controller.setCenter(startingCoordinates)
                }
            })
    }
}