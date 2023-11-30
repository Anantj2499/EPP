package com.example.epp.models

import java.time.LocalDateTime
import java.time.LocalTime

data class Event(
    val name: String="",
    val organizingClub: String="",
    val clubCoordinator: String="",
    val eventDetails: String="",
    val noOfVolunteers: String="",
    val noOfAttendees: String="",
    val fee: String="",
    val organizer: String="",
    val date: LocalDateTime? = null,
    val time: LocalTime?= null,
    val imageUrl: String="",
    )
