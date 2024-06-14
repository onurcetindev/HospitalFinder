package com.example.mobproject.composables.hospital_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar


@Composable
fun DateTimeSelection(
    enabled: Boolean,
    selectedDate: String?,
    selectedTime: String?,
    onDateSelected: (String) -> Unit,
    onTimeSelected: (String) -> Unit
) {
    if (!enabled) return
    Column {
        Text("Choose Date:")
        DatePicker(
            selectedDate = selectedDate,
            onDateSelected = onDateSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Choose Date:")
        TimePicker(
            selectedTime = selectedTime,
            onTimeSelected = onTimeSelected
        )
    }
}

@Composable
fun DatePicker(
    selectedDate: String?,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var date by remember { mutableStateOf(selectedDate ?: "") }

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            date = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            onDateSelected(date)
        },
        year,
        month,
        day
    )

    OutlinedTextField(
        value = date,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, Color.Gray),
        placeholder = { Text("Choose Date") },
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(Icons.Default.CalendarToday, contentDescription = null)
            }
        },
        readOnly = true
    )
}

@Composable
fun TimePicker(
    selectedTime: String?,
    onTimeSelected: (String) -> Unit
) {
    val timeSlots = listOf(
        "09:30", "10:00", "10:30",
        "11:00", "11:30", "12:00",
        "12:30", "13:00", "13:30",
        "14:00", "14:30", "15:00",
        "15:30", "16:00", "16:30"
    )
    var selectedSlot by remember { mutableStateOf(selectedTime) }

    Column {
        for (i in timeSlots.indices step 3) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                for (j in 0 until 3) {
                    if (i + j < timeSlots.size) {
                        OutlinedButton(
                            onClick = {
                                selectedSlot = timeSlots[i + j]
                                onTimeSelected(timeSlots[i + j])
                            },
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f),
                            border = if (selectedSlot == timeSlots[i + j]) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else BorderStroke(1.dp, Color.Gray),
                        ) {
                            Text(timeSlots[i + j], fontSize = 18.sp)
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
