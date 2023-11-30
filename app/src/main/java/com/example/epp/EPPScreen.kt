package com.example.epp

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import com.example.epp.screens.AddEventScreen
import com.example.epp.screens.EventsScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.epp.screens.AddPermissionsScreen

enum class EPPScreen (@StringRes val title: Int){
    Events(R.string.events),
    AddEvents(R.string.addEvent),
    AddPermissions(R.string.addPermissions)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EPPAppBar(
    currentEPPScreen: EPPScreen,
    canNavigateBack: Boolean,
    navigateUp:()-> Unit,
    modifier: Modifier=Modifier
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
        }
    )
}
@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EppScreen(
    modifier: Modifier=Modifier
){
    val navController= rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentEPPScreen= EPPScreen.values().find {
        it.name==navBackStackEntry?.destination?.route
    }?:EPPScreen.Events
    val canNavigateUp=navController.previousBackStackEntry!=null
    Scaffold(
        topBar = {
            EPPAppBar(
                currentEPPScreen = currentEPPScreen,
                canNavigateBack = canNavigateUp,
                navigateUp = {navController.navigateUp()},
                modifier = modifier
            )
        }
    ) { innerPadding->
        NavHost(navController = navController,
            startDestination = EPPScreen.Events.name,
            modifier = Modifier.padding(innerPadding)){
            composable(EPPScreen.Events.name){
                EventsScreen(onAddEventClicked = {
                    navController.navigate(EPPScreen.AddEvents.name)
                })
            }
            composable(EPPScreen.AddEvents.name){
                AddEventScreen(
                    onCancelButtonClicked = {navController.navigateUp()}
                ) { navController.navigate(EPPScreen.AddPermissions.name) }
            }
            composable(EPPScreen.AddPermissions.name){
                AddPermissionsScreen(
                    onCancelButtonClicked = {navController.popBackStack(EPPScreen.Events.name, inclusive = false)}
                ) { navController.navigate(EPPScreen.AddPermissions.name) }
            }
        }
    }
}