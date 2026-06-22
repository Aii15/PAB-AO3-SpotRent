package com.pab.spotrent.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.R
import com.pab.spotrent.data.repository.AuthRepository
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailScreen(
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val context = LocalContext.current
    val currentUser by AuthRepository.currentUser.collectAsState()

    var nameState by remember { mutableStateOf(currentUser?.fullName ?: "") }
    var phoneState by remember { mutableStateOf(currentUser?.phone ?: "") }
    var emailState by remember { mutableStateOf(currentUser?.email ?: "") }

    var isEditingName by remember { mutableStateOf(false) }
    var isEditingPhone by remember { mutableStateOf(false) }
    var isEditingEmail by remember { mutableStateOf(false) }

    var showPassword by remember { mutableStateOf(false) }
    val passwordState = remember { AuthRepository.getCurrentUserPassword() }
    
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
                    text = "Detail Akun",
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
                text = "Informasi Pribadi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = BrandDarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Box Nama Lengkap
            DetailItemBox(
                label = "Nama Lengkap",
                isEditing = isEditingName,
                onEditClick = { isEditingName = !isEditingName },
                onValueChange = { nameState = it },
                value = nameState,
                placeholder = "Masukkan Nama Lengkap"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Box No Telepon
            DetailItemBox(
                label = "No Telepon",
                isEditing = isEditingPhone,
                onEditClick = { isEditingPhone = !isEditingPhone },
                onValueChange = { phoneState = it },
                value = phoneState,
                placeholder = "Masukkan No Telepon",
                keyboardType = KeyboardType.Phone
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Box Email
            DetailItemBox(
                label = "Email",
                isEditing = isEditingEmail,
                onEditClick = { isEditingEmail = !isEditingEmail },
                onValueChange = { emailState = it },
                value = emailState,
                placeholder = "Masukkan Email",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Box Password (Hanya Lihat, tidak bisa diedit)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Password",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (showPassword) passwordState else "•".repeat(passwordState.length.coerceAtLeast(8)),
                            fontSize = 14.sp,
                            color = BrandDarkGray,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Icon(
                        painter = painterResource(id = if (showPassword) R.drawable.ic_hide else R.drawable.ic_show),
                        contentDescription = if (showPassword) "Hide password" else "Show password",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { showPassword = !showPassword },
                        tint = Color.Gray
                    )
                }
            }
        }

        // Simpan Button di Bagian Bawah
        val hasChanges = nameState != (currentUser?.fullName ?: "") ||
                phoneState != (currentUser?.phone ?: "") ||
                emailState != (currentUser?.email ?: "")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Button(
                onClick = {
                    if (nameState.isBlank() || emailState.isBlank()) {
                        Toast.makeText(context, "Nama dan Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailState).matches()) {
                        Toast.makeText(context, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val success = AuthRepository.updateProfile(nameState, phoneState, emailState)
                    if (success) {
                        isEditingName = false
                        isEditingPhone = false
                        isEditingEmail = false
                        showSuccessDialog = true
                    } else {
                        Toast.makeText(context, "Gagal menyimpan profil", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandYellow,
                    disabledContainerColor = Color(0xFFE0E0E0),
                    disabledContentColor = Color.Gray
                ),
                enabled = hasChanges
            ) {
                Text(
                    text = "SIMPAN PERUBAHAN",
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
                        onSaveSuccess()
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
                    Text(text = "Profil Diperbarui", fontWeight = FontWeight.Bold, color = BrandDarkGray)
                }
            },
            text = {
                Text(
                    text = "Perubahan informasi profil Anda telah berhasil disimpan.",
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

@Composable
private fun DetailItemBox(
    label: String,
    value: String,
    isEditing: Boolean,
    onEditClick: () -> Unit,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (isEditing) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = BrandDarkGray,
                            fontWeight = FontWeight.Medium
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        decorationBox = { innerTextField ->
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                } else {
                    Text(
                        text = value.ifEmpty { "-" },
                        fontSize = 14.sp,
                        color = BrandDarkGray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Tombol Ubah / Selesai
            Surface(
                onClick = onEditClick,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BrandDarkGray),
                color = if (isEditing) BrandDarkGray else Color.White,
                modifier = Modifier.height(32.dp)
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isEditing) "Selesai" else "Ubah",
                        fontSize = 12.sp,
                        color = if (isEditing) Color.White else BrandDarkGray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
