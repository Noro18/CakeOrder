package com.example.ordermanagementcake.ui.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordermanagementcake.R
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ordermanagementcake.ui.components.BottomNavigationBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(){
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
            var selectedItem by remember { mutableStateOf(1) }
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
            Text(
                text = stringResource(id = R.string.current_order),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(top = 16.dp, start = 15.dp)

            )
            Text(
                text = stringResource(id = R.string.culinary_cration),
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 7.dp, start = 15.dp)
            )

            // search bar
            var searchText by remember { mutableStateOf("") }

            OutlinedTextField(
                value = searchText,
                onValueChange = {searchText = it},
                label = {Text(
                    text =  stringResource(id = R.string.search_order),
                    modifier = Modifier.padding(all = 0.dp)
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
                    .fillMaxWidth()
                    .padding(end = 10.dp, start = 10.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 15.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF87146),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_all_order)
                    )
                }

                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_pending_order)
                    )

                }

                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_inprogress_order)
                    )

                }

                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_ready_order)
                    )

                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_complete)
                    )

                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.double_chodolate_fudge),
                        contentDescription = "Foto Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)

                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .width(120.dp)

                    ) {
                        Text(
                            text = stringResource(id = R.string.naran1_order),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(80.dp),
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(id = R.string.cake1_type),
                            modifier = Modifier
                                .fillMaxWidth(),
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(90.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        Text(
                            text = stringResource(id = R.string.pickup1),
                            fontSize = 10.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .width(80.dp),
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(id = R.string.time_pickup1),
                            fontSize = 11.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .width(80.dp)
                        )
                    }
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(start = 250.dp, end = 10.dp, bottom  = 10.dp)
                        .height(35.dp)
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEE8111),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_pending_order),
                        fontSize = 10.sp
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.wild_berry_midley),
                        contentDescription = "Foto Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)

                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .width(120.dp)

                    ) {
                        Text(
                            text = stringResource(id = R.string.naran2_order),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(80.dp),
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(id = R.string.cake2_type),
                            modifier = Modifier
                                .fillMaxWidth(),
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(90.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        Text(
                            text = stringResource(id = R.string.pickup2),
                            fontSize = 10.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .width(80.dp),
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(id = R.string.time_pickup2),
                            fontSize = 11.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .width(80.dp)
                        )
                    }
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(start = 250.dp, end = 10.dp, bottom  = 10.dp)
                        .height(35.dp)
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8DE7EF),
                        contentColor = Color.Blue
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_inprogress_order),
                        fontSize = 10.sp
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.zesty_lemon_drezzy),
                        contentDescription = "Foto Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)

                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .width(120.dp)

                    ) {
                        Text(
                            text = stringResource(id = R.string.naran3_order),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(80.dp),
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(id = R.string.cake3_type),
                            modifier = Modifier
                                .fillMaxWidth(),
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(90.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        Text(
                            text = stringResource(id = R.string.pickup3),
                            fontSize = 10.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .width(80.dp),
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(id = R.string.time_pickup3),
                            fontSize = 11.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .width(80.dp)
                        )
                    }
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(start = 250.dp, end = 10.dp, bottom  = 10.dp)
                        .height(35.dp)
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7BE068),
                        contentColor = Color(0xFF29601E)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_ready_order),
                        fontSize = 10.sp
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.custom_wedding_tier),
                        contentDescription = "Foto Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)

                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .width(120.dp)

                    ) {
                        Text(
                            text = stringResource(id = R.string.naran4_order),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(80.dp),
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(id = R.string.cake4_type),
                            modifier = Modifier
                                .fillMaxWidth(),
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(90.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        Text(
                            text = stringResource(id = R.string.pickup4),
                            fontSize = 10.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .width(80.dp),
                            maxLines = 1
                        )
                        Text(
                            text = stringResource(id = R.string.time_pickup4),
                            fontSize = 11.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .width(80.dp)
                        )
                    }
                }
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(start = 250.dp, end = 10.dp, bottom  = 10.dp)
                        .height(35.dp)
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBABDB9),
                        contentColor = Color(0xFF424442)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_complete),
                        fontSize = 10.sp
                    )
                }
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun OrderListScreenPreview(){
    _root_ide_package_.com.example.ordermanagementcake.ui.theme.OrderManagementCakeTheme {
        OrderListScreen()
    }
}