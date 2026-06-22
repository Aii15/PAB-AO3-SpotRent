package com.pab.spotrent.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.R
import com.pab.spotrent.data.repository.BookingRepository
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetailScreen(
    bookingId: String,
    onBackClick: () -> Unit
) {
    val booking = BookingRepository.getBookingById(bookingId) ?: return

    val diffInMillis = booking.endDate - booking.startDate
    val days = (TimeUnit.MILLISECONDS.toDays(diffInMillis) + 1).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Detail Pesanan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandDarkGray
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = BrandDarkGray
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
        )

        HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Status Header
            Surface(
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Status: ${booking.status}",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Booking ID Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "ID Pesanan", fontSize = 12.sp, color = Color.Gray)
                    Text(text = booking.id, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = BrandDarkGray)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color(0xFFEEEEEE))
                    Spacer(modifier = Modifier.height(16.dp))

                    // Property Info
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = booking.propertyThumbnail),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = booking.propertyName, fontWeight = FontWeight.Bold, color = BrandDarkGray)
                            Text(text = booking.propertyLocation, fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Detail Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    DetailRow(label = "Tanggal Check-in", value = formatDateWithDay(booking.startDate))
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Tanggal Check-out", value = formatDateWithDay(booking.endDate))
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Durasi", value = "$days Hari")
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Metode Pembayaran", value = booking.paymentMethod)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color(0xFFEEEEEE))
                    Spacer(modifier = Modifier.height(16.dp))

                    // Total Price Section - Improved Layout
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = "Total Pembayaran", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "IDR ${NumberFormat.getInstance(Locale("id", "ID")).format(booking.totalPrice)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = BrandDarkGray,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label, 
            fontSize = 13.sp, 
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value, 
            fontSize = 13.sp, 
            fontWeight = FontWeight.Bold, 
            color = BrandDarkGray,
            modifier = Modifier.weight(1.5f),
            textAlign = TextAlign.End
        )
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
