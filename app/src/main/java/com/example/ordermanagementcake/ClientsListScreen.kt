package com.example.ordermanagementcake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Label
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme
import com.google.android.engage.shopping.datamodel.ShoppingCart


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsListScreen(){
    Scaffold( // uza scafforl hodi fahe nia parte sira ex: topbar,containt, bottombar
        topBar = {
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
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
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NavigationBar {
                        NavigationBarItem(
                            selected = false,
                            onClick = { },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Dashboard,
                                    contentDescription = "Dashboard"
                                )
                            },
                            label = { Text("DASHBOARD") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFC23C12) ,        // ← icon nia kor wainhira ita hili
                                selectedTextColor = Color(0xFFC23C12) ,  // ← teks nia kor wainhira ita hili
                                unselectedIconColor = Color.Gray,       // ← icon nia kor wainhira ita la hili
                                unselectedTextColor = Color.Gray,       // ← text nia kor wainhira ita la hili
                                indicatorColor = Color(0xFFF39D82)      // ← kor kabuar ne'ebe sei mosu iha icon nia kotuk
                            )
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.AddShoppingCart,
                                    contentDescription = "Orders"
                                )
                            },
                            label = { Text("ORDERS") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFC23C12) ,        // ← icon nia kor wainhira ita hili
                                selectedTextColor = Color(0xFFC23C12) ,  // ← teks nia kor wainhira ita hili
                                unselectedIconColor = Color.Gray,       // ← icon nia kor wainhira ita la hili
                                unselectedTextColor = Color.Gray,       // ← text nia kor wainhira ita la hili
                                indicatorColor = Color(0xFFF39D82)      // ← kor kabuar ne'ebe sei mosu iha icon nia kotuk
                            )
                        )

                        NavigationBarItem(
                            selected = false,
                            onClick = { },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.People,
                                    contentDescription = "Clients"
                                )
                            },
                            label = { Text("CLIENTS") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFC23C12) ,        // ← icon nia kor wainhira ita hili
                                selectedTextColor = Color(0xFFC23C12) ,  // ← teks nia kor wainhira ita hili
                                unselectedIconColor = Color.Gray,       // ← icon nia kor wainhira ita la hili
                                unselectedTextColor = Color.Gray,       // ← text nia kor wainhira ita la hili
                                indicatorColor = Color(0xFFF39D82)      // ← kor kabuar ne'ebe sei mosu iha icon nia kotuk
                            )
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "Schedule"
                                )
                            },
                            label = { Text("SCHEDULES") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFC23C12) ,        // ← icon nia kor wainhira ita hili
                                selectedTextColor = Color(0xFFC23C12) ,  // ← teks nia kor wainhira ita hili
                                unselectedIconColor = Color.Gray,       // ← icon nia kor wainhira ita la hili
                                unselectedTextColor = Color.Gray,       // ← text nia kor wainhira ita la hili
                                indicatorColor = Color(0xFFF39D82)     // ← kor kabuar ne'ebe sei mosu iha icon nia kotuk
                            )
                        )
                    }
                }
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


@Preview(showBackground = true)
@Composable
fun ClientsListScreenPreview(){
    OrderManagementCakeTheme {
        ClientsListScreen()
    }
}