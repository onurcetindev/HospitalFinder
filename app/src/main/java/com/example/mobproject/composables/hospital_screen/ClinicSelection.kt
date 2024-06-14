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
fun PolyclinicSelection(
    enabled: Boolean,
    selectedPolyclinic: String?,
    onPolyclinicSelected: (String) -> Unit
) {
    if (!enabled) return
    val polyclinics = listOf(
        "Psychology",
        "Physiotherapy",
        "Ear, nose and throat",
        "Cardiovascular diseases",
        "Dental"
    )

    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text("Choose Clinic:")
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .border(1.dp, Color.Gray),
            placeholder = { Text("Clinic Name") }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            polyclinics.forEach { polyclinic ->
                DropdownMenuItem(
                    text = { Text(polyclinic) },
                    onClick = {
                        text = polyclinic
                        expanded = false
                    }
                )
            }
        }

        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            Text("Select Clinic")
        }

        Button(
            onClick = { onPolyclinicSelected(text) }, // text.text'ten text'e düzeltildi
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
    }
}
