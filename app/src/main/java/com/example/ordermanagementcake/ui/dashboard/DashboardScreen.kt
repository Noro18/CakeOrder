package com.example.ordermanagementcake.ui.dashboard


import android.icu.text.ListFormatter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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


// ida ne'e mak file intissssss
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
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

        Spacer(modifier = Modifier.height(16.dp))

       Row(
           modifier = Modifier.fillMaxWidth()
       ) {
           Text(
               text = stringResource(id = R.string.upcoming_pickups),
               fontSize = 20.sp,
               fontWeight = FontWeight.Bold,
               modifier = Modifier
                   .padding(start = 10.dp)
           )

           Text(
               text = stringResource(id = R.string.view_all),
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 140.dp, top = 5.dp),
               color = Color(0xFF9A2A07),
               fontSize = 15.sp
           )
       }

        Card(
            modifier = Modifier
                .width(380.dp)
                .padding(start = 10.dp, top = 16.dp)
        ) {
            Row(
                modifier = Modifier
            ) {
                Image(
                    painter = painterResource(id = R.drawable.double_chodolate_fudge),
                    contentDescription = "Foto Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(70.dp)
                        .clip(CircleShape)

                )

                Column(
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(
                        text = "Eleanor Shellstrop",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Salted Caramel Macaron Tower",
                        modifier = Modifier
                            .width(200.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp)
                ) {
                    Text(
                        text = "Today",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9A2A07)
                    )
                    Text(
                        text = "09:00 AM"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Orderan agora dau-daun",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .width(380.dp)
                .height(120.dp)
                .padding(start = 10.dp, top = 16.dp)
        ) {
            Row(
                modifier = Modifier.width(500.dp)
            ) {
                Column(
                    modifier = Modifier
                        .width(250.dp)
                        .padding(start = 15.dp)
                ) {
                    Text(
                        text = "Jason Mendoza",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "6x Jalapeno Cornbread Loaves",
                        modifier = Modifier
                            .width(200.dp),
                        fontSize = 13.sp
                    )

                    Text(
                        text = "Order #8841",
                        modifier = Modifier
                            .width(200.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 5.dp)
                ) {
                   Button(
                       onClick = {},
                       colors = ButtonDefaults.buttonColors(
                           containerColor = Color(0xFFA9EF95),
                           contentColor = Color.White
                       ),
                       modifier = Modifier
                           .width(90.dp)
                           .height(45.dp)
                           .padding(top = 10.dp)
                   ) {
                       Text(
                           text = "Ready",
                           fontWeight = FontWeight.Bold,
                           color = Color(0xFF31912E),
                       )
                   }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "$48.00",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = 30.dp)
                    )
                }
            }
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