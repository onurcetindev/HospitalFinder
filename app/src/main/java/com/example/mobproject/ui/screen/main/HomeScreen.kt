package com.example.mobproject.ui.screen.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobproject.composables.home_screen.Appointment
import com.example.mobproject.composables.home_screen.ExpandableSection
import com.example.mobproject.composables.home_screen.UpdateAppointmentDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Firestore'dan randevuları çekmek için bir işlev
suspend fun fetchAppointmentsFromFirestore(): List<Appointment> {
    val appointmentList = mutableListOf<Appointment>()
    try {
        val querySnapshot = FirebaseFirestore.getInstance()
            .collection("appointments")
            .get()
            .await()

        for (document in querySnapshot.documents) {
            val hospital = document.getString("hospital") ?: ""
            val polyclinic = document.getString("polyclinic") ?: ""
            val date = document.getString("date") ?: ""
            val time = document.getString("time") ?: ""

            val appointment = Appointment(hospital, polyclinic, date, time)
            appointmentList.add(appointment)
        }
    } catch (e: Exception) {
        // Handle any potential exceptions here
        e.printStackTrace()
    }
    return appointmentList
}

@Composable
fun HomeScreen(
    navController: NavController? = null,
    context: Context = LocalContext.current,
) {
    var appointments by remember { mutableStateOf<List<Appointment>>(emptyList()) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedAppointment by remember { mutableStateOf<Appointment?>(null) }

    // Firestore'dan randevuları çekmek için suspend işlevini kullan
    LaunchedEffect(Unit) {
        val fetchedAppointments = fetchAppointmentsFromFirestore()
        appointments = fetchedAppointments
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ExpandableSection(
            title = "Appointments",
            appointments = appointments,
            onAppointmentUpdate = { appointment ->
                selectedAppointment = appointment
                showUpdateDialog = true
            },
            onAppointmentDelete = { appointment ->
                selectedAppointment = appointment
                showDeleteDialog = true
            }
        )
    }

    if (showUpdateDialog && selectedAppointment != null) {
        UpdateAppointmentDialog(
            appointment = selectedAppointment!!,
            onDismiss = { showUpdateDialog = false },
            onUpdate = { updatedAppointment ->
                // Güncelleme işlemlerini yap
                appointments = appointments.map { appointment ->
                    if (appointment == selectedAppointment) {
                        updatedAppointment
                    } else {
                        appointment
                    }
                }
                showUpdateDialog = false
            }
        )
    }

    if (showDeleteDialog && selectedAppointment != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Appointment will be deleted") },
            text = { Text("Are you sure you want to delete this appointment?") },
            confirmButton = {
                Button(
                    onClick = {
                        appointments = appointments.filterNot { it == selectedAppointment }
                        showDeleteDialog = false
                    }
                ) {
                    Text("Evet")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Hayır")
                }
            }
        )
    }
}
