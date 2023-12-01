package com.example.epp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.epp.models.Event

@Composable
fun EventDetailScreen(event: Event) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        val boldStyle = SpanStyle(fontWeight = FontWeight.Bold)
        val italicStyle = SpanStyle(fontStyle = FontStyle.Italic)
        Text(buildAnnotatedString {
            withStyle(style = boldStyle) {
                append("Name: ")
            }
            withStyle(style = italicStyle) {
                append(event.name)
            }
        })
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ){
            Text(buildAnnotatedString {
                withStyle(style = boldStyle) {
                    append("Date: ")
                }
                withStyle(style = italicStyle) {
                    append(event.date.toString())
                }
            })
            Text(buildAnnotatedString {
                withStyle(style = boldStyle) {
                    append("Time: ")
                }
                withStyle(style = italicStyle) {
                    append(event.time.toString())
                }
            })

        }
        Text(buildAnnotatedString {
            withStyle(style = boldStyle) {
                append("Fee: ")
            }
            withStyle(style = italicStyle) {
                append(event.fee)
            }
        })
        Text(buildAnnotatedString {
            withStyle(style = boldStyle) {
                append("Details: ")
            }
            withStyle(style = italicStyle) {
                append(event.eventDetails)
            }
        })
        Text(buildAnnotatedString {
            withStyle(style = boldStyle) {
                append("Organizer: ")
            }
            withStyle(style = italicStyle) {
                append(event.organizer)
            }
        })
        Text(buildAnnotatedString {
            withStyle(style = boldStyle) {
                append("Organizing Club: ")
            }
            withStyle(style = italicStyle) {
                append(event.organizingClub)
            }
        })
        Text(buildAnnotatedString {
            withStyle(style = boldStyle) {
                append("Club Coordinator: ")
            }
            withStyle(style = italicStyle) {
                append(event.clubCoordinator)
            }
        })
        Text(buildAnnotatedString {
            withStyle(style = boldStyle) {
                append("No of Volunteers: ")
            }
            withStyle(style = italicStyle) {
                append(event.noOfVolunteers)
            }
        })
        Text(buildAnnotatedString {
            withStyle(style = boldStyle) {
                append("No of Attendees: ")
            }
            withStyle(style = italicStyle) {
                append(event.noOfAttendees)
            }
        })
    }
}
@Preview
@Composable
fun EventDetailScreenPreview() {
    EventDetailScreen(event = Event("Coderspree", "Innogeeks", "Aryan Mishra", "A coding event held in october in collaboration with MLH", "25", "100", "0", "Innogeeks", null, null, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.freepik.com%2Ffree-vector%2Fflat-people"))
}