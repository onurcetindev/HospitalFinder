package com.example.mobproject.composables.home_screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun ExpandableSection(
    title: String,
    appointments: List<Appointment>,
    onAppointmentUpdate: ((Appointment) -> Unit)?,
    onAppointmentDelete: (Appointment) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }
        if (expanded) {
            LazyColumn {
                items(appointments.size) { index ->
                    AppointmentItem(
                        appointment = appointments[index],
                        onUpdate = onAppointmentUpdate,
                        onDelete = onAppointmentDelete
                    )
                }
            }
        }
    }
}

@Composable
fun AppointmentItem(
    appointment: Appointment,
    onUpdate: ((Appointment) -> Unit)?,
    onDelete: (Appointment) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Hospital: ${appointment.hospitalName}", fontSize = 16.sp)
                Text("Polyclinic: ${appointment.polyclinic}", fontSize = 16.sp)
                Text("Date: ${appointment.date}", fontSize = 16.sp)
                Text("Time: ${appointment.time}", fontSize = 16.sp)
            }
            if (onUpdate != null) {
                IconButton(onClick = { onUpdate(appointment) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Update", tint = Color.Blue)
                }
            }
            IconButton(onClick = { onDelete(appointment) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }
    }
}

@Composable
fun UpdateAppointmentDialog(
    appointment: Appointment,
    onDismiss: () -> Unit,
    onUpdate: (Appointment) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var newDate by remember { mutableStateOf(appointment.date) }
    var newTime by remember { mutableStateOf(appointment.time) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            newDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        },
        year, month, day
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Appointment") },
        text = {
            Column {
                OutlinedTextField(
                    value = newDate,
                    onValueChange = { newDate = it },
                    label = { Text("Date") },
                    trailingIcon = {
                        IconButton(onClick = { datePickerDialog.show() }) {
                            Icon(Icons.Default.CalendarToday, contentDescription = null)
                        }
                    },
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newTime,
                    onValueChange = { newTime = it },
                    label = { Text("Time") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedAppointment = appointment.copy(date = newDate, time = newTime)
                    onUpdate(updatedAppointment)
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
object AppointmentStore {
    var selectedHospital by mutableStateOf<String?>(null)
    var selectedPolyclinic by mutableStateOf<String?>(null)
    var selectedDate by mutableStateOf<String?>(null)
    var selectedTime by mutableStateOf<String?>(null)

    fun clear() {
        selectedHospital = null
        selectedPolyclinic = null
        selectedDate = null
        selectedTime = null
    }
}

data class Appointment(
    val hospitalName: String,
    val polyclinic: String,
    val date: String,
    val time: String
)