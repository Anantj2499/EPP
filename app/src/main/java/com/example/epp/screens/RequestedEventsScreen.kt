package com.example.epp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.epp.models.Event

@Composable
fun RequestedEventsScreen(onEventClick: (Event) -> Unit) {
    val events = remember { mutableStateListOf<Event>() } // Replace with your list of events

    LazyColumn {
        items(events) { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEventClick(event) }
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = event.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = event.organizingClub, style = MaterialTheme.typography.titleSmall)
                }
            }
        }
    }
}

@Composable
fun EventDetailWithButtonsScreen(event: Event) {
    var showTextField by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf("") }

    Column {
        EventDetailScreen(event = event)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Handle Accept */ }) {
                Text("Accept")
            }

            Button(onClick = { /* Handle Deny */ }) {
                Text("Deny")
            }

            Button(onClick = { showTextField = true }) {
                Text("Request Changes")
            }
        }

        if (showTextField) {
            TextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            Button(
                onClick = { /* Handle Send */ },
                modifier = Modifier.align(Alignment.End).padding(8.dp)
            ) {
                Text("Send")
            }
        }
    }
}