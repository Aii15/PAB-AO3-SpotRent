package com.pab.spotrent.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.R
import com.pab.spotrent.data.repository.PropertyRepository
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
fun PaymentConfirmationScreen(
    propertyId: Int,
    startDate: Long,
    endDate: Long,
    paymentMethod: String,
    onBackClick: () -> Unit,
    onChangeDateClick: () -> Unit
) {
    val property = PropertyRepository.getPropertyById(propertyId) ?: return
    
    val diffInMillis = endDate - startDate
    val days = (TimeUnit.MILLISECONDS.toDays(diffInMillis) + 1).toInt()
    val totalPrice = property.price * days

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
                text = "Nota Pemesanan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BrandDarkGray
            )
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Confirmation Card
        Surface(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)), // Increased thickness and slightly darker border
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Property Info Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = property.thumbnailRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp) // Slightly smaller for better fit
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = property.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandDarkGray
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_location),
                                contentDescription = null,
                                modifier = Modifier.size(12.dp),
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = property.location,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star),
                                contentDescription = null,
                                modifier = Modifier.size(12.dp),
                                tint = BrandYellow
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${property.rating} (${property.reviews} Reviews)",
                                fontSize = 11.sp,
                                color = BrandDarkGray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                Spacer(modifier = Modifier.height(20.dp))

                // Date Range Section - VERTICAL STACKED & CENTERED
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Dari", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = formatDateWithDay(startDate),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandDarkGray,
                            textAlign = TextAlign.Center
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Arrow pointing down to show flow
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp).graphicsLayer(rotationZ = 270f),
                        tint = Color.LightGray
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Sampai", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = formatDateWithDay(endDate),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandDarkGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                
                // Change Date Button
                OutlinedButton(
                    onClick = onChangeDateClick,
                    modifier = Modifier.align(Alignment.CenterHorizontally).height(36.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(18.dp),
                    border = BorderStroke(1.5.dp, BrandDarkGray) // Consistent border thickness
                ) {
                    Text(text = "Ubah Tanggal", color = BrandDarkGray, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))
                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                Spacer(modifier = Modifier.height(20.dp))

                // Total Price
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Total Harga", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "IDR ${formatPrice(totalPrice)}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = BrandDarkGray
                    )
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
                onClick = { /* Non-functional for now as per instruction */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
            ) {
                Text(
                    text = "BAYAR",
                    color = BrandDarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

private fun formatDateWithDay(millis: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis
    val dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale("id", "ID"))
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("id", "ID"))
    val year = calendar.get(Calendar.YEAR)
    return "$dayName - $day $month $year"
}

