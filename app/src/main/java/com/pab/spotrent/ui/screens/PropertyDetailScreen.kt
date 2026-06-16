package com.pab.spotrent.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pab.spotrent.data.repository.PropertyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailScreen(propertyId: Int, onBackClick: () -> Unit) {
    val property = PropertyRepository.getPropertyById(propertyId) ?: return
    val scope = rememberCoroutineScope()
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateRange by remember { mutableStateOf("Pilih Tanggal") }
    var paymentStatus by remember { mutableStateOf("Idle") } // Idle, Loading, Success

    val dateRangePickerState = rememberDateRangePickerState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SpotRent", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Share, contentDescription = "Share") }
                    IconButton(onClick = {}) { Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite", tint = Color.Red) }
                }
            )
        }
    ) { paddingValues ->
        if (paymentStatus == "Loading") {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)).clickable(enabled = false) {}, contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFF0D050))
            }
        }

        if (paymentStatus == "Success") {
            AlertDialog(
                onDismissRequest = { paymentStatus = "Idle" },
                confirmButton = {
                    Button(onClick = { paymentStatus = "Idle" }) { Text("OK") }
                },
                title = { Text("Pembayaran Berhasil") },
                text = { Text("Pemesanan lokasi ${property.name} telah berhasil disimulasikan.") }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Image Gallery (Simplified)
            item {
                AsyncImage(
                    model = property.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Title and Description
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = property.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(text = property.description, fontSize = 14.sp, color = Color.Gray)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoChip(icon = Icons.Default.LocationOn, label = property.location)
                        InfoChip(icon = Icons.Default.Category, label = "Tipe: ${property.type}")
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF0D050), modifier = Modifier.size(20.dp))
                            Text(text = property.rating.toString(), fontWeight = FontWeight.Bold)
                            Text(text = " (${property.reviews} Reviews)", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Operator
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(modifier = Modifier.size(40.dp), shape = CircleShape, color = Color(0xFF4CAF50)) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("KAI", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("PT. Kereta Api Wisata", fontWeight = FontWeight.Bold)
                            Text("Pengelola Operasional", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(text = "Spesifikasi Properti", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Specifications Grid
            item {
                androidx.compose.foundation.layout.FlowRow(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = 2
                ) {
                    property.specifications.forEach { spec ->
                        SpecificationItem(spec)
                    }
                }
            }

            // Price and Calendar
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(text = "IDR ${formatPrice(property.price)}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Untuk 3 Hari (DD-MM-YY - DD-MM-YY)", fontSize = 12.sp, color = Color.Gray)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Calendar Simulation Button
                    Button(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.fillMaxWidth().height(150.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Black, modifier = Modifier.size(32.dp))
                            Text(text = selectedDateRange, color = Color.Black)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Payment Simulation Button
                    Button(
                        onClick = {
                            scope.launch {
                                paymentStatus = "Loading"
                                delay(2000)
                                paymentStatus = "Success"
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0D050))
                    ) {
                        Text("Bayar Sekarang", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        val start = dateRangePickerState.selectedStartDateMillis
                        val end = dateRangePickerState.selectedEndDateMillis
                        if (start != null && end != null) {
                            selectedDateRange = "${formatDate(start)} - ${formatDate(end)}"
                        }
                        showDatePicker = false
                    }) { Text("Pilih") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("Batal") }
                }
            ) {
                DateRangePicker(state = dateRangePickerState, modifier = Modifier.height(400.dp))
            }
        }
    }
}

@Composable
fun InfoChip(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.Red, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Black)
    }
}

@Composable
fun SpecificationItem(label: String) {
    Row(
        modifier = Modifier
            .width(160.dp)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

fun formatDate(millis: Long): String {
    val formatter = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
