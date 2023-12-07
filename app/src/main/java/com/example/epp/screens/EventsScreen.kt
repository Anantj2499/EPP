package com.example.epp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.epp.R
import com.example.epp.models.Event
@Composable
fun EventsScreen(
    onAddEventClicked: () -> Unit,
    ) {
    Box(Modifier.fillMaxSize()){
        Column {
            ExpandableCard(title = "Upcoming Events") {
                EventList(events = emptyList())
            }
            ExpandableCard(title = "Ongoing Events") {
                EventList(events = emptyList())
            }
            ExpandableCard(title = "Completed Events") {
                EventList(events = emptyList())
            }
        }
        IconButton(onClick = onAddEventClicked,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(percent = 50))
                .shadow(4.dp, RoundedCornerShape(percent = 50))
                .size(56.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )){
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.add_event_button),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
            )
        }
    }
}
@Composable
fun ExpandableCard(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, modifier = Modifier.padding(8.dp))
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
            if (expanded) {
                content()
            }
        }
    }
}
@Composable
fun EventItem(event: Event) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(200.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = event.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = event.name,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
@Composable
fun EventList(events: List<Event>) {
    if (events.isEmpty()) {
        Text(text = "Nothing to show up here", modifier = Modifier.padding(8.dp))
    } else {
        LazyRow {
            items(events) { event ->
                EventItem(event = event)
            }
        }
    }
}