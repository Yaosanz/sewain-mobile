package com.sewain.mobileapp.ui.screen.create_catalog

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.sewain.mobileapp.data.remote.model.Catalog
import com.sewain.mobileapp.di.Injection
import com.sewain.mobileapp.ui.CatalogViewModelFactory
import com.sewain.mobileapp.ui.ViewModelFactory
import com.sewain.mobileapp.ui.navigation.Screen
import com.sewain.mobileapp.ui.screen.login.LoginViewModel
import com.sewain.mobileapp.ui.theme.MidnightBlue
import com.sewain.mobileapp.ui.theme.RoyalBlue
import com.sewain.mobileapp.ui.theme.SewainAppTheme
import com.sewain.mobileapp.utils.uriToFile
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCatalogScreen(
    modifier: Modifier = Modifier,
    shopId: String,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    viewModel: CreateCatalogViewModel = viewModel(
        factory = CatalogViewModelFactory(
            Injection.provideCatalogRepository(LocalContext.current),
            Injection.provideUserRepository(LocalContext.current),
        )
    ),
) {
    var inputCatalogName by remember { mutableStateOf("") }
    var inputDescription by remember { mutableStateOf("") }
    var inputSize by remember { mutableStateOf("") }
    var inputPrice by remember { mutableStateOf("") }
    var inputStatus by remember { mutableStateOf("") }
    var inputDayRental by remember { mutableStateOf("") }
    var inputDayMaintenance by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    var success by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(true) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var hasImage by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcherGallery = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    val scrollState = rememberScrollState()

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.create_catalog),
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

            Text(
                text = stringResource(R.string.create_catalog_message),
                fontSize = 13.sp,
                modifier = modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.photo_costume),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.padding(top = 4.dp)
            )

            IconButton(
                onClick = {
                    launcherGallery.launch("image/*")
                },
                colors = IconButtonDefaults.filledIconButtonColors(MaterialTheme.colorScheme.primary),
                modifier = modifier.clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add),
                )
            }

            Text(
                text = stringResource(R.string.catalog_name),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(start = 20.dp, top = 16.dp, end = 16.dp)
                    .align(Alignment.Start),
            )

            OutlinedTextField(
                value = inputCatalogName,
                onValueChange = { newInput ->
                    inputCatalogName = newInput
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
                        text = stringResource(R.string.catalog_name_placeholder),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
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
                text = stringResource(R.string.description),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(start = 20.dp, top = 8.dp, end = 16.dp)
                    .align(Alignment.Start),
            )

            OutlinedTextField(
                value = inputDescription,
                onValueChange = { newInput ->
                    inputDescription = newInput
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
                        text = stringResource(R.string.description_placeholder),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
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
                text = stringResource(R.string.size),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(start = 20.dp, top = 8.dp, end = 16.dp)
                    .align(Alignment.Start),
            )

            OutlinedTextField(
                value = inputSize,
                onValueChange = { newInput ->
                    inputSize = newInput
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
                        text = stringResource(R.string.size_placeholder),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
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
                text = stringResource(R.string.price),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(start = 20.dp, top = 8.dp, end = 16.dp)
                    .align(Alignment.Start),
            )

            OutlinedTextField(
                value = inputPrice,
                onValueChange = { newInput ->
                    inputPrice = newInput
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
                        text = stringResource(R.string.price_placeholder),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
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
                text = stringResource(R.string.status),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(start = 20.dp, top = 8.dp, end = 16.dp)
                    .align(Alignment.Start),
            )

            OutlinedTextField(
                value = inputStatus,
                onValueChange = { newInput ->
                    inputStatus = newInput
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
                        text = stringResource(R.string.status_placeholder),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
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
                text = stringResource(R.string.day_rental),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(start = 20.dp, top = 8.dp, end = 16.dp)
                    .align(Alignment.Start),
            )

            OutlinedTextField(
                value = inputDayRental,
                onValueChange = { newInput ->
                    inputDayRental = newInput
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
                        text = stringResource(R.string.day_placeholder),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
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
                text = stringResource(R.string.day_maintenance),
                fontSize = 16.sp,
                modifier = modifier
                    .padding(start = 20.dp, top = 8.dp, end = 16.dp)
                    .align(Alignment.Start),
            )

            OutlinedTextField(
                value = inputDayMaintenance,
                onValueChange = { newInput ->
                    inputDayMaintenance = newInput
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
                        text = stringResource(R.string.day_placeholder),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.secondary
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
                        if (inputCatalogName.isEmpty() ||
                            inputDescription.isEmpty() ||
                            inputSize.isEmpty() ||
                            inputStatus.isEmpty() ||
                            inputDayRental.isEmpty() ||
                            inputDayMaintenance.isEmpty() ||
                            !hasImage
                        ) {
                            snackbarHostState.showSnackbar(message = "Incomplete Catalog field")
                        } else {
                            loading = true

                            imageUri?.let { uri ->
                                val imageFile = uriToFile(uri, context)
                                viewModel.uploadImage(imageFile)
                            }
                            hasImage = false

                            val catalog = Catalog(
                                name = inputCatalogName,
                                description = inputDescription,
                                size = inputSize,
                                price = inputPrice.toInt(),
                                status = inputStatus,
                                dayRent = inputDayRental.toInt(),
                                dayMaintenance = inputDayMaintenance.toInt(),
                                photoUrl = viewModel.imageString.value,
                                shopId = shopId
                            )

                            viewModel.addCatalog(catalog)

                            success = viewModel.success.value

                            delay(2000)
                            snackbarHostState.showSnackbar(message = viewModel.message.value)
                            loading = false
                            enabled = true

                            if (success) {
                                navController.navigate(Screen.Home.route)
                            }
                        }
                    }
                },
                modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = enabled,
                shape = RoundedCornerShape(10.dp),
                colors =
                ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                    enabled = false
                } else {
                    Text(
                        text = stringResource(R.string.add_catalog),
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
fun CreateCatalogScreenPreview() {
    SewainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CreateCatalogScreen(
                shopId = "",
                navController = rememberNavController(),
                snackbarHostState = remember { SnackbarHostState() }
            )
        }
    }
}