package com.pab.spotrent.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.spotrent.ui.theme.BrandDarkGray
import com.pab.spotrent.ui.theme.BrandYellow
import com.pab.spotrent.data.repository.BookingRepository
import com.pab.spotrent.data.model.Booking
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BookingCalendarScreen(
    propertyId: Int,
    onBackClick: () -> Unit,
    onNextClick: (Long, Long) -> Unit
) {
    var calendar by remember { mutableStateOf(Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 1) }) }
    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }

    val allBookings by BookingRepository.bookings.collectAsState()
    val propertyBookings = remember(allBookings, propertyId) {
        allBookings.filter { it.propertyId == propertyId && it.status == "Berhasil" }
    }

    var showWarningDialog by remember { mutableStateOf(false) }
    var warningMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Pilih Tanggal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BrandDarkGray
            )
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }

        // Selection Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (startDate != null) {
                val end = endDate ?: startDate
                Text(
                    text = "${formatDate(startDate!!)} - ${formatDate(end!!)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            } else {
                Text(
                    text = "Silakan pilih rentang tanggal",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Month Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                val newCal = calendar.clone() as Calendar
                newCal.add(Calendar.MONTH, -1)
                calendar = newCal
            }) {
                Icon(Icons.Default.ChevronLeft, contentDescription = "Prev", tint = Color.Black)
            }

            Text(
                text = SimpleDateFormat("MMMM yyyy", Locale("id", "ID")).format(calendar.time),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            IconButton(onClick = {
                val newCal = calendar.clone() as Calendar
                newCal.add(Calendar.MONTH, 1)
                calendar = newCal
            }) {
                Icon(Icons.Default.ChevronRight, contentDescription = "Next", tint = Color.Black)
            }
        }

        // Day Labels
        val dayLabels = listOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            dayLabels.forEach { label ->
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        // Calendar Grid
        val todayMillis = remember {
            Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
        }
        val days = getDaysInMonth(calendar)
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            items(days) { day ->
                if (day == null) {
                    Spacer(modifier = Modifier.aspectRatio(1f))
                } else {
                    val isPastDate = day.timeInMillis < todayMillis
                    val isBooked = isDateBooked(day.timeInMillis, propertyBookings)
                    
                    val isSelectedStart = startDate == day.timeInMillis
                    val isSelectedEnd = endDate == day.timeInMillis
                    val isInRange = startDate != null && endDate != null && 
                                     day.timeInMillis > startDate!! && day.timeInMillis < endDate!!
                    
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isSelectedStart || isSelectedEnd -> BrandYellow
                                    isInRange -> BrandYellow.copy(alpha = 0.3f)
                                    isBooked -> Color(0xFFEF9A9A) // Soft Red background
                                    else -> Color.Transparent
                                }
                            )
                            .clickable(enabled = !isPastDate) {
                                if (isBooked) {
                                    warningMessage = "Tanggal ini sudah dibooking. Silakan pilih tanggal lain."
                                    showWarningDialog = true
                                } else {
                                    if (startDate == null || (startDate != null && endDate != null)) {
                                        startDate = day.timeInMillis
                                        endDate = null
                                    } else if (day.timeInMillis < startDate!!) {
                                        startDate = day.timeInMillis
                                    } else {
                                        // Check if there is any booked date in between
                                        var hasBookedDateInRange = false
                                        val start = minOf(startDate!!, day.timeInMillis)
                                        val end = maxOf(startDate!!, day.timeInMillis)
                                        val checkCal = Calendar.getInstance().apply { timeInMillis = start }
                                        while (checkCal.timeInMillis <= end) {
                                            if (isDateBooked(checkCal.timeInMillis, propertyBookings)) {
                                                hasBookedDateInRange = true
                                                break
                                            }
                                            checkCal.add(Calendar.DAY_OF_MONTH, 1)
                                        }

                                        if (hasBookedDateInRange) {
                                            warningMessage = "Rentang tanggal pilihan Anda melewati tanggal yang sudah dibooking. Silakan pilih rentang tanggal lain."
                                            showWarningDialog = true
                                        } else {
                                            endDate = day.timeInMillis
                                        }
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.get(Calendar.DAY_OF_MONTH).toString(),
                            fontSize = 14.sp,
                            color = when {
                                isPastDate -> Color.LightGray
                                isBooked -> Color(0xFFB71C1C) // Dark Red text
                                isSelectedStart || isSelectedEnd || isInRange -> Color.Black
                                else -> Color.Black
                            },
                            fontWeight = if (isSelectedStart || isSelectedEnd || isBooked) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // Bottom Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Button(
                onClick = {
                    val start = startDate
                    val end = endDate ?: startDate
                    if (start != null) {
                        onNextClick(start, end!!)
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
                enabled = startDate != null
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

    if (showWarningDialog) {
        AlertDialog(
            onDismissRequest = { showWarningDialog = false },
            title = { Text(text = "Peringatan", fontWeight = FontWeight.Bold, color = BrandDarkGray) },
            text = { Text(text = warningMessage, color = BrandDarkGray) },
            confirmButton = {
                Button(
                    onClick = { showWarningDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandYellow)
                ) {
                    Text("OK", color = BrandDarkGray, fontWeight = FontWeight.Bold)
                }
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color.White
        )
    }
}

private fun isDateBooked(timeInMillis: Long, bookings: List<Booking>): Boolean {
    val cal = Calendar.getInstance().apply {
        this.timeInMillis = timeInMillis
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val targetTime = cal.timeInMillis

    for (booking in bookings) {
        val startCal = Calendar.getInstance().apply {
            this.timeInMillis = booking.startDate
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endCal = Calendar.getInstance().apply {
            this.timeInMillis = booking.endDate
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val start = startCal.timeInMillis
        val end = endCal.timeInMillis

        if (targetTime in start..end) {
            return true
        }
    }
    return false
}

private fun getDaysInMonth(calendar: Calendar): List<Calendar?> {
    val days = mutableListOf<Calendar?>()
    val cal = calendar.clone() as Calendar
    cal.set(Calendar.DAY_OF_MONTH, 1)
    
    val firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1 // 0-based
    repeat(firstDayOfWeek) { days.add(null) }
    
    val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    for (i in 1..daysInMonth) {
        val day = cal.clone() as Calendar
        day.set(Calendar.DAY_OF_MONTH, i)
        // Reset time for comparison
        day.set(Calendar.HOUR_OF_DAY, 0)
        day.set(Calendar.MINUTE, 0)
        day.set(Calendar.SECOND, 0)
        day.set(Calendar.MILLISECOND, 0)
        days.add(day)
    }
    return days
}

private fun formatDate(millis: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("id", "ID"))
    val year = calendar.get(Calendar.YEAR)
    return "$day $month $year"
}
