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
            val contentText = "Lorem Ipsum Dolor Sit Amet, Consectetur Adipiscing Elit. Suspendisse Ipsum Dolor, Venenatis Ut Egestas Quis, Accumsan Eu Mi. Duis Nibh Mi, Dibus Nec Arcu Sit Amet, Iaculis Aliquet Magna. Aliquam Pharetra Libero Urna, Ac Consectetur Sapien Aliquam Sit Amet. Nulla Lacinia, Eros Non Ultrices Vestibulum, Ligula Felis Laoreet Mi, Nec Placerat Ligula Est Vel Neque. Donec Rutrum Dui Non Purus Ultrices Cursus Quis Non Mauris. Aenean Maximus Ornare Fringilla. Nullam Ac Feugiat Lorem. Pellentesque Egestas Dolor Ac Est Consequat Semper. Suspendisse Pretium Porta Laoreet. Ut Eget Erat Dui. Aliquam Venenatis Interdum Nunc Id Dibus. Sed Non Congue Massa. Integer Lectus Velit, Pulvinar Ut Maximus Pulvinar, Bibendum Ac Ipsum. Aliquam Nec Lorem Velit."

            Text(
                text = contentText,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Justify,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = contentText,
                fontSize = 14.sp,
                color = Color.Black,
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
