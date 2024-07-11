package com.sewain.mobileapp.ui.screen.profile

import android.content.res.Configuration
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
import androidx.compose.material.icons.outlined.Lock
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sewain.mobileapp.R
import com.sewain.mobileapp.di.Injection
import com.sewain.mobileapp.ui.ViewModelFactory
import com.sewain.mobileapp.ui.theme.Gray700
import com.sewain.mobileapp.ui.theme.MidnightBlue
import com.sewain.mobileapp.ui.theme.RoyalBlue
import com.sewain.mobileapp.ui.theme.SewainAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeScreenPasswordScreen(
    id: String,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideUserRepository(LocalContext.current))
    ),
) {
    // State for input
    var inputCurrentPassword by remember { mutableStateOf("") }
    var inputNewPassword by remember { mutableStateOf("") }
    var inputRepeatNewPassword by remember { mutableStateOf("") }
    val passwordHidden by rememberSaveable { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    var loading by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(true) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.change_password),
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
            horizontalAlignment = CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    state = scrollState
                )
        ) {

            HorizontalDivider(color = MaterialTheme.colorScheme.primary)

            Image(
                painter = painterResource(R.drawable.change_password),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .padding(top = 24.dp)
                    .size(200.dp)
            )

            Text(
                text = stringResource(R.string.password_page_message),
                fontSize = 13.sp,
                modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center,
            )

            OutlinedTextField(
                value = inputCurrentPassword,
                onValueChange = { newInput ->
                    inputCurrentPassword = newInput
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                textStyle = TextStyle(
                    color = MidnightBlue,
                    fontSize = 20.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.current_password),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = Gray700
                    )
                },
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
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

            OutlinedTextField(
                value = inputNewPassword,
                onValueChange = { newInput ->
                    inputNewPassword = newInput
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                textStyle = TextStyle(
                    color = MidnightBlue,
                    fontSize = 20.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.new_password),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = Gray700
                    )
                },
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
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

            OutlinedTextField(
                value = inputRepeatNewPassword,
                onValueChange = { newInput ->
                    inputRepeatNewPassword = newInput
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                textStyle = TextStyle(
                    color = MidnightBlue,
                    fontSize = 20.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.repeat_new_password),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = Gray700
                    )
                },
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
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
                    scope.launch {
                        if (
                            inputCurrentPassword.isEmpty() &&
                            inputNewPassword.isEmpty() &&
                            inputRepeatNewPassword.isEmpty()
                        ) {
                            snackbarHostState.showSnackbar(message = "Error: No changes made")
                        } else {
                            viewModel.changePassword(
                                id,
                                inputCurrentPassword,
                                inputNewPassword,
                                inputRepeatNewPassword
                            )

                            loading = viewModel.loading.value

                            delay(2000)

                            snackbarHostState.showSnackbar(message = viewModel.message.value)
                            loading = false
                        }
                        enabled = true
                    }
                },
                modifier
                    .padding(top = 48.dp, start = 16.dp, end = 16.dp)
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
                        text = stringResource(R.string.change_password),
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
fun ChangeScreenPasswordScreenPreview() {
    SewainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ChangeScreenPasswordScreen(
                id = "",
                navController = rememberNavController(),
                snackbarHostState = remember { SnackbarHostState() },
            )
        }
    }
}