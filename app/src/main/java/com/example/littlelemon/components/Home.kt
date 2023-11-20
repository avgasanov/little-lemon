package com.example.littlelemon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.R
import com.example.littlelemon.persistence.MenuItemRecord
import com.example.littlelemon.persistence.MenuRepository
import com.example.littlelemon.ui.theme.SemiWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {
    val databaseMenuItems by
    MenuRepository(LocalContext.current).getMenuItems().observeAsState(initial = emptyList())

    val (searchQuery, setSearchQuery) = remember { mutableStateOf("") }
    val (categoryFilter, setCategoryFilter) = remember {
        mutableStateOf<String?>(null)
    }
    Column(Modifier.fillMaxSize(1F)) {
        Box(Modifier.fillMaxWidth()) {
            Image(
                painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo_desc),
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(200.dp)
                    .height(60.dp)
            )
            Image(
                painterResource(id = R.drawable.profile),
                contentDescription = stringResource(id = R.string.logo_desc),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .width(40.dp)
                    .height(40.dp)
                    .clickable {
                        navController.navigate(com.example.littlelemon.Profile.route)
                    }
            )

        }
        Box(Modifier.background(MaterialTheme.colorScheme.primary)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Row(Modifier.fillMaxWidth(1F)) {
                    Column(Modifier.fillMaxWidth(0.6F), verticalArrangement = Arrangement.Top) {
                        Text(
                            text = stringResource(id = R.string.chicago),
                            style = MaterialTheme.typography.titleLarge,
                            color = SemiWhite
                        )
                        Text(
                            text = stringResource(id = R.string.hero_desc),
                            modifier = Modifier.padding(end = 16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = SemiWhite
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.hero),
                        contentDescription = stringResource(
                            id = R.string.hero_image_alt
                        ),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(160.dp)
                            .align(CenterVertically)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
                TextField(
                    value = searchQuery,
                    onValueChange = setSearchQuery,
                    placeholder = {
                        Text(
                            stringResource(id = R.string.enter_search_query)
                        )
                    },
                    leadingIcon = {
                        Image(
                            painterResource(id = android.R.drawable.ic_menu_search),
                            contentDescription = stringResource(
                                id = R.string.search_icon
                            ),
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(1F)
                        .padding(8.dp, 20.dp),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
        CategoriesList(setCategoryFilter)
        MenuItemsList(items = databaseMenuItems.let { menuItems ->
            if (searchQuery.isNotBlank()) {
                menuItems.filter {
                    it.title.lowercase().contains(searchQuery.lowercase()) ||
                            it.description.lowercase().contains(searchQuery.lowercase())
                }
            } else menuItems
        }.let {
            if (categoryFilter != null) {
                it.filter { menuItem ->
                    menuItem.category.lowercase() == categoryFilter.lowercase()
                }
            } else it
        }.sortedBy { it.title })
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(name: String, desc: String, price: Double, image: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column() {
            Text(
                name,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                desc,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth(0.75F)
                    .padding(top = 5.dp, bottom = 5.dp)
            )
            Text(
                "$${price}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
        GlideImage(
            model = image,
            contentDescription = "",
            Modifier
                .width(60.dp)
                .height(60.dp)
        )
    }
}

@Composable
private fun CategoriesList(setCategoryFilter: (String?) -> Unit) {
    val categories = remember {
        mutableStateOf(
            listOf(
                "Starters",
                "Mains",
                "Desserts",
                "Drinks"

            )
        )
    }
    Column() {
        Text(
            text = stringResource(id = R.string.order_for_delivery),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(count = 4, itemContent = {
                val category = categories.value[it]
                Button(
                    {
                        setCategoryFilter(category)
                    }, shape = RoundedCornerShape(6.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = SemiWhite,
                    ), modifier = Modifier
                        .padding(8.dp)
                        .wrapContentWidth()
                ) {
                    Text(
                        text = category,
                        Modifier
                            .padding(vertical = 4.dp, horizontal = 0.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            })
        }
    }
}

@Composable
private fun MenuItemsList(items: List<MenuItemRecord>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 4.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            count = items.size,
            itemContent = {
                with(items[it]) {
                    MenuItem(name = title, desc = description, price = price, image = image)
                }
            }
        )
    }
}