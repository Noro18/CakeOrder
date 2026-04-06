package com.example.ordermanagementcake.ui.dashboard

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.R
import com.example.ordermanagementcake.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    Scaffold(
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
                            text = stringResource(id = R.string.name_profile),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(70.dp))
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
            var selectedItem by remember { mutableStateOf(0) }
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
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
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, start = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.greeting),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Text(
                    text = stringResource(id = R.string.datetime_dashboard),
                    color = Color.Gray,
                    fontSize = 20.sp
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 4.dp)

                ) {
                    Card(
                        modifier = Modifier
                            .width(180.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.num_todays_order),
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 10.dp)
                            )
                            Spacer(modifier = Modifier.height(50.dp))

                            Text(
                                text = stringResource(id = R.string.todays_order),
                                modifier = Modifier
                                    .padding(start = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Card(
                        modifier = Modifier
                            .width(180.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.num_pending),
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 10.dp)
                            )
                            Spacer(modifier = Modifier.height(50.dp))

                            Text(
                                text = stringResource(id = R.string.btn_pending_order),
                                modifier = Modifier
                                    .padding(start = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(7.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 4.dp)

                ) {
                    Card(
                        modifier = Modifier
                            .width(180.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.num_ready),
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 10.dp)
                            )
                            Spacer(modifier = Modifier.height(50.dp))

                            Text(
                                text = stringResource(id = R.string.ready_for_pickup),
                                modifier = Modifier
                                    .padding(start = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Card(
                        modifier = Modifier
                            .width(180.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.num_total_clients),
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 10.dp)
                            )
                            Spacer(modifier = Modifier.height(50.dp))

                            Text(
                                text = stringResource(id = R.string.total_client),
                                modifier = Modifier
                                    .padding(start = 5.dp, bottom = 5.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview(){
    _root_ide_package_.com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme {
        DashboardScreen()
    }
}