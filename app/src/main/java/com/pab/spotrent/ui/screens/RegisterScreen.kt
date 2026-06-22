package com.pab.spotrent.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    var identifierError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var generalError by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandDarkBlue)
    ) {
        // Background Pattern
        Image(
            painter = painterResource(id = com.pab.spotrent.R.drawable.bg_login),
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
                    painter = painterResource(id = com.pab.spotrent.R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }
        }

        // Top section with Logo and Text
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo from drawable
            Image(
                painter = painterResource(id = com.pab.spotrent.R.drawable.logo_spotrent),
                contentDescription = "Logo SpotRent",
                modifier = Modifier.size(120.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "SpotRent",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Bottom section with Register Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandDarkGray
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Username/Email field
                OutlinedTextField(
                    value = identifier,
                    onValueChange = { 
                        identifier = it
                        identifierError = ""
                        generalError = ""
                    },
                    placeholder = { Text("Username Or Email", color = Color.Gray) },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = BrandDarkGray)
                    },
                    isError = identifierError.isNotEmpty(),
                    supportingText = {
                        if (identifierError.isNotEmpty()) {
                            Text(text = identifierError, color = Color.Red)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = BrandDarkGray,
                        unfocusedTextColor = BrandDarkGray,
                        focusedContainerColor = BrandLightGray,
                        unfocusedContainerColor = BrandLightGray,
                        focusedBorderColor = if (identifierError.isNotEmpty()) Color.Red else Color.Transparent,
                        unfocusedBorderColor = if (identifierError.isNotEmpty()) Color.Red else Color.Transparent
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        passwordError = ""
                        generalError = ""
                    },
                    placeholder = { Text("Password", color = Color.Gray) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = BrandDarkGray)
                    },
                    isError = passwordError.isNotEmpty(),
                    supportingText = {
                        if (passwordError.isNotEmpty()) {
                            Text(text = passwordError, color = Color.Red)
                        }
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = BrandDarkGray,
                        unfocusedTextColor = BrandDarkGray,
                        focusedContainerColor = BrandLightGray,
                        unfocusedContainerColor = BrandLightGray,
                        focusedBorderColor = if (passwordError.isNotEmpty()) Color.Red else Color.Transparent,
                        unfocusedBorderColor = if (passwordError.isNotEmpty()) Color.Red else Color.Transparent
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Confirm Password field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { 
                        confirmPassword = it
                        confirmPasswordError = ""
                        generalError = ""
                    },
                    placeholder = { Text("Confirm Password", color = Color.Gray) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = BrandDarkGray)
                    },
                    isError = confirmPasswordError.isNotEmpty(),
                    supportingText = {
                        if (confirmPasswordError.isNotEmpty()) {
                            Text(text = confirmPasswordError, color = Color.Red)
                        }
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = BrandDarkGray,
                        unfocusedTextColor = BrandDarkGray,
                        focusedContainerColor = BrandLightGray,
                        unfocusedContainerColor = BrandLightGray,
                        focusedBorderColor = if (confirmPasswordError.isNotEmpty()) Color.Red else Color.Transparent,
                        unfocusedBorderColor = if (confirmPasswordError.isNotEmpty()) Color.Red else Color.Transparent
                    ),
                    singleLine = true
                )

                if (generalError.isNotEmpty()) {
                    Text(
                        text = generalError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Register Button
                Button(
                    onClick = {
                        var hasError = false
                        if (identifier.isBlank()) {
                            identifierError = "Username atau email tidak boleh kosong"
                            hasError = true
                        }
                        if (password.length < 8) {
                            passwordError = "Password minimal 8 karakter"
                            hasError = true
                        }
                        if (confirmPassword != password) {
                            confirmPasswordError = "Konfirmasi password tidak cocok"
                            hasError = true
                        }
                        
                        if (!hasError) {
                            val email: String
                            val username: String
                            if (identifier.contains("@")) {
                                email = identifier.trim()
                                username = identifier.substringBefore("@").trim()
                            } else {
                                username = identifier.trim()
                                email = "$username@spotrent.com"
                            }
                            val fullName = username.replaceFirstChar { it.uppercase() }
                            
                            val success = AuthRepository.register(username, email, fullName, password)
                            if (success) {
                                onRegisterSuccess()
                            } else {
                                generalError = "Username atau email sudah terdaftar"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
                ) {
                    Text("Daftar", color = BrandDarkGray, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Or", color = BrandDarkGray)

                Spacer(modifier = Modifier.height(16.dp))

                // Login Link
                Row {
                    Text(text = "Sudah Punya Akun? ", color = BrandDarkGray)
                    Text(
                        text = "Masuk",
                        color = BrandLinkBlue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onLoginClick() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_7)
@Composable
fun RegisterScreenPreview() {
    SpotRentTheme {
        RegisterScreen(onRegisterSuccess = {}, onLoginClick = {}, onBackClick = {})
    }
}
