package com.example.epp.screens

import com.example.epp.repository.SharedPrefManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.epp.EPPScreen
import com.example.epp.repository.UserRepository
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val userRepository = UserRepository(context)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Button(onClick = {
            userRepository.login(emailState.value, passwordState.value,
                onSuccess = {
                    // Navigate to EventScreen on success
                    SharedPrefManager(context).setLoggedIn(true)
                    // Navigate to EventScreen on success
                    navController.navigate(EPPScreen.Events.name){
                        popUpTo(EPPScreen.Login.name){
                            inclusive = true
                        }
                        launchSingleTop= true
                    }
                },
                onFailure = { exception ->
                    // Handle failure
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(exception.message ?: "Unknown error")
                    }
                }
            )
        }) {
            Text("Login")
        }
        Row (
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = "Forgot Password? ")
            ClickableText(
                text = AnnotatedString("Reset", spanStyle = SpanStyle(fontWeight = FontWeight.Bold)),
                onClick = {
                    navController.navigate(EPPScreen.ResetPassword.name){
                        popUpTo(EPPScreen.Login.name){
                            inclusive = true
                        }
                        launchSingleTop= true
                    }
                }
            )
        }
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Don't have an account? ")
            ClickableText(
                text = AnnotatedString("Sign Up", spanStyle = SpanStyle(fontWeight = FontWeight.Bold)),
                onClick = {
                    navController.navigate(EPPScreen.SignUp.name){
                        popUpTo(EPPScreen.Login.name){
                            inclusive = true
                        }
                        launchSingleTop= true
                    }
                }
            )
        }

    }
}

@Composable
fun SignUpScreen(navController: NavController) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val userRepository = UserRepository(context)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Button(onClick = {
            userRepository.register(emailState.value, passwordState.value,
                onSuccess = {
                    // Navigate to EventScreen on success
                    SharedPrefManager(context).setLoggedIn(true)
                    // Navigate to EventScreen on success
                    navController.navigate(EPPScreen.Events.name){
                        popUpTo(EPPScreen.SignUp.name){
                            inclusive = true
                        }
                        launchSingleTop= true
                    }
                },
                onFailure = { exception ->
                    // Handle failure
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(exception.message ?: "Unknown error")
                    }
                }
            )
        }) {
            Text("Sign Up")
        }
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already have an account? ")
            ClickableText(
                text = AnnotatedString("Login", spanStyle = SpanStyle(fontWeight = FontWeight.Bold)),
                onClick = {
                    navController.navigate(EPPScreen.Login.name){
                        popUpTo(EPPScreen.SignUp.name){
                            inclusive = true
                        }
                        launchSingleTop= true
                    }
                }
            )
        }
    }
}
@Composable
fun ResetPasswordScreen(navController: NavController) {
    val emailState = remember { mutableStateOf("") }
    val newPasswordState = remember { mutableStateOf("") }
    val confirmPasswordState = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val userRepository = UserRepository(context)
    val isEmailVerified = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        if (isEmailVerified.value){
            OutlinedTextField(
                value = newPasswordState.value,
                onValueChange = { newPasswordState.value = it },
                label = { Text("New Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            OutlinedTextField(
                value = confirmPasswordState.value,
                onValueChange = { confirmPasswordState.value = it },
                label = { Text("Confirm Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Button(onClick = {
                if (newPasswordState.value == confirmPasswordState.value) {
                    userRepository.updatePassword(newPasswordState.value,
                        onSuccess = {
                            // Handle success
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Password updated successfully")
                            }
                            navController.navigate(EPPScreen.Login.name) {
                                popUpTo(EPPScreen.ResetPassword.name) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },
                        onFailure = { exception ->
                            // Handle failure
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(exception.message ?: "Unknown error")
                            }
                        }
                    )
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Passwords do not match")
                    }
                }
            }) {
                Text("Update Password")
            }
        }else {
            Button(onClick = {
                userRepository.verifyEmail(emailState.value,
                    onSuccess = {
                        // Handle success
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Email sent successfully")
                        }
                        isEmailVerified.value = true
                    },
                    onFailure = { exception ->
                        // Handle failure
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(exception.message ?: "Unknown error")
                        }
                    }
                )
            }){
                Text("Reset Password")
            }
        }

        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Remembered your password? ")
            ClickableText(
                text = AnnotatedString("Login", spanStyle = SpanStyle(fontWeight = FontWeight.Bold)),
                onClick = {
                    navController.navigate(EPPScreen.Login.name){
                        popUpTo(EPPScreen.ResetPassword.name){
                            inclusive = true
                        }
                        launchSingleTop= true
                    }
                }
            )
        }
    }
}