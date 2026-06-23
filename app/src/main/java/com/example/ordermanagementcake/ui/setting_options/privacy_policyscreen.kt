package com.example.ordermanagementcake.ui.setting_options

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Privacy Policy Bottom Sheet tailored for Order Management Cake.
 * Language: Tetum
 */
@Composable
fun PrivacyPolicySheet() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            // Drag Handle
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .background(Color(0xFF505050), RoundedCornerShape(2.dp))
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Title: Polítika Privasidade
            Text(
                text = "Polítika privasidade",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Adjusted Body text for Cake Order Management context
            Text(
                text = "Ami halibur de'it dadus ne'ebé presiza, hodi jere ita-boot nia pedidu dose sira. Ita-nia rekordu pedidu, kliente, no finansa nian sei hela de'it iha device laran, se ita-boot la loke funsaun 'backup' hodi rai dados sira iha fatin seluk.",
                fontSize = 16.sp,
                lineHeight = 24.sp,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PrivacyPolicySheetPreview() {
    PrivacyPolicySheet()
}
