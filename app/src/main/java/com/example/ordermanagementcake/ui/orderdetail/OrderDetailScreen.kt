package com.example.ordermanagementcake.ui.orderdetail


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.R
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.rememberLifecycleOwner

@Composable
fun OrderDetailScreen(){



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ){
        Text(
            text = "Referencia order #BK-8821",
            fontSize = 20.sp,
            color = Color(0xFF9A2A07)
        )

        Text(
            text = "Vanilla Bean & Raspberry Layer Cake",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 16.dp)
        ) {
//            Box(
//                modifier = Modifier
//                    .width(1000.dp)
//                    .height(200.dp)
//            ) {
//                Image(
//                    painter = painterResource(id = com.example.ordermanagementcake.R.drawable.raspberrylayercake),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                )
//            }
//
//            Text(
//                text = "ORDER SELECIONASAUN",
//                fontSize = 12.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .padding(top = 20.dp)
//            )
//            Text(
//                text = "Vanilla Bean Custom",
//                fontSize = 25.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .padding(top = 5.dp)
//            )
//
//            Text(
//                text = "QUANTIDADE",
//                fontSize = 12.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .padding(top = 20.dp)
//            )
//            Text(
//                text = "Oct 24, 2026",
//                fontSize = 25.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .padding(top = 5.dp)
//            )
//
//            Text(
//                text = "DATA FOTI",
//                fontSize = 12.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .padding(top = 20.dp)
//            )
//            Text(
//                text = "Oct 30, 2026 - 10:30 AM",
//                fontSize = 25.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .padding(top = 5.dp)
//            )
//
//            Text(
//                text = "STATUS AGORA DAUDAUN",
//                fontSize = 12.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .padding(top = 20.dp)
//            )
            Surface(
                color = Color(0xFFF38B46),
                shape = RoundedCornerShape(6000.dp)
            ) {
                Text(
                    text = "- BAKING IN PROGRESS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(9.dp)
                )
            }
            Text(
                text = "NOTA SIRA",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 20.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "iha ne'e mak notes sira sei mosJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJu",
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

            Card(
                modifier = Modifier
                    .width(500.dp)
                    .height(150.dp)
                    .padding(top = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .width(500.dp)
                        .height(500.dp)
                        .padding(top = 27.dp)
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .padding(bottom = 25.dp)
                            .size(100.dp)
                    ) {
                        Image(
                            painter = painterResource(id = com.example.ordermanagementcake.R.drawable.foto_profile),
                            contentDescription = "Foto Profile",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .width(200.dp)
                    ) {
                        Text(
                            text = "Eleanor Rigby",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        Text(
                            text = "+67078654324",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(top = 0.dp)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone Icon",
                        modifier = Modifier
                            .padding(end = 5.dp, top = 20.dp)
                            .size(40.dp)
                    )


                }
            }

        }
    }
//    BottomAppBar(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Button(
//                onClick = {}
//            ) {
//
//            }
//        }
//    }

}

@Preview(showBackground = true)
@Composable
fun OrderDetailScreenPreview(){
    OrderDetailScreen()
}
