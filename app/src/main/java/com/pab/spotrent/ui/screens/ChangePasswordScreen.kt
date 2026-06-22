package com.pab.spotrent.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.data.repository.AuthRepository
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    onBackClick: () -> Unit,
    onChangeSuccess: () -> Unit
) {
    val context = LocalContext.current

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    var currentPasswordError by remember { mutableStateOf("") }
    var newPasswordError by remember { mutableStateOf("") }
    var confirmNewPasswordError by remember { mutableStateOf("") }
    var generalError by remember { mutableStateOf("") }
    
    var showSuccessDialog by remember { mutableStateOf(false) }

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
                    text = "Ganti Kata Sandi",
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

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            Text(
                text = "Jangan Bagikan Kata Sandi Mu Kepada Siapapun Demi Keamanan Data Anda",
                fontSize = 13.sp,
                color = Color.Gray,
                lineHeight = 18.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Input Kata Sandi Saat Ini
            Text(
                text = "Kata Sandi Saat Ini",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = BrandDarkGray,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            OutlinedTextField(
                value = currentPassword,
                onValueChange = { 
                    currentPassword = it
                    currentPasswordError = ""
                    generalError = ""
                },
                placeholder = { Text("Masukkan Kata Sandi", color = Color.LightGray, fontSize = 14.sp) },
                visualTransformation = PasswordVisualTransformation(),
                isError = currentPasswordError.isNotEmpty(),
                supportingText = {
                    if (currentPasswordError.isNotEmpty()) {
                        Text(text = currentPasswordError, color = Color.Red)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BrandDarkGray,
                    unfocusedTextColor = BrandDarkGray,
                    focusedBorderColor = if (currentPasswordError.isNotEmpty()) Color.Red else Color.Gray,
                    unfocusedBorderColor = if (currentPasswordError.isNotEmpty()) Color.Red else Color.LightGray
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Kata Sandi Baru
            Text(
                text = "Kata Sandi Baru",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = BrandDarkGray,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            OutlinedTextField(
                value = newPassword,
                onValueChange = { 
                    newPassword = it
                    newPasswordError = ""
                    generalError = ""
                },
                placeholder = { Text("Masukkan Kata Sandi Baru", color = Color.LightGray, fontSize = 14.sp) },
                visualTransformation = PasswordVisualTransformation(),
                isError = newPasswordError.isNotEmpty(),
                supportingText = {
                    if (newPasswordError.isNotEmpty()) {
                        Text(text = newPasswordError, color = Color.Red)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BrandDarkGray,
                    unfocusedTextColor = BrandDarkGray,
                    focusedBorderColor = if (newPasswordError.isNotEmpty()) Color.Red else Color.Gray,
                    unfocusedBorderColor = if (newPasswordError.isNotEmpty()) Color.Red else Color.LightGray
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input Ulang Kata Sandi Baru
            Text(
                text = "Kata Sandi Baru",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = BrandDarkGray,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            OutlinedTextField(
                value = confirmNewPassword,
                onValueChange = { 
                    confirmNewPassword = it
                    confirmNewPasswordError = ""
                    generalError = ""
                },
                placeholder = { Text("Masukkan Ulang Kata Sandi Baru", color = Color.LightGray, fontSize = 14.sp) },
                visualTransformation = PasswordVisualTransformation(),
                isError = confirmNewPasswordError.isNotEmpty(),
                supportingText = {
                    if (confirmNewPasswordError.isNotEmpty()) {
                        Text(text = confirmNewPasswordError, color = Color.Red)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BrandDarkGray,
                    unfocusedTextColor = BrandDarkGray,
                    focusedBorderColor = if (confirmNewPasswordError.isNotEmpty()) Color.Red else Color.Gray,
                    unfocusedBorderColor = if (confirmNewPasswordError.isNotEmpty()) Color.Red else Color.LightGray
                ),
                singleLine = true
            )

            if (generalError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = generalError,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        // Lanjutkan Button di Bagian Bawah
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Button(
                onClick = {
                    var hasError = false

                    // 1. Validasi Input Kosong
                    if (currentPassword.isBlank()) {
                        currentPasswordError = "Kata sandi saat ini wajib diisi"
                        hasError = true
                    }
                    if (newPassword.isBlank()) {
                        newPasswordError = "Kata sandi baru wajib diisi"
                        hasError = true
                    }
                    if (confirmNewPassword.isBlank()) {
                        confirmNewPasswordError = "Masukkan ulang kata sandi baru wajib diisi"
                        hasError = true
                    }

                    if (hasError) return@Button

                    // 2. Verifikasi Password Saat Ini dengan Database
                    val realPassword = AuthRepository.getCurrentUserPassword()
                    if (currentPassword != realPassword) {
                        currentPasswordError = "Kata sandi saat ini salah"
                        return@Button
                    }

                    // 3. Validasi Panjang Password Baru
                    if (newPassword.length < 8) {
                        newPasswordError = "Password minimal 8 karakter"
                        return@Button
                    }

                    // 4. Validasi Konfirmasi Password Baru
                    if (confirmNewPassword != newPassword) {
                        confirmNewPasswordError = "Konfirmasi password tidak cocok"
                        return@Button
                    }

                    // Simpan ke SQLite
                    val success = AuthRepository.updatePassword(newPassword)
                    if (success) {
                        showSuccessDialog = true
                    } else {
                        generalError = "Gagal memperbarui kata sandi"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
            ) {
                Text(
                    text = "LANJUTKAN",
                    color = BrandDarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        onChangeSuccess()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
                ) {
                    Text("OK", color = BrandDarkGray, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Kata Sandi Diperbarui", fontWeight = FontWeight.Bold, color = BrandDarkGray)
                }
            },
            text = {
                Text(
                    text = "Kata sandi Anda telah berhasil diperbarui. Silakan gunakan kata sandi baru untuk login berikutnya.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = BrandDarkGray
                )
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color.White
        )
    }
}
