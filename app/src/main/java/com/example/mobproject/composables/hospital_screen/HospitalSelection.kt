package com.example.mobproject.composables.hospital_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun HospitalSelection(
    selectedHospital: String?,
    onHospitalSelected: (String) -> Unit
) {
    Column {
        Text("Choose Hospital:")

        var text by remember { mutableStateOf(TextFieldValue()) }
        var expanded by remember { mutableStateOf(false) }
        val hospitals = listOf(
            "Akdeniz University Hospital",
            "Acıbadem Hospital",
            "OpenEye Eye Clinic",
            "Medipol Hospital",
            "Antalya Hospital"
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .border(4.dp, Color.Gray)
                .padding(bottom = 3.dp),
            placeholder = { Text("Hospital Name") },
            readOnly = true,
            singleLine = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
        ) {
            hospitals.forEach { hospital ->
                DropdownMenuItem(
                    text = { Text(hospital) },
                    onClick = {
                        text = TextFieldValue(hospital)
                        expanded = false
                    }
                )
            }
        }

        Button(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Select Hospital")
        }

        Button(
            onClick = { onHospitalSelected(text.text) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
    }
}
