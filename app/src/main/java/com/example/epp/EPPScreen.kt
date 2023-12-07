package com.example.epp

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.example.epp.screens.AddEventScreen
import com.example.epp.screens.EventsScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.epp.repository.UserRepository
import com.example.epp.screens.AddPermissionsScreen
import com.example.epp.screens.LoginScreen
import com.example.epp.screens.ResetPasswordScreen
import com.example.epp.screens.SignUpScreen

enum class EPPScreen (@StringRes val title: Int){
    Events(R.string.events),
    AddEvents(R.string.addEvent),
    AddPermissions(R.string.addPermissions),
    ResetPassword(R.string.resetPassword),
    Login(R.string.login),
    SignUp(R.string.signup)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EPPAppBar(
    currentEPPScreen: EPPScreen,
    canNavigateBack: Boolean,
    navigateUp:()-> Unit,
    modifier: Modifier=Modifier,
    navController: NavController
){
    CenterAlignedTopAppBar(
        title = { Text(stringResource(id = currentEPPScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack){
                IconButton(onClick = navigateUp){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (currentEPPScreen == EPPScreen.Events)
                LogoutButton(
                    userRepository = UserRepository(context = LocalContext.current),
                    navController = navController
                )
        }
    )
}
@SuppressLint("NewApi")
@Composable
fun EppScreen(
    modifier: Modifier=Modifier
){
    val navController= rememberNavController()
    val userRepository = UserRepository(LocalContext.current)
    val startDestination = if (userRepository.isLoggedIn()) EPPScreen.Events.name else EPPScreen.Login.name


    NavHost(navController = navController,
        startDestination = startDestination,) {
        composable(EPPScreen.Login.name) { LoginScreen(navController) }
        composable(EPPScreen.SignUp.name) { SignUpScreen(navController) }
        composable(EPPScreen.ResetPassword.name) { ResetPasswordScreen(navController) }
        composable(EPPScreen.Events.name) {
            Scaffold(
                topBar = {
                    EPPAppBar(
                        currentEPPScreen = EPPScreen.Events,
                        canNavigateBack = false,
                        navigateUp = { navController.navigateUp() },
                        modifier = modifier,
                        navController = navController
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    EventsScreen(onAddEventClicked = {
                        navController.navigate(EPPScreen.AddEvents.name)
                    },)
                }
            }
        }
        composable(EPPScreen.AddEvents.name){
            Scaffold(
                topBar = {
                    EPPAppBar(
                        currentEPPScreen = EPPScreen.AddEvents,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        modifier = modifier,
                        navController = navController
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AddEventScreen(
                        onCancelButtonClicked = {navController.navigateUp()},
                    ) { navController.navigate(EPPScreen.AddPermissions.name) }
                }
            }
        }
        composable(EPPScreen.AddPermissions.name){
            Scaffold(
                topBar = {
                    EPPAppBar(
                        currentEPPScreen = EPPScreen.AddPermissions,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() },
                        modifier = modifier,
                        navController = navController
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AddPermissionsScreen(
                        onCancelButtonClicked = {
                            navController.popBackStack(
                                EPPScreen.Events.name,
                                inclusive = false
                            )
                        }
                    ) { navController.navigate(EPPScreen.AddPermissions.name) }
                }
            }
        }
    }
}
@Composable
fun LogoutButton(userRepository: UserRepository, navController: NavController) {
    Button(onClick = {
        userRepository.logout()
        navController.navigate(EPPScreen.Login.name){
            popUpTo(EPPScreen.Events.name){
                inclusive = true
            }
            launchSingleTop = true
        }
    }) {
        Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "logout")
    }
}