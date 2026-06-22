package com.pab.spotrent.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.R
import com.pab.spotrent.data.model.Booking
import com.pab.spotrent.data.repository.BookingRepository
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBackClick: () -> Unit,
    onBookingClick: (String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val bookings by BookingRepository.bookings.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top Bar
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Riwayat Booking",
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )

            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)

            if (bookings.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Belum ada riwayat booking", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(bookings) { booking ->
                        BookingHistoryItem(booking, onClick = { onBookingClick(booking.id) })
                    }
                }
            }
        }

        // Floating Bottom Navigation (STAYS FIXED)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 16.dp, start = 24.dp, end = 24.dp)
                .shadow(12.dp, RoundedCornerShape(32.dp))
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White)
                .height(72.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HistoryBottomNavItem(
                    iconRes = R.drawable.ic_home, 
                    label = "Beranda", 
                    isSelected = false,
                    onClick = onNavigateToHome
                )
                HistoryBottomNavItem(
                    iconRes = R.drawable.ic_history, 
                    label = "Riwayat", 
                    isSelected = true,
                    onClick = { /* Stay here */ }
                )
                HistoryBottomNavItem(
                    iconRes = R.drawable.ic_profile, 
                    label = "Profil", 
                    isSelected = false,
                    onClick = onNavigateToProfile
                )
            }
        }
    }
}

@Composable
fun BookingHistoryItem(booking: Booking, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val thumbnailRes = if (booking.propertyThumbnail != 0) booking.propertyThumbnail else R.drawable.prop_default
            Image(
                painter = painterResource(id = thumbnailRes),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = booking.propertyName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandDarkGray
                )
                Text(
                    text = booking.propertyLocation,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = booking.status,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32) // Success Green
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "IDR ${NumberFormat.getInstance(Locale("id", "ID")).format(booking.totalPrice)}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandDarkGray
                )
            }
        }
    }
}

@Composable
private fun HistoryBottomNavItem(
    iconRes: Int, 
    label: String, 
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (isSelected) BrandDarkGray else Color.LightGray
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) BrandDarkGray else Color.LightGray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
