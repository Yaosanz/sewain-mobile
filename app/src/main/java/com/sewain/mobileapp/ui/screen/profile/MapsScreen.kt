package com.sewain.mobileapp.ui.screen.profile

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.sewain.mobileapp.R
import com.sewain.mobileapp.data.remote.model.Maps
import com.sewain.mobileapp.data.remote.model.MapsBody
import com.sewain.mobileapp.di.Injection
import com.sewain.mobileapp.ui.ViewModelFactory
import com.sewain.mobileapp.ui.component.DialogScreen
import com.sewain.mobileapp.ui.theme.SewainAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(
    id: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideUserRepository(LocalContext.current))
    ),
) {
    val openDialog = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val permission = Manifest.permission.ACCESS_FINE_LOCATION
    val permissionGranted =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    val currentLocation = LatLng(-2.4833825997828716, 117.8902853)

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var mapProperties = MapProperties()
    val cameraPositionState = rememberCameraPositionState()
    {
        position = CameraPosition.fromLatLngZoom(currentLocation, 3f)
    }

    var markerPosition = rememberMarkerState()

    if (permissionGranted) {
        viewModel.getDeviceLocation(fusedLocationProviderClient)

        mapProperties = MapProperties(
            isMyLocationEnabled = true,
        )

    } else {
        val requestPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                viewModel.getDeviceLocation(fusedLocationProviderClient)
            } else {
                openDialog.value = false
            }
        }

        when {
            openDialog.value -> {
                DialogScreen(
                    onDismissRequest = { openDialog.value = false },
                    onConfirmation = {
                        requestPermissionLauncher.launch(permission)
                        openDialog.value = false
                    },
                    message = R.string.maps_permission,
                    icon = Icons.Outlined.Info
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.back),
                        modifier = modifier
                            .padding(start = 8.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                }
            )
        }
    ) { innerPadding ->
        GoogleMap(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onMapClick = { location ->
                markerPosition.position = location
                scope.launch {
                    val maps = Maps(
                        MapsBody(location.latitude, location.longitude)
                    )

                    viewModel.updateMap(id, maps)

                    snackbarHostState.showSnackbar(message = "Success adding position, please back to the address screen")
                }
            }
        ) {
            Marker(
                state = markerPosition
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun MapsScreenPreview() {
    SewainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            MapsScreen(
                id = "",
                navController = rememberNavController(),
                snackbarHostState = remember { SnackbarHostState() }
            )
        }
    }
}