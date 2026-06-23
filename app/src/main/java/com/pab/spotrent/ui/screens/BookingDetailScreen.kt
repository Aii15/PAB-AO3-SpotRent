package com.pab.spotrent.ui.screens

import androidx.compose.foundation.*
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
import com.pab.spotrent.data.repository.AuthRepository
import com.pab.spotrent.data.repository.ReviewRepository
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow
import androidx.compose.runtime.*
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetailScreen(
    bookingId: String,
    onBackClick: () -> Unit
) {
    val bookings by BookingRepository.bookings.collectAsState()
    val booking = bookings.find { it.id == bookingId } ?: return
    
    val reviews by ReviewRepository.reviews.collectAsState()
    val myReview = reviews.find { it.bookingId == booking.id }
    
    var showRatingDialog by remember { mutableStateOf(false) }

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

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
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

            if (myReview == null) {
                if (booking.status == "Berhasil") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
                        color = Color.White
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Bagaimana pengalaman Anda?",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = BrandDarkGray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Beri penilaian untuk membantu meningkatkan kualitas layanan kami.",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { showRatingDialog = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp),
                                shape = RoundedCornerShape(22.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
                            ) {
                                Text(
                                    text = "Beri Rating & Ulasan",
                                    color = BrandDarkGray,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ulasan Anda",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = BrandDarkGray
                            )
                            Text(
                                text = myReview.date,
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            repeat(myReview.rating) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = BrandYellow
                                )
                            }
                            repeat(5 - myReview.rating) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = Color.LightGray
                                )
                            }
                        }
                        if (myReview.comment.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = myReview.comment,
                                fontSize = 13.sp,
                                color = BrandDarkGray,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            if (showRatingDialog) {
                RatingDialog(
                    propertyName = booking.propertyName,
                    onDismiss = { showRatingDialog = false },
                    onSubmit = { stars, commentText ->
                        val currentUser = AuthRepository.currentUser.value
                        val userName = currentUser?.fullName ?: "Anonim"
                        ReviewRepository.addReview(
                            propertyId = booking.propertyId,
                            bookingId = booking.id,
                            userName = userName,
                            rating = stars,
                            comment = commentText
                        )
                        showRatingDialog = false
                    }
                )
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

@Composable
fun RatingDialog(
    propertyName: String,
    onDismiss: () -> Unit,
    onSubmit: (Int, String) -> Unit
) {
    var rating by remember { mutableStateOf(5) }
    var comment by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(rating, comment)
                },
                colors = ButtonDefaults.buttonColors(containerColor = BrandYellow),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Kirim", color = BrandDarkGray, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal", color = Color.Gray)
            }
        },
        title = {
            Text(
                text = "Beri Rating & Ulasan",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = BrandDarkGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = propertyName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                // Interactive Star Rating
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..5) {
                         IconButton(
                             onClick = { rating = i },
                             modifier = Modifier.size(36.dp)
                         ) {
                             Icon(
                                 painter = painterResource(id = R.drawable.ic_star),
                                 contentDescription = "$i Bintang",
                                 modifier = Modifier.size(32.dp),
                                 tint = if (i <= rating) BrandYellow else Color.LightGray
                             )
                         }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Comment Text Field
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    placeholder = { Text("Tulis komentar Anda di sini...") },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandYellow,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = Color.White
    )
}
