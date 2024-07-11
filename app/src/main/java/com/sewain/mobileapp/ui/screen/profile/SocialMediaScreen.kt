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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
fun SocialMediaScreen(
    id: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideUserRepository(LocalContext.current))
    ),
) {
    viewModel.getUserById(id)

    var inputFacebook by remember { mutableStateOf("") }
    var inputInstagram by remember { mutableStateOf("") }
    var inputTikTok by remember { mutableStateOf("") }

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
                        text = stringResource(R.string.social_media),
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
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    state = scrollState
                )
        ) {

            HorizontalDivider(color = MaterialTheme.colorScheme.primary)

            Image(
                painter = painterResource(R.drawable.social_media),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .padding(top = 24.dp)
                    .size(200.dp)
                    .align(CenterHorizontally),
            )

            Text(
                text = stringResource(R.string.social_media_message),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )

            OutlinedTextField(
                value = inputFacebook,
                onValueChange = { newInput ->
                    inputFacebook = newInput
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp, start = 16.dp, end = 16.dp),
                textStyle = TextStyle(
                    color = MidnightBlue,
                    fontSize = 20.sp
                ),
                placeholder = {
                    Text(
                        text = if(viewModel.facebook.value == "null") stringResource(R.string.facebook_account) else viewModel.facebook.value,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.facebook),
                        contentDescription = null,
                        tint = Gray700,
                        modifier = modifier.size(30.dp)
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

            OutlinedTextField(
                value = inputInstagram,
                onValueChange = { newInput ->
                    inputInstagram = newInput
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                textStyle = TextStyle(
                    color = MidnightBlue,
                    fontSize = 20.sp
                ),
                placeholder = {
                    Text(
                        text = if(viewModel.instagram.value == "null") stringResource(R.string.instagram_account) else viewModel.instagram.value,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.instagram),
                        contentDescription = null,
                        tint = Gray700,
                        modifier = modifier.size(30.dp)
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

            OutlinedTextField(
                value = inputTikTok,
                onValueChange = { newInput ->
                    inputTikTok = newInput
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                textStyle = TextStyle(
                    color = MidnightBlue,
                    fontSize = 20.sp
                ),
                placeholder = {
                    Text(
                        text = if(viewModel.tiktok.value == "null") stringResource(R.string.tiktok_account) else viewModel.tiktok.value,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.tiktok),
                        contentDescription = null,
                        tint = Gray700,
                        modifier = modifier.size(30.dp)
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

            Button(
                onClick = {
                    scope.launch {
                        if (
                            inputInstagram.isEmpty() &&
                            inputFacebook.isEmpty() &&
                            inputTikTok.isEmpty()
                        ) {
                            snackbarHostState.showSnackbar(message = "Error: No changes made")
                        } else {
                            viewModel.updateSocialMedia(
                                viewModel.socialMediaId.value,
                                inputFacebook,
                                inputInstagram,
                                inputTikTok
                            )

                            loading = viewModel.loading.value

                            delay(2000)
                            snackbarHostState.showSnackbar(message = viewModel.message.value)

                            loading = false
                            enabled = true
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
fun SocialMediaScreenPreview() {
    SewainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            SocialMediaScreen(
                id = "",
                navController = rememberNavController(),
                snackbarHostState = remember { SnackbarHostState() }
            )
        }
    }
}