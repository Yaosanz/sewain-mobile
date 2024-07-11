package com.sewain.mobileapp.ui.screen.profile

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sewain.mobileapp.R
import com.sewain.mobileapp.data.local.model.SessionModel
import com.sewain.mobileapp.di.Injection
import com.sewain.mobileapp.ui.ViewModelFactory
import com.sewain.mobileapp.ui.component.DialogScreen
import com.sewain.mobileapp.ui.navigation.Screen
import com.sewain.mobileapp.ui.theme.LightBlueGray
import com.sewain.mobileapp.ui.theme.SalmonPink
import com.sewain.mobileapp.ui.theme.SewainAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    sessionModel: SessionModel,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideUserRepository(LocalContext.current))
    ),
) {
    val openDialog = remember { mutableStateOf(false) }

    viewModel.getUserById(sessionModel.id)

    val username =
        if (sessionModel.isShop && viewModel.usernameShop.value == "null") {
            stringResource(R.string.username_shop)
        } else if (sessionModel.isShop) {
            viewModel.usernameShop.value
        } else {
            viewModel.username.value
        }


    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
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

            // Condition photo profile
            if (viewModel.imageString.value != "null") {
                AsyncImage(
                    model = viewModel.imageString.value,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = modifier
                        .padding(top = 24.dp)
                        .size(150.dp)
                        .clip(CircleShape),
                )
            } else {
                Image(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = modifier
                        .padding(top = 24.dp)
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
            }

            Text(
                text = if (viewModel.fullName.value == "null" && !sessionModel.isShop) {
                    stringResource(R.string.full_name)
                } else if (viewModel.shopName.value == "null" && sessionModel.isShop) {
                    stringResource(R.string.shop_name)
                } else {
                    if (sessionModel.isShop) viewModel.shopName.value else viewModel.fullName.value
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(top = 24.dp)
            )

            Text(
                text = stringResource(R.string.username_profile_format, username),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = modifier.padding(top = 4.dp)
            )

            Text(
                text = viewModel.email.value,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = modifier.padding(top = 4.dp)
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    .height(50.dp)
                    .border(1.dp, LightBlueGray, RoundedCornerShape(8.dp))
                    .clickable {
                        if (sessionModel.isShop) {
                            viewModel.setSession(sessionModel.id, sessionModel.token, false)
                        } else {
                            viewModel.setSession(sessionModel.id, sessionModel.token, true)
                        }

                        if (!sessionModel.isShop && viewModel.shopName.value == "null") {
                            navController.navigate(
                                Screen.ShopAccount.createRoute(
                                    sessionModel.id
                                )
                            )
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (sessionModel.isShop) {
                        stringResource(R.string.switch_account_user)
                    } else {
                        stringResource(R.string.switch_account_shop)
                    },
                    modifier = modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.secondary
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = modifier.padding(end = 16.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                    .height(50.dp)
                    .border(1.dp, LightBlueGray, RoundedCornerShape(8.dp))
                    .clickable {
                        if (sessionModel.isShop) {
                            navController.navigate(
                                Screen.ShopAccount.createRoute(
                                    sessionModel.id
                                )
                            )
                        } else {
                            navController.navigate(
                                Screen.DetailProfile.createRoute(
                                    sessionModel.id
                                )
                            )
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if(sessionModel.isShop) stringResource(R.string.edit_shop) else stringResource(R.string.edit_profile),
                    modifier = modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.secondary
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = modifier.padding(end = 16.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                    .height(50.dp)
                    .border(1.dp, LightBlueGray, RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate(
                            Screen.ChangePassword.createRoute(
                                sessionModel.id
                            )
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.change_password),
                    modifier = modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.secondary
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = modifier.padding(end = 16.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                    .height(50.dp)
                    .border(1.dp, LightBlueGray, RoundedCornerShape(8.dp))
                    .clickable { navController.navigate(Screen.Adresses.createRoute(sessionModel.id)) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.add_addresses),
                    modifier = modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.secondary
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = modifier.padding(end = 16.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                    .height(50.dp)
                    .border(1.dp, LightBlueGray, RoundedCornerShape(8.dp))
                    .clickable { navController.navigate(Screen.SocialMedia.createRoute(sessionModel.id)) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.add_social_media),
                    modifier = modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.secondary
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = modifier.padding(end = 16.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                    .height(50.dp)
                    .border(1.dp, SalmonPink, RoundedCornerShape(8.dp))
                    .clickable { openDialog.value = true },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.logout),
                    modifier = modifier.padding(start = 16.dp),
                    color = SalmonPink
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = modifier.padding(end = 16.dp),
                    tint = SalmonPink
                )
            }

        }
    }

    when {
        openDialog.value -> {
            DialogScreen(
                onDismissRequest = { openDialog.value = false },
                onConfirmation = {
                    viewModel.logout()
                    openDialog.value = false
                },
                message = R.string.logout_message,
                icon = Icons.Outlined.Info
            )
        }
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewProfileScreen() {
    SewainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ProfileScreen(
                navController = rememberNavController(),
                sessionModel = SessionModel("", "", false)
            )
        }
    }
}