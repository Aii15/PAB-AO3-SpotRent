package com.pab.spotrent.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.data.repository.PropertyRepository
import com.pab.spotrent.data.repository.WishlistRepository
import com.pab.spotrent.ui.theme.BrandDarkGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(
    onBackClick: () -> Unit,
    onPropertyClick: (Int) -> Unit
) {
    val wishlistedIds by WishlistRepository.wishlistedIds.collectAsState()
    val wishlistedProperties = PropertyRepository.dummyProperties.filter { wishlistedIds.contains(it.id) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Bar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Wishlist Saya",
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

        if (wishlistedProperties.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Belum ada properti di wishlist Anda.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .verticalScroll(scrollState)
            ) {
                val chunkedProperties = wishlistedProperties.chunked(2)
                chunkedProperties.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        rowItems.forEach { property ->
                            Box(modifier = Modifier.weight(1f)) {
                                PropertyCard(
                                    property = property,
                                    onClick = { onPropertyClick(property.id) }
                                )
                            }
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
