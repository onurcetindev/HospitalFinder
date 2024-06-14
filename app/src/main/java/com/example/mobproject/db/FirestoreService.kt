package com.example.mobproject.db

import com.example.mobproject.composables.home_screen.Appointment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirestoreService {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getAppointments(): List<Appointment> {
        val snapshot = db.collection("appointments").get().await()
        return snapshot.documents.mapNotNull { it.toObject(Appointment::class.java) }
    }

    suspend fun addAppointment(appointment: Appointment): Boolean {
        return try {
            db.collection("appointments").add(appointment).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
