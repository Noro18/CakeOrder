package com.example.ordermanagementcake.ui.clients

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.windowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ordermanagementcake.R
import com.example.ordermanagementcake.ui.components.BottomNavigationBar
import com.example.ordermanagementcake.ui.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsListScreen(navController: NavHostController){
    Scaffold( // uza scafforl hodi fahe nia parte sira ex: topbar,containt, bottombar
        topBar = {
            TopAppBar(
                windowInsets = windowInsets,
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFC23C12))
                    ) {
                        IconButton(
                            onClick = { /* asaun icon bele klik */ }
                        ) {
                            Icon( // icon menu
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                modifier = Modifier
                                    .size(30.dp),
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = stringResource(id = R.string.title_profile),
                            fontSize = 33.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(150.dp))
                        Image( // foto profile
                            painter = painterResource(id = R.drawable.foto_profile),
                            contentDescription = "Foto Profile",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)

                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFC23C12)
                ),
//                windowInsets = WindowInsets(left = 0.dp)
            )
        },

        // ne'e mak bottom bar
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState() // holds info about my current screen
            val currentRoute = navBackStackEntry?.destination?.route // extrack sai string hsui route nia naran

            val selectedItem = when (currentRoute) { // uza naran extracted no troka sai index
                "orders" -> 1
                "clients" -> 2
                else -> 2
            }
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    when (index) {
                        1 -> navController.navigate(Routes.ORDERS)
                        2 -> navController.navigate(Routes.CLIENTS)
                    }
                }
            )
        },

        // ida ne'e mak button add
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = Color(0xFFC23C12)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "aumenta",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
           // search bar
            var searchText by remember { mutableStateOf("") }

            OutlinedTextField(
                value = searchText,
                onValueChange = {searchText = it},
                placeholder = {Text(
                    text =  stringResource(id = R.string.search_clients),
                    modifier = Modifier
                        .padding(0.dp),
                    textAlign = TextAlign.Center
                )},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(400.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(id = R.string.directory_clients),
                color = Color(0xFFB2A9A7),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.loyal_patrons),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.width(70.dp))

                Text(
                    text = stringResource(id = R.string.total_patrons),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 7.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.foto_profile),
                                contentDescription = "Foto Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)

                            )

                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.client1_name),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(bottom = 0.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.nu_telefone1),
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                )
                            }
                            IconButton(
                                onClick = { /* asaun icon bele klik */ }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "hmmm",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.foto_profile),
                                contentDescription = "Foto Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)

                            )

                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.client2_name),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(bottom = 0.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.nu_telefone2),
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                )
                            }
                            IconButton(
                                onClick = { /* asaun icon bele klik */ }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "hmmm",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun ClientsListScreenPreview(){
    _root_ide_package_.com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme {
        ClientsListScreen(navController = rememberNavController())
    }
}*/
