package com.pab.spotrent.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.R
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow

@Composable
fun PaymentMethodScreen(
    propertyId: Int,
    startDate: Long,
    endDate: Long,
    onBackClick: () -> Unit,
    onNextClick: (String) -> Unit
) {
    var selectedMethod by remember { mutableStateOf("") }
    
    val paymentMethods = listOf(
        PaymentMethod("Mastercard", "Kartu Debit/Kredit Mastercard", R.drawable.logo_mastercard),
        PaymentMethod("Visa", "Kartu Debit/Kredit Visa", R.drawable.logo_visa),
        PaymentMethod("OVO", "OVO", R.drawable.logo_ovo),
        PaymentMethod("GoPay", "Gopay", R.drawable.logo_gopay),
        PaymentMethod("DANA", "Dana", R.drawable.logo_dana)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Metode Pembayaran",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BrandDarkGray
            )
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Pilih Metode Pembayaran",
            modifier = Modifier.padding(horizontal = 24.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = BrandDarkGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Payment Methods List
        Surface(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)), // Increased thickness
            color = Color.White
        ) {
            Column {
                paymentMethods.forEach { method ->
                    PaymentMethodItem(
                        method = method,
                        isSelected = selectedMethod == method.id,
                        onClick = { selectedMethod = method.id }
                    )
                    if (method != paymentMethods.last()) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFEEEEEE))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Button(
                onClick = { onNextClick(selectedMethod) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandYellow,
                    disabledContainerColor = Color(0xFFE0E0E0),
                    disabledContentColor = Color.Gray
                ),
                enabled = selectedMethod.isNotEmpty()
            ) {
                Text(
                    text = "LANJUTKAN",
                    color = BrandDarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun PaymentMethodItem(
    method: PaymentMethod,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = method.iconRes),
            contentDescription = method.name,
            modifier = Modifier.size(width = 40.dp, height = 24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = method.name,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = BrandDarkGray
        )
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = BrandYellow)
        )
    }
}

data class PaymentMethod(
    val id: String,
    val name: String,
    val iconRes: Int
)
