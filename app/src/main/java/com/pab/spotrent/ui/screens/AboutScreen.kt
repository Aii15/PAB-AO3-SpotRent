package com.pab.spotrent.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {
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
                    text = "SpotRent",
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

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .verticalScroll(scrollState)
        ) {
            val contentParagraph1 = "SpotRent merupakan platform digital penyewaan lokasi dan properti yang dikhususkan untuk kebutuhan syuting komersial. Platform ini dirancang untuk menjadi penghubung antara pemilik properti (mitra) dan pihak kreatif seperti produser atau location manager (user) yang membutuhkan lokasi untuk kebutuhan produksi film, video, maupun komersial."
            val contentParagraph2 = "Dengan mempermudah proses pencarian, pemilihan, pemesanan, hingga pembayaran lokasi syuting secara terpusat, SpotRent bertujuan untuk menciptakan ekosistem penyewaan yang efisien dan cepat, sehingga dapat membantu menghemat waktu serta tenaga pada tahap pra-produksi bagi industri kreatif."

            Text(
                text = contentParagraph1,
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = contentParagraph2,
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                lineHeight = 22.sp
            )
        }

        // Bottom Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
            ) {
                Text(
                    text = "KEMBALI",
                    color = BrandDarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}
