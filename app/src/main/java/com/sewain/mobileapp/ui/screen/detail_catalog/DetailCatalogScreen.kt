package com.sewain.mobileapp.ui.screen.detail_catalog

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sewain.mobileapp.data.remote.response.CatalogItem
import com.sewain.mobileapp.di.Injection
import com.sewain.mobileapp.ui.CatalogViewModelFactory
import com.sewain.mobileapp.ui.component.CustomButton
import com.sewain.mobileapp.ui.theme.SewainAppTheme
import com.sewain.mobileapp.utils.rp

@Composable
fun DetailCatalogScreen(id : String, navController: NavController , viewModel: DetailCatalogViewModel = viewModel(
    factory = CatalogViewModelFactory(Injection.provideCatalogRepository(LocalContext.current),
        Injection.provideUserRepository(LocalContext.current))
)
) {
    // React to state changes
    val catalog = viewModel.catalog.value

    // Call fetchCatalog when the composable enters the composition
    LaunchedEffect(id) {
        viewModel.fetchCatalog(id)
    }

    val goToCheckout = {
        navController.navigate("checkout/${id}")
    }

    SewainAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (catalog != null) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    DetailCatalog(catalog, navController, goToCheckout)
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCatalog(catalog : CatalogItem,navController: NavController, onClick : () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
        AsyncImage(
            model = catalog.photoUrl,
            contentDescription = "Product Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        )
                Column(modifier = Modifier.clip(RoundedCornerShape(16.dp))){
                    catalog.name?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    // Add rating bar here

                    Text(
                        text = "Size ${catalog.size}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                    // Add more sizes as needed

                    catalog.description?.let {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    // Add more detail texts
                }
    }}
    // IconButton at the top
    IconButton(
        onClick = { navController.navigateUp() },
        modifier = Modifier
            .align(Alignment.TopStart)
    ) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
    }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier
                .align(Alignment.CenterVertically)){
                Text(
                    text = "Total Price",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${catalog.price.rp()} / ${catalog.dayRent} days",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            CustomButton(text = "Book Now", onClick = onClick)
        }
}
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewDetailCatalog(){
    SewainAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        DetailCatalog(CatalogItem(id = "1", price = 10000.0, name = "gojo", dayRent = 3, photoUrl = "https://down-id.img.susercontent.com/file/bc2dc2f92c402aa078b5409470625b46", description = "anjay gojo", size = "M"), rememberNavController())
    }
}
}

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewDarkDetailCatalog(){
    SewainAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DetailCatalog(
                CatalogItem(
                    id = "1",
                    price = 10000.0,
                    name = "gojo",
                    dayRent = 3,
                    photoUrl = "https://down-id.img.susercontent.com/file/bc2dc2f92c402aa078b5409470625b46",
                    description = "anjay gojo",
                    size = "M"
                ),
                rememberNavController()
            )
        }
    }
}