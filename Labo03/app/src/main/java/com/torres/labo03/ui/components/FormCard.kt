package com.torres.labo03.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.torres.labo03.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormCard(
    onTaskCreated: (Task) -> Unit
) {
    val hidden: MutableState<Boolean> = remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isDone by remember { mutableStateOf(false) }
    val selectedDateMillis = remember { mutableStateOf<Long?>(null) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis.value)

    Card(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Add your task here",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        Button(onClick = { hidden.value = true }) {
            Icon(Icons.Filled.DateRange, contentDescription = "Date")
        }
        if (hidden.value) {
            DatePickerDialog(
                onDismissRequest = { hidden.value = false },
                confirmButton = {
                    Button(onClick = {
                        selectedDateMillis.value = datePickerState.selectedDateMillis
                        hidden.value = false
                    }) {
                        Text("OK")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Button(
            onClick = {
                val task = Task(
                    title = title,
                    description = description,
                    isDone = isDone,
                    date = selectedDateMillis.value
                )
                onTaskCreated(task)
            }
        ) {
            Text(text = "Save task")
        }
    }
}