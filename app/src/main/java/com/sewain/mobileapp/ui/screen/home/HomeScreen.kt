package com.sewain.mobileapp.ui.screen.home

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.sewain.mobileapp.R
import com.sewain.mobileapp.data.local.entity.CatalogEntity
import com.sewain.mobileapp.data.local.model.SessionModel
import com.sewain.mobileapp.di.Injection
import com.sewain.mobileapp.ui.CatalogViewModelFactory
import com.sewain.mobileapp.ui.component.GridCatalogItem
import com.sewain.mobileapp.ui.component.SearchBar
import com.sewain.mobileapp.ui.navigation.Screen
import com.sewain.mobileapp.ui.theme.SewainAppTheme
import com.sewain.mobileapp.utils.uriToFile

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = viewModel(
        factory = CatalogViewModelFactory(
            Injection.provideCatalogRepository(LocalContext.current),
            Injection.provideUserRepository(
                LocalContext.current
            )
        )
    ),
    sessionModel: SessionModel,
) {
    viewModel.getUserById(sessionModel.id)

    val items = viewModel.catalogs.collectAsLazyPagingItems()
    val query = viewModel.searchQuery.collectAsState()
    val context = LocalContext.current

    // Register the activity result launcher
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            // Handle the picked image URI
            uri?.let {
                val imageFile = uriToFile(uri, context)
                viewModel.processImagePrediction(imageFile)
            }
        }

    LaunchedEffect(Unit) {
        viewModel.initImagePickerLauncher(imagePickerLauncher)
    }
    SewainAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeaderHome(navController)
                SearchBar(
                    query = query.value,
                    onQueryChange = viewModel::setSearchQuery,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (!viewModel.loading.value) {
                            IconButton(
                                onClick = { viewModel.pickImageFromGallery() },
                                modifier = Modifier
                                    .size(48.dp)
                            ) {
                                Icon(Icons.Filled.CameraAlt, contentDescription = "Pick Image")
                            }
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(48.dp)
                            )
                        }
                    }
                )
//                BannerHome()
                Text(
                    text = stringResource(R.string.new_arrivals),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(4.dp),
                )

                if (sessionModel.isShop) {
                    viewModel.setShopId(viewModel.data.value)
                    Button(
                        onClick = {
                            navController.navigate(Screen.CreateCatalog.createRoute(viewModel.data.value))
                        },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier
                            .align(End)
                            .padding(bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_catalog),
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        Text(
                            text = stringResource(R.string.add_catalog),
                        )
                    }
                } else {
                    viewModel.setShopId(null)
                }

                CatalogsHome(items, navController)
            }
        }
    }
}

@Composable
fun CatalogsHome(catalogItems: LazyPagingItems<CatalogEntity>, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(catalogItems.itemCount) { index ->
                catalogItems[index]?.let { catalogItem ->
                    GridCatalogItem(catalogItem, onClick = {
                        navController.navigate("detail_catalog/${catalogItem.id}")
                    })
                }
            }
        }

        when (catalogItems.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is LoadState.Error, is LoadState.NotLoading -> {
                if (catalogItems.itemCount == 0) {
                    Button(
                        onClick = { catalogItems.retry() },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text("Reload")
                    }
                }
            }

            else -> { /* Do nothing for other states */
            }
        }
    }
}

@Composable
fun HeaderHome(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.welcome),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.to_sewain_app),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
        }
        AsyncImage(
            model = "https://storage.googleapis.com/sewain/etc/profile.png",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
                .clickable {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

        )
    }
}

@Composable
fun BannerHome() {

}

@Preview
@Composable
fun PreviewHeaderHome() {
    HeaderHome(rememberNavController())
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
fun PreviewBottomNavigationBar() {
    HomeScreen(
        navController = rememberNavController(),
        sessionModel = SessionModel("", "", false)
    )
}

class FakeCatalogPagingSource : PagingSource<Int, CatalogEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatalogEntity> {
        val items = List(20) { CatalogEntity(id = it.toString(), "Catalog Item $it") }
        return LoadResult.Page(items, prevKey = null, nextKey = null)
    }

    override fun getRefreshKey(state: PagingState<Int, CatalogEntity>): Int? {
        return null
    }
}

// Preview Composable
@Preview(showBackground = true)
@Composable
fun CatalogsHomePreview() {
    val fakePagingSource = FakeCatalogPagingSource()
    val pager = Pager(PagingConfig(pageSize = 10)) { fakePagingSource }
    val catalogItems = pager.flow.collectAsLazyPagingItems()

    CatalogsHome(catalogItems = catalogItems, navController = rememberNavController())
}
