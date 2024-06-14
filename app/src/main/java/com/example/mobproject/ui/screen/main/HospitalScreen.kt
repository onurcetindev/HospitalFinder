package com.example.mobproject.ui.screen.main

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobproject.composables.hospital_screen.AppointmentButton
import com.example.mobproject.composables.hospital_screen.DateTimeSelection
import com.example.mobproject.composables.hospital_screen.HospitalSelection
import com.example.mobproject.composables.hospital_screen.PolyclinicSelection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HospitalScreen(
    navController: NavController? = null,
    context: Context = LocalContext.current,
) {
    var selectedHospital by remember { mutableStateOf<String?>(null) }
    var selectedPolyclinic by remember { mutableStateOf<String?>(null) }
    var selectedDate by remember { mutableStateOf<String?>(null) }
    var selectedTime by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            HospitalSelection(
                selectedHospital = selectedHospital,
                onHospitalSelected = {
                    selectedHospital = it
                    selectedPolyclinic = null
                    selectedDate = null
                    selectedTime = null
                }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            PolyclinicSelection(
                enabled = selectedHospital != null,
                selectedPolyclinic = selectedPolyclinic,
                onPolyclinicSelected = {
                    selectedPolyclinic = it
                    selectedDate = null
                    selectedTime = null
                }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            DateTimeSelection(
                enabled = selectedPolyclinic != null,
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                onDateSelected = { selectedDate = it },
                onTimeSelected = { selectedTime = it }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            AppointmentButton(
                enabled = selectedHospital != null && selectedPolyclinic != null && selectedDate != null && selectedTime != null,
                onClick = {
                    if (currentUser != null) {
                        val appointment = hashMapOf(
                            "hospital" to selectedHospital,
                            "polyclinic" to selectedPolyclinic,
                            "date" to selectedDate,
                            "time" to selectedTime,
                            "userId" to currentUser.uid
                        )

                        db.collection("appointments")
                            .add(appointment)
                            .addOnSuccessListener { documentReference ->
                                dialogMessage = "Appointment successfully created: ${documentReference.id}"
                                showDialog = true
                                println(dialogMessage)
                            }
                            .addOnFailureListener { e ->
                                dialogMessage = "Error creating appointment: ${e.message}"
                                showDialog = true
                                println(dialogMessage)
                            }
                    } else {
                        dialogMessage = "User not authenticated"
                        showDialog = true
                    }
                }
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Appointment Status") },
            text = { Text(dialogMessage) },
            confirmButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("Ok")
                }
            }
        )
    }
}
