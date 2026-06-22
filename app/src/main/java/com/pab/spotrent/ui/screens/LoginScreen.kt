package com.pab.spotrent.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.R
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.pab.spotrent.data.repository.AuthRepository
import com.pab.spotrent.ui.theme.BrandDarkBlue
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandLightGray
import com.pab.spotrent.ui.theme.BrandLinkBlue
import com.pab.spotrent.ui.theme.BrandYellow
import com.pab.spotrent.ui.theme.SpotRentTheme

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandDarkBlue)
    ) {
        // Background Pattern
        Image(
            painter = painterResource(id = R.drawable.bg_login),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f),
            contentScale = ContentScale.FillBounds
        )

        // Back Button
        Surface(
            onClick = onBackClick,
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

        // Main layout Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top section with Logo and Text
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo from drawable
                Image(
                    painter = painterResource(id = R.drawable.logo_spotrent),
                    contentDescription = "Logo SpotRent",
                    modifier = Modifier.size(100.dp)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "SpotRent",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Bottom section with Login Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .navigationBarsPadding()
                        .padding(horizontal = 32.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandDarkGray
                    )

                Spacer(modifier = Modifier.height(32.dp))

                // Username/Email field
                OutlinedTextField(
                    value = identifier,
                    onValueChange = { identifier = it },
                    placeholder = { Text("Username Or Email", color = Color.Gray) },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = BrandDarkGray)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = BrandDarkGray,
                        unfocusedTextColor = BrandDarkGray,
                        focusedContainerColor = BrandLightGray,
                        unfocusedContainerColor = BrandLightGray,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        errorMessage = "" // clear error when typing
                    },
                    placeholder = { Text("Password", color = Color.Gray) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = BrandDarkGray)
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = BrandDarkGray,
                        unfocusedTextColor = BrandDarkGray,
                        focusedContainerColor = BrandLightGray,
                        unfocusedContainerColor = BrandLightGray,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Login Button
                Button(
                    onClick = {
                        if (identifier.isBlank() || password.isBlank()) {
                            errorMessage = "Email atau password Anda salah"
                        } else {
                            if (AuthRepository.login(identifier, password)) {
                                errorMessage = ""
                                onLoginSuccess()
                            } else {
                                errorMessage = "Email atau password Anda salah"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
                ) {
                    Text("Masuk", color = BrandDarkGray, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Or", color = BrandDarkGray)

                Spacer(modifier = Modifier.height(16.dp))

                // Register Link
                Row {
                    Text(text = "Belum Punya Akun? ", color = BrandDarkGray)
                    Text(
                        text = "Daftar",
                        color = BrandLinkBlue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onRegisterClick() }
                    )
                }
            }
        }
    }
}
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
fun LoginScreenPreview() {
    SpotRentTheme {
        LoginScreen(onLoginSuccess = {}, onRegisterClick = {}, onBackClick = {})
    }
}
