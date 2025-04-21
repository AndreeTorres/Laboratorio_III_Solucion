package com.torres.labo03.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.torres.labo03.data.Task
import com.torres.labo03.ui.components.FormCard
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold() {
    val hidden: MutableState<Boolean> = remember { mutableStateOf(false) }
    val tasks = remember { mutableListOf<Task>() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { hidden.value = !hidden.value }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top

        ) {
            if (hidden.value) {// Inside if
                Dialog(
                    onDismissRequest = { hidden.value = false },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    )
                ) {
                    FormCard { task ->
                        tasks.add(task)
                        hidden.value = false
                    }
                }
            }// Outside if
            tasks.forEach { task ->
                var isChecked by remember { mutableStateOf(task.isDone) }

                Card(
                    modifier = Modifier
                        .padding(
                            horizontal = 20.dp,
                            vertical = 10.dp
                        )
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (!isChecked) Color(76, 175, 80) else Color.Gray,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    )
                ) {
                    // Estado local del checkbox

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = task.title,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )

                        Text(
                            text = task.description,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            maxLines = 3,
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { newValue ->
                                        isChecked = newValue
                                        task.isDone = newValue // Almacenándolo en el modelo
                                    }
                                )
                                Text(text = formatMillisToDate(task.date))
                            }
                        }
                    }
                }
            }
        }
    }
}


fun formatMillisToDate(millis: Long?): String {
    return if (millis != null) {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formatter.format(millis)
    } else {
        "No date selected"
    }
}