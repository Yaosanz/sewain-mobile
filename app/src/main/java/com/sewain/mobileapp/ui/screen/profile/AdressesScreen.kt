package com.sewain.mobileapp.ui.screen.profile

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sewain.mobileapp.R
import com.sewain.mobileapp.data.remote.model.Address
import com.sewain.mobileapp.data.remote.model.AddressBody
import com.sewain.mobileapp.di.Injection
import com.sewain.mobileapp.ui.ViewModelFactory
import com.sewain.mobileapp.ui.navigation.Screen
import com.sewain.mobileapp.ui.theme.Gray700
import com.sewain.mobileapp.ui.theme.MidnightBlue
import com.sewain.mobileapp.ui.theme.RoyalBlue
import com.sewain.mobileapp.ui.theme.SewainAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdressesScreen(
    id: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideUserRepository(LocalContext.current))
    ),
) {
    viewModel.getUserById(id)

    // State for input
    var inputFullAdress by remember { mutableStateOf("") }
    var inputNumberPhone by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    var success by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.adresses),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.padding(start = 24.dp)
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    state = scrollState
                )
        ) {

            HorizontalDivider(color = MaterialTheme.colorScheme.primary)

            Image(
                painter = painterResource(R.drawable.adresses),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .padding(top = 24.dp)
                    .size(200.dp)
            )

            Text(
                text = stringResource(R.string.adresses_message),
                fontSize = 13.sp,
                modifier = modifier.padding(top = 38.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.adresses),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(start = 20.dp, top = 48.dp, end = 16.dp)
                    .align(Start),
            )

            OutlinedTextField(
                value = inputFullAdress,
                onValueChange = { newInput ->
                    inputFullAdress = newInput
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp),
                textStyle = TextStyle(
                    color = MidnightBlue,
                    fontSize = 20.sp
                ),
                placeholder = {
                    Text(
                        text = if (viewModel.fullAddress.value == "null") stringResource(R.string.full_address) else viewModel.fullAddress.value,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = Gray700
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = RoyalBlue,
                    selectionColors = TextSelectionColors(
                        handleColor = RoyalBlue,
                        backgroundColor = RoyalBlue
                    ),
                    focusedIndicatorColor = RoyalBlue,
                ),
            )

            Text(
                text = stringResource(R.string.phone_number),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(start = 20.dp, top = 16.dp, end = 16.dp)
                    .align(Start),
            )

            OutlinedTextField(
                value = inputNumberPhone,
                onValueChange = { newInput ->
                    inputNumberPhone = newInput
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 16.dp, end = 16.dp),
                textStyle = TextStyle(
                    color = MidnightBlue,
                    fontSize = 20.sp
                ),
                placeholder = {
                    Text(
                        text = if (viewModel.numberPhone.value == "null") stringResource(R.string.phone_format) else viewModel.numberPhone.value,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = null,
                        tint = Gray700
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = RoyalBlue,
                    selectionColors = TextSelectionColors(
                        handleColor = RoyalBlue,
                        backgroundColor = RoyalBlue
                    ),
                    focusedIndicatorColor = RoyalBlue,
                )
            )

            Button(
                onClick = {
                    navController.navigate(Screen.Maps.createRoute(id))
                },
                modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .align(Start),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )

                Text(
                    text = stringResource(R.string.set_map),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = modifier.padding(start = 4.dp)
                )
            }

            Button(
                onClick = {
                    scope.launch {
                        if (
                            inputFullAdress.isEmpty() &&
                            inputNumberPhone.isEmpty()
                        ) {
                            snackbarHostState.showSnackbar(message = "Error: No changes made")
                        } else {
                            loading = true
                            success = viewModel.success.value

                            if (inputFullAdress.isEmpty()) {
                                inputFullAdress = viewModel.fullAddress.value
                            }
                            if (inputNumberPhone.isEmpty()) {
                                inputNumberPhone = viewModel.numberPhone.value
                            }

                            val address = Address(
                                AddressBody(
                                    viewModel.fullName.value,
                                    inputFullAdress,
                                    inputNumberPhone
                                )
                            )

                            viewModel.updateAddress(id, address)

                            delay(2000)
                            snackbarHostState.showSnackbar(message = viewModel.message.value)

                            enabled = true
                            loading = false
                        }
                    }
                },
                modifier
                    .padding(top = 36.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = enabled,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                    enabled = false
                } else {
                    Text(
                        text = stringResource(R.string.save),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
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
fun AddressesScreenPreview() {
    SewainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AdressesScreen(
                id = "",
                navController = rememberNavController(),
                snackbarHostState = remember { SnackbarHostState() }
            )
        }
    }
}