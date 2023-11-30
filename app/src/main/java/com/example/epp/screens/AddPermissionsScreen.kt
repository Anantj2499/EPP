package com.example.epp.screens

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.epp.R

@Composable
fun AddPermissionsScreen(
    onCancelButtonClicked: ()-> Unit,
    onNexButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    val permissionTitles = remember { mutableStateListOf("") }
    val permissionFiles = remember { mutableStateListOf<Uri>() }
    val pickPdf = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { permissionFiles.add(it) }
    }
    val permissionRequest = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // Permission is granted
            pickPdf.launch("application/pdf")

        } else {
            // Permission has not been granted
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        LazyColumn {
            itemsIndexed(permissionTitles) { index, title ->
                TextField(
                    value = title,
                    onValueChange = { permissionTitles[index] = it },
                    label = { Text("Permission Title") },
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
                Button(onClick = {
                    permissionRequest.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }) {
                    Text("Upload Permission File")
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ){
                    Button(onClick = {
                        permissionTitles.add("")
                    }) {
                        Text("+")
                    }
                    Button(onClick = {
                        permissionTitles.removeLast()
                    }) {
                        Text("-")
                    }
                }


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
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .alpha(1f),
                onClick = onCancelButtonClicked) {
                Text(stringResource(R.string.cancel))
            }
            Button(
                modifier = Modifier.weight(1f),
                // the button is enabled when the user makes a selection
                //enabled = selectedValue.isNotEmpty(),
                onClick = onNexButtonClicked
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}