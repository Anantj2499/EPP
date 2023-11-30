package com.example.epp.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.epp.R
import com.example.epp.models.Event
import com.example.epp.viewModel.EventViewModel
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar

@Composable
fun AddEventScreen(onCancelButtonClicked: () -> Unit = {}, onNexButtonClicked: () -> Unit) {
    val eventViewModel : EventViewModel = viewModel()
    val event by eventViewModel.uiState.collectAsState()

    // Add validation for other fields

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            TextField(
                value = event.name,
                onValueChange = { eventViewModel.setEventName(it) },
                label = { Text("Event Name") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            TextField(
                value = event.organizingClub,
                onValueChange = { eventViewModel.setOrganizingClub(it) },
                label = { Text("Organizing Club Name") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            TextField(
                value = event.clubCoordinator,
                onValueChange = { eventViewModel.setClubCoordinator(it) },
                label = { Text("Club Coordinator") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
            TextField(
                value = event.eventDetails,
                onValueChange = { eventViewModel.setEventDetails(it) },
                label = { Text("Event Details") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                TextField(
                    value = event.noOfVolunteers,
                    onValueChange = { eventViewModel.setNoOfVolunteers(it) },
                    label = { Text("No. of Volunteers") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = event.noOfAttendees,
                    onValueChange = { eventViewModel.setNoOfAttendees(it) },
                    label = { Text("No. of Attendees") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
            }
            TextField(
                value = event.fee,
                onValueChange = { eventViewModel.setFee(it) },
                label = { Text("Fee") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = { Text("₹") }
            )
            TextField(
                value = event.organizer,
                onValueChange = { eventViewModel.setOrganizer(it) },
                label = { Text("Organizer Name") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            // Add other fields with validation
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                DatePickerDialog(eventViewModel)
                TimePickerDialog(eventViewModel)
            }

        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedButton(modifier = Modifier.weight(1f),
                onClick = {
                    eventViewModel.resetEvent()
                    onCancelButtonClicked()
                }) {
                Text(stringResource(R.string.cancel))
            }
            Button(
                modifier = Modifier.weight(1f),
                // the button is enabled when the user makes a selection
                //enabled = selectedValue.isNotEmpty(),
                onClick = {
                    if (validateEvent(event)) {
                        eventViewModel.setEvent(event)
                        onNexButtonClicked()
                    }else{}
                }
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}

@Composable
fun DatePickerDialog(eventViewModel: EventViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    var selectedDate = eventViewModel.selectedDate
    var isDateSet = eventViewModel.isDateSet

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            DatePicker(onDateChange = { newDate ->
                eventViewModel.updateSelectedDate(newDate)
                eventViewModel.setDate(newDate)
                openDialog.value = false
                isDateSet = true
            }, minDate = LocalDateTime.now(), maxDate = LocalDateTime.now().plusYears(1))
        }
    }

    Button(onClick = { openDialog.value = true }) {
        Text(if (isDateSet) selectedDate!!.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)) else "Set Date")
    }
}





@Composable
fun DatePicker(
    onDateChange: (LocalDateTime) -> Unit,
    minDate: LocalDateTime,
    maxDate: LocalDateTime
) {
    // Implement DatePicker UI here
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply {
        set(minDate.year, minDate.monthValue - 1, minDate.dayOfMonth)
    }
    val maxCalendar = Calendar.getInstance().apply {
        set(maxDate.year, maxDate.monthValue - 1, maxDate.dayOfMonth)
    }

    DisposableEffect(Unit) {
        val dialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
                onDateChange(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = calendar.timeInMillis
            datePicker.maxDate = maxCalendar.timeInMillis
            show()
        }
        onDispose { dialog.dismiss() }
    }
}

@Composable
fun TimePickerDialog(eventViewModel: EventViewModel) {
    val openDialog = remember { mutableStateOf(false) }

    var selectedTime = eventViewModel.selectedTime
    var isTimeSet = eventViewModel.isTimeSet

    if (openDialog.value) {
        Dialog(onDismissRequest = { openDialog.value = false }) {
            TimePicker(onTimeChange = { newTime ->
                eventViewModel.updateSelectedTime(newTime)
                eventViewModel.setTime(newTime)
                openDialog.value = false
                isTimeSet = true
            })
        }
    }

    Button(onClick = { openDialog.value = true }) {
        Text(if (isTimeSet) selectedTime!!.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) else "Set Time")
    }
}
@Composable
fun TimePicker(onTimeChange: (LocalTime) -> Unit) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val dialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val selectedTime = LocalTime.of(hourOfDay, minute)
                onTimeChange(selectedTime)
            },
            LocalTime.now().hour,
            LocalTime.now().minute,
            true
        ).apply {
            show()
        }
        onDispose { dialog.dismiss() }
    }
}

// Add other validation functions
fun validateEvent(event: Event): Boolean {
    return event.name.isNotEmpty() &&
           event.organizingClub.isNotEmpty() &&
           event.clubCoordinator.isNotEmpty() &&
           event.eventDetails.isNotEmpty() &&
           event.noOfVolunteers.isNotEmpty() &&
           event.noOfAttendees.isNotEmpty() &&
           event.fee.isNotEmpty() &&
           event.organizer.isNotEmpty() &&
           event.date != null &&
           event.time != null
}