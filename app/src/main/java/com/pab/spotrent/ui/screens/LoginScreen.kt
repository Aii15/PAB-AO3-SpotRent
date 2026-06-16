package com.pab.spotrent.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pab.spotrent.R

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Grid of Images (Simplified with few images for now)
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(4.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(4.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(4.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
            }
            Row(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(4.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(4.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(4.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
            }
            Row(modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(4.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(4.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(4.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
            }
        }

        // Semi-transparent overlay
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)))

        // Login Card
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo and Name
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Placeholder for Logo
                    Surface(
                        modifier = Modifier.size(32.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFE0E0E0)
                    ) {}
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "SpotRent",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Tab Switcher (Masuk / Daftar)
                Surface(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFFF5F5F5)
                ) {
                    Row {
                        Button(
                            onClick = { isLoginMode = true },
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isLoginMode) Color.White else Color.Transparent,
                                contentColor = if (isLoginMode) Color.Black else Color.Gray
                            ),
                            elevation = if (isLoginMode) ButtonDefaults.buttonElevation(defaultElevation = 2.dp) else null
                        ) {
                            Text("Masuk")
                        }
                        Button(
                            onClick = { isLoginMode = false },
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (!isLoginMode) Color.White else Color.Transparent,
                                contentColor = if (!isLoginMode) Color.Black else Color.Gray
                            ),
                            elevation = if (!isLoginMode) ButtonDefaults.buttonElevation(defaultElevation = 2.dp) else null
                        ) {
                            Text("Daftar")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Input Fields
                OutlinedTextField(
                    value = identifier,
                    onValueChange = { identifier = it },
                    label = { Text("E-mail atau username", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color.Gray) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Login Button
                Button(
                    onClick = onLoginSuccess,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0D050))
                ) {
                    Text("Masuk", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
