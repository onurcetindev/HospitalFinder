package com.example.mobproject.ui.screen.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

data class Hospital(val name: String, val latitude: Double, val longitude: Double)

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    navController: NavController? = null,
    context: Context = LocalContext.current,
) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var nearbyHospitals by remember { mutableStateOf<List<Hospital>>(emptyList()) }
    val antalya = LatLng(36.88414000, 30.70563000) // Varsayılan konum Antalya
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(antalya, 15f)
    }

    LaunchedEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted) {
            getCurrentLocation(context) { location ->
                userLocation = location ?: antalya
                cameraPositionState.position = CameraPosition.fromLatLngZoom(userLocation!!, 15f)
            }
        } else {
            permissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(userLocation) {
        userLocation?.let {
            nearbyHospitals = fetchNearbyHospitals(it)
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
        
    ) {
        nearbyHospitals.forEach { hospital ->
            Marker(
                state = MarkerState(position = LatLng(hospital.latitude, hospital.longitude)),
                title = hospital.name

            )
        }
    }
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onLocationReceived: (LatLng?) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            onLocationReceived(LatLng(location.latitude, location.longitude))
        } else {
            onLocationReceived(null)
        }
    }.addOnFailureListener {
        onLocationReceived(null) // Başarısız olursa null döndür
    }
}

suspend fun fetchNearbyHospitals(location: LatLng): List<Hospital> {
    val apiKey = "AIzaSyAtcTm3CJHafsnCcowGCJkSTNZ-v-SUcl4"

    return withContext(Dispatchers.IO) {
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=${location.latitude},${location.longitude}" +
                "&radius=5000" +
                "&type=hospital" +
                "&key=$apiKey"

        val connection = URL(url).openConnection() as HttpsURLConnection
        val inputStream = connection.inputStream
        val response = inputStream.bufferedReader().use { it.readText() }
        connection.disconnect()

        return@withContext parseHospitalsResponse(response)
    }
}

fun parseHospitalsResponse(response: String): List<Hospital> {
    val hospitals = mutableListOf<Hospital>()
    val jsonObject = JSONObject(response)
    val resultsArray = jsonObject.getJSONArray("results")

    for (i in 0 until resultsArray.length()) {
        val hospitalObject = resultsArray.getJSONObject(i)
        val name = hospitalObject.getString("name")
        val geometry = hospitalObject.getJSONObject("geometry")
        val location = geometry.getJSONObject("location")
        val latitude = location.getDouble("lat")
        val longitude = location.getDouble("lng")

        hospitals.add(Hospital(name, latitude, longitude))
    }

    return hospitals
}
