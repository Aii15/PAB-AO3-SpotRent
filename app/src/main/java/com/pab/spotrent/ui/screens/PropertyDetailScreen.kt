package com.pab.spotrent.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.R
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.pab.spotrent.data.repository.PropertyRepository
import com.pab.spotrent.data.repository.WishlistRepository
import com.pab.spotrent.data.repository.AuthRepository
import com.pab.spotrent.data.repository.BookingRepository
import com.pab.spotrent.data.repository.ReviewRepository
import com.pab.spotrent.ui.theme.BrandDarkBlue
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow
import com.pab.spotrent.ui.theme.SpotRentTheme
import androidx.compose.ui.text.style.TextAlign
import java.text.NumberFormat
import java.util.*

@Composable
fun PropertyDetailScreen(
    propertyId: Int,
    onBackClick: () -> Unit,
    onBookingClick: () -> Unit,
    onLoginRequired: () -> Unit
) {
    val properties by PropertyRepository.properties.collectAsState()
    val property = properties.find { it.id == propertyId } ?: return
    val scrollState = rememberScrollState()
    
    val wishlistedIds by WishlistRepository.wishlistedIds.collectAsState()
    val isWishlisted = wishlistedIds.contains(propertyId)

    val bookings by BookingRepository.bookings.collectAsState()
    val isLoggedIn = AuthRepository.isLoggedIn()
    
    // Find an unreviewed successful booking for this property
    val unreviewedBooking = if (isLoggedIn) {
        bookings.find { it.propertyId == propertyId && it.status == "Berhasil" && !ReviewRepository.hasUserReviewedBooking(it.id) }
    } else null

    var showRatingDialog by remember { mutableStateOf(false) }

    if (showRatingDialog && unreviewedBooking != null) {
        RatingDialog(
            propertyName = property.name,
            onDismiss = { showRatingDialog = false },
            onSubmit = { stars, commentText ->
                val currentUser = AuthRepository.currentUser.value
                val userName = currentUser?.fullName ?: "Anonim"
                ReviewRepository.addReview(
                    propertyId = property.id,
                    bookingId = unreviewedBooking.id,
                    userName = userName,
                    rating = stars,
                    comment = commentText
                )
                showRatingDialog = false
            }
        )
    }
    
    // Images for pager from property model
    val propertyImages = property.detailImages
    val pagerState = rememberPagerState(pageCount = { propertyImages.size })

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Header Image with Pager
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    Image(
                        painter = painterResource(id = propertyImages[page]),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // Photo Counter Badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 64.dp, end = 24.dp), // Increased padding to stay above the white card
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.7f) // Slightly darker for better contrast
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_photo),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${pagerState.currentPage + 1}/${propertyImages.size}",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Back Button
                Surface(
                    onClick = { onBackClick() },
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(24.dp)
                        .size(40.dp),
                    shape = CircleShape,
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Black
                        )
                    }
                }
            }

            // Property Info Card
            Surface(
                modifier = Modifier
                    .offset(y = (-40).dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Pull bar
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(40.dp)
                            .height(4.dp)
                            .background(Color.LightGray, RoundedCornerShape(2.dp))
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = property.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandDarkGray,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                if (AuthRepository.isLoggedIn()) {
                                    WishlistRepository.toggleWishlist(propertyId)
                                } else {
                                    onLoginRequired()
                                }
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_like),
                                contentDescription = "Wishlist",
                                tint = if (isWishlisted) Color.Red else Color.Gray,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = property.location,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(modifier = Modifier.width(1.dp).height(12.dp).background(Color.LightGray))
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = BrandYellow
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${property.rating} (${property.reviews} Reviews)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal, // Changed from Bold as per request
                            color = BrandDarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = property.description,
                        fontSize = 14.sp,
                        color = BrandDarkGray,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Pengelola
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)), // Increased thickness
                        color = Color.White,
                        contentColor = BrandDarkGray
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier.size(40.dp),
                                shape = CircleShape,
                                color = Color(0xFF2E7D32)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(property.partnerLogoText, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = property.partnerName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(text = "Pengelola Operasional", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Spesifikasi
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)), // Increased thickness
                        color = Color.White,
                        contentColor = BrandDarkGray
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Spesifikasi Properti", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            val specIconMap = mapOf(
                                "Sanitasi" to R.drawable.ic_sanitasi,
                                "Listrik dan Penerangan" to R.drawable.ic_listrik,
                                "CCTV" to R.drawable.ic_cctv,
                                "Parkir Mobil" to R.drawable.ic_parkir,
                                "Sprinkler Water" to R.drawable.ic_sprinkler,
                                "Permit Included" to R.drawable.ic_permit,
                                "APAR" to R.drawable.ic_apar,
                                "Outdoor" to R.drawable.ic_outdoor
                            )
                            val specs = property.specifications.mapNotNull { name ->
                                specIconMap[name]?.let { iconRes -> name to iconRes }
                            }

                            Column {
                                specs.chunked(2).forEach { rowSpecs ->
                                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                                        rowSpecs.forEach { spec ->
                                            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    painter = painterResource(id = spec.second),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp),
                                                    tint = BrandDarkGray
                                                )
                                                Spacer(modifier = Modifier.width(8.dp)) // Reduced spacer
                                                Text(
                                                    text = spec.first, 
                                                    fontSize = 11.sp, // Slightly smaller font to fit in one line
                                                    color = BrandDarkGray,
                                                    maxLines = 1
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Ulasan
                    Divider(color = Color(0xFFEEEEEE))
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Ulasan", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = BrandYellow
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${property.rating} ( ${property.reviews} Ulasan )",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        if (unreviewedBooking != null) {
                            val BrandLinkBlue = Color(0xFF3B5BDB)
                            TextButton(
                                onClick = { showRatingDialog = true }
                            ) {
                                Text(
                                    text = "Tulis Ulasan",
                                    color = BrandLinkBlue,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val propertyReviews by ReviewRepository.getReviewsForProperty(propertyId).collectAsState(initial = emptyList())

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (propertyReviews.isEmpty()) {
                            item {
                                Text(
                                    text = "Belum ada ulasan.",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(vertical = 16.dp)
                                )
                            }
                        } else {
                            items(propertyReviews) { review ->
                                ReviewCard(review = review)
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(120.dp))
                }
            }
        }

        // Fixed Bottom Bar
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color.White,
            shadowElevation = 16.dp
        ) {
            Column(modifier = Modifier.navigationBarsPadding()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(start = 24.dp, end = 16.dp), // Reduced end padding to push button right
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "IDR ${formatPrice(property.price)}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandDarkGray
                        )
                        Text(text = "Per Hari", fontSize = 12.sp, color = Color.Gray)
                    }
                    
                    Button(
                        onClick = onBookingClick,
                        modifier = Modifier
                            .width(160.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
                    ) {
                        Text(text = "Pesan", color = BrandDarkGray, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewCard(review: com.pab.spotrent.data.model.Review) {
    Surface(
        modifier = Modifier.width(280.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
        color = Color.White,
        contentColor = BrandDarkGray
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(32.dp),
                    shape = CircleShape,
                    color = Color(0xFF2E7D32)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(review.userAvatarText, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = review.userName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = review.date, fontSize = 10.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                repeat(review.rating) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = BrandYellow
                    )
                }
                repeat(5 - review.rating) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = Color.LightGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.comment,
                fontSize = 12.sp,
                color = BrandDarkGray,
                maxLines = 3,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun RatingDialog(
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

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
fun PropertyDetailScreenPreview() {
    SpotRentTheme {
        PropertyDetailScreen(propertyId = 1, onBackClick = {}, onBookingClick = {}, onLoginRequired = {})
    }
}
