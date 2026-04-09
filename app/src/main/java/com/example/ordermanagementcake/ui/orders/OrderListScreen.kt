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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ordermanagementcake.ui.components.BottomNavigationBar
import com.example.ordermanagementcake.ui.navigation.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(){
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                            .weight(1f) // ida ne'e atu nun'ee elementu sira pas pas
                    ) {
                        Text(
                            text = stringResource(id = R.string.naran1_order),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = stringResource(id = R.string.cake1_type),
                            fontSize = 15.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.End // allgiht data ba iah liman loos
                    ) {
                        Text(
                            text = stringResource(id = R.string.pickup1),
                            fontSize = 15.sp,
                            color = Color.Black,
                        )
                        Text(
                            text = stringResource(id = R.string.time_pickup1),
                            fontSize = 11.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier.height(35.dp),
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
