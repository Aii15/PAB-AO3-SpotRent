package com.pab.spotrent.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.R
import com.pab.spotrent.data.model.Property
import com.pab.spotrent.data.repository.AuthRepository
import com.pab.spotrent.data.repository.PropertyRepository
import com.pab.spotrent.ui.theme.BrandDarkBlue
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow
import com.pab.spotrent.ui.theme.Poppins
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.pab.spotrent.ui.theme.SpotRentTheme
import java.text.NumberFormat
import java.util.*

@Composable
fun HomeScreen(
    onPropertyClick: (Int) -> Unit,
    onLoginClick: () -> Unit,
    onProfileClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    val properties by PropertyRepository.properties.collectAsState()
    val categories = listOf("Semua", "Komersial", "Hunian", "Lanskap", "Studio", "Heritage")
    var selectedCategory by remember { mutableStateOf("Semua") }
    var searchQuery by remember { mutableStateOf("") }
    
    val currentUser by AuthRepository.currentUser.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Main Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Reduced from 340 to give more space below
                    .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_hero),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp)) // More compact top bar
                    
                    // Top Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_spotrent),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp) // Slightly smaller logo
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SpotRent",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp // Slightly smaller text
                            )
                        }
                        
                        if (currentUser != null) {
                            // Profile Icon when logged in
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.2f),
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable { onProfileClick() }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_profile),
                                    contentDescription = "Profile",
                                    modifier = Modifier.padding(8.dp),
                                    tint = Color.White
                                )
                            }
                        } else {
                            // Login Button when not logged in
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = BrandYellow,
                                modifier = Modifier.clickable { onLoginClick() }
                            ) {
                                Text(
                                    text = "Masuk",
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BrandDarkGray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Temukan Lokasi\nSyuting Terbaik",
                        color = Color.White,
                        fontSize = 22.sp, // Slightly more compact
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp
                    )
                    Text(
                        text = "Dalam Sekejap",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Search Bar
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp), // Reduced height from 48
                        shape = RoundedCornerShape(22.dp),
                        color = Color.White
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            Box(contentAlignment = Alignment.CenterStart) {
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "Cari Lokasi Syuting",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                }
                                BasicTextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    textStyle = TextStyle(
                                        fontFamily = Poppins,
                                        fontSize = 14.sp,
                                        color = BrandDarkGray
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Categories with Edge Cutting Affordance
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 24.dp), 
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    Surface(
                        onClick = { selectedCategory = category },
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) BrandYellow else Color.White,
                        border = if (isSelected) null else BorderStroke(1.5.dp, Color(0xFFE0E0E0)), // Increased thickness
                        shadowElevation = if (isSelected) 4.dp else 2.dp
                    ) {
                        Text(
                            text = category,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp), // More compact chips
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = BrandDarkGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Property Grid
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                // Duplicate properties to ensure scrollability
                val filteredProperties = if (selectedCategory == "Semua") {
                    properties
                } else {
                    properties.filter { it.type == selectedCategory }
                }
                val chunkedProperties = filteredProperties.chunked(2)
                chunkedProperties.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(20.dp) // More spacing for breath
                    ) {
                        rowItems.forEach { property ->
                            Box(modifier = Modifier.weight(1f)) {
                                PropertyCard(property = property, onClick = { onPropertyClick(property.id) })
                            }
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(120.dp)) // Extra space for floating bottom nav
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
                BottomNavItem(
                    iconRes = R.drawable.ic_home, 
                    label = "Beranda", 
                    isSelected = true,
                    onClick = { /* Stay on Home */ }
                )
                BottomNavItem(
                    iconRes = R.drawable.ic_history, 
                    label = "Riwayat", 
                    isSelected = false,
                    onClick = onHistoryClick
                )
                BottomNavItem(
                    iconRes = R.drawable.ic_profile, 
                    label = "Profil", 
                    isSelected = false,
                    onClick = onProfileClick
                )
            }
        }
    }
}

@Composable
fun PropertyCard(property: Property, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) { // Tokopedia style: Square Image
                Image(
                    painter = painterResource(id = property.thumbnailRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Property Type Tag
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = BrandDarkBlue.copy(alpha = 0.8f)
                ) {
                    Text(
                        text = property.type,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                Text(
                    text = property.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = BrandDarkGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
                
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "IDR ${formatPrice(property.price)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandDarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        modifier = Modifier.size(10.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = property.location,
                        fontSize = 10.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        modifier = Modifier.size(10.dp),
                        tint = BrandYellow
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = property.rating.toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavItem(
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

fun formatPrice(price: Long): String {
    val formatter = NumberFormat.getInstance(Locale("id", "ID"))
    return formatter.format(price)
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
fun HomeScreenPreview() {
    SpotRentTheme {
        HomeScreen(onPropertyClick = {}, onLoginClick = {}, onProfileClick = {}, onHistoryClick = {})
    }
}
