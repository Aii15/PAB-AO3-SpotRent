package com.pab.spotrent.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.R
import com.pab.spotrent.data.repository.AuthRepository
import com.pab.spotrent.ui.theme.BrandDarkBlue
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow

@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToAccountDetail: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onLogoutSuccess: () -> Unit
) {
    val currentUser by AuthRepository.currentUser.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Section (Navy Background)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(BrandDarkBlue)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_login),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    alpha = 0.5f
                )
            }

            // Menu List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 110.dp) // Increased from 80dp for better spacing
            ) {
                ProfileMenuItem(
                    iconRes = R.drawable.ic_key,
                    label = "Ganti Kata Sandi",
                    onClick = onNavigateToChangePassword
                )
                ProfileMenuItem(
                    iconRes = R.drawable.ic_history,
                    label = "Lihat Riwayat Booking",
                    onClick = onNavigateToHistory
                )
                ProfileMenuItem(
                    iconRes = R.drawable.ic_info,
                    label = "Tentang SpotRent",
                    onClick = { /* Not implemented */ }
                )
                ProfileMenuItem(
                    iconRes = R.drawable.ic_heart,
                    label = "Lihat Wishlist",
                    onClick = { /* Not implemented */ }
                )
                ProfileMenuItem(
                    iconRes = R.drawable.ic_logout,
                    label = "Keluar",
                    textColor = Color.Red,
                    iconTint = Color.Red,
                    onClick = { showLogoutDialog = true }
                )
            }
        }

        // User Info Card (Floating Overlap)
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            border = BorderStroke(1.5.dp, Color(0xFFEEEEEE))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar
                    Surface(
                        modifier = Modifier.size(64.dp),
                        shape = CircleShape,
                        color = Color(0xFF2E7D32) // Standard green avatar
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = currentUser?.fullName?.take(1)?.uppercase() ?: "A",
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(20.dp))
                    
                    Column {
                        Text(
                            text = currentUser?.fullName ?: "Anonim",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandDarkGray
                        )
                        Text(
                            text = "User",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Lihat Profil Button
                Button(
                    onClick = onNavigateToAccountDetail,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
                ) {
                    Text(
                        text = "Lihat Profil",
                        color = BrandDarkGray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Bottom Navigation Placeholder
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
                ProfileBottomNavItem(
                    iconRes = R.drawable.ic_home, 
                    label = "Beranda", 
                    isSelected = false,
                    onClick = onNavigateToHome
                )
                ProfileBottomNavItem(
                    iconRes = R.drawable.ic_history, 
                    label = "Riwayat", 
                    isSelected = false,
                    onClick = onNavigateToHistory
                )
                ProfileBottomNavItem(
                    iconRes = R.drawable.ic_profile, 
                    label = "Profil", 
                    isSelected = true,
                    onClick = { }
                )
            }
        }
    }

    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text(text = "Konfirmasi Keluar", fontWeight = FontWeight.Bold, color = BrandDarkGray) },
            text = { Text(text = "Apakah Anda yakin ingin keluar dari akun SpotRent?", color = BrandDarkGray) },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        AuthRepository.logout()
                        onLogoutSuccess()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
                ) {
                    Text("Ya, Keluar", color = BrandDarkGray, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Batal", color = BrandDarkGray)
                }
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color.White
        )
    }
}

@Composable
fun ProfileMenuItem(
    iconRes: Int,
    label: String,
    textColor: Color = BrandDarkGray,
    iconTint: Color = BrandDarkGray,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = iconTint
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = label,
                    fontSize = 15.sp,
                    color = textColor,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp),
                color = Color(0xFFEEEEEE),
                thickness = 1.dp
            )
        }
    }
}

@Composable
private fun ProfileBottomNavItem(
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
