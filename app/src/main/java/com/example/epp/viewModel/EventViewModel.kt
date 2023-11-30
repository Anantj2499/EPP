package com.example.epp.viewModel

import androidx.lifecycle.ViewModel
import com.example.epp.models.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.LocalTime

class EventViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(Event())
    val uiState: StateFlow<Event> = _uiState.asStateFlow()
    var selectedDate: LocalDateTime? = null
    var selectedTime: LocalTime? = null
    var isDateSet: Boolean = false
    var isTimeSet: Boolean = false
    fun updateSelectedDate(date: LocalDateTime) {
        selectedDate = date
        setDate(date)
        isDateSet = true
    }

    fun updateSelectedTime(time: LocalTime) {
        selectedTime = time
        setTime(time)
        isTimeSet = true
    }
    fun setEventName(eventName: String){
        _uiState.value = _uiState.value.copy(name = eventName)
    }
    fun setOrganizingClub(organizingClub: String){
        _uiState.value = _uiState.value.copy(organizingClub = organizingClub)
    }
    fun setClubCoordinator(clubCoordinator: String){
        _uiState.value = _uiState.value.copy(clubCoordinator = clubCoordinator)
    }
    fun setEventDetails(eventDetails: String){
        _uiState.value = _uiState.value.copy(eventDetails = eventDetails)
    }
    fun setNoOfVolunteers(noOfVolunteers: String){
        _uiState.value = _uiState.value.copy(noOfVolunteers = noOfVolunteers)
    }
    fun setNoOfAttendees(noOfAttendees: String){
        _uiState.value = _uiState.value.copy(noOfAttendees = noOfAttendees)
    }
    fun setFee(fee: String){
        _uiState.value = _uiState.value.copy(fee = fee)
    }
    fun setOrganizer(organizer: String){
        _uiState.value = _uiState.value.copy(organizer = organizer)
    }
    fun setDate(date: LocalDateTime){
        _uiState.value = _uiState.value.copy(date = date)
    }
    fun setTime(time: LocalTime){
        _uiState.value = _uiState.value.copy(time = time)
    }
    fun setImageUrl(imageUrl: String){
        _uiState.value = _uiState.value.copy(imageUrl = imageUrl)
    }
    fun setEvent(event: Event){
        _uiState.value = event
    }
    fun resetEvent(){
        _uiState.value = Event()
        selectedDate= null
        selectedTime= null
        isDateSet= false
        isTimeSet= false
    }
}