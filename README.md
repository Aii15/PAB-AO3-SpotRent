# Project PAB (Mobile Dev) "SpotRent"

## Anggota Tim (Kelompok)

| Nama                         | NIM      |  Akun GitHub                                 |
| :--------------------------- | :------- | :------------------------------------- |
| **YUSRAN RIZQI LAKSONO**     | L0124125 | [@YYYusrn](https://github.com/YYYusrn) |
| **JAUHAR MUFID TAMIR**       | L0124131 | [@jauhar7](https://github.com/jauhar7) |
| **MUHAMMAD AKBAR KURNIAWAN** | L0124136 | [@Frezga](https://github.com/Frezga)   |
| **MUHAMMAD AYMAN**           | L0124137 | [@Aii15](https://github.com/Aii15)     |

---

## Deskripsi Project

**SpotRent** merupakan platform penyewaan lokasi atau properti yang dikhususkan untuk kebutuhan syuting komersial. Platform ini dirancang untuk menjadi penghubung antara pemilik properti (mitra) dan pihak kreatif seperti produser atau _location manager_ (user) yang membutuhkan lokasi untuk kebutuhan produksi film, video, maupun komersial.

---

## Fitur Utama

- **Autentikasi Pengguna**: Registrasi akun baru, login, dan ubah kata sandi yang terintegrasi dengan penyimpanan database lokal.
- **Eksplorasi Lokasi (Home)**: Pencarian dan filter lokasi syuting berdasarkan kategori atau keyword.
- **Detail Properti Lengkap**: Informasi menyeluruh mengenai properti, mulai dari deskripsi, harga, fasilitas pendukung produksi, ulasan (reviews), hingga rating bintang.
- **Fitur Wishlist**: Pengguna dapat menandai dan menyimpan lokasi syuting favorit.
- **Fitur Pemesanan (Booking Calendar)**: Kalender interaktif untuk menentukan jadwal/tanggal penyewaan lokasi syuting.
- **Fitur Pembayaran**: Pilihan berbagai metode pembayaran beserta halaman konfirmasi detail transaksi.
- **Riwayat Pemesanan (History)**: Memantau riwayat penyewaan properti, baik yang sedang aktif maupun yang sudah selesai.
- **Manajemen Profil**: Halaman edit profil untuk memperbarui data diri dan melihat detail akun.

---

## Tech Stack yang Digunakan

- **Bahasa**: Kotlin
- **UI Framework**: Jetpack Compose
- **Desain UI/UX**: Material 3
- **Navigasi**: Compose Navigation
- **Image Loader**: Coil (Coroutine Image Loader)
- **Database Lokal**: SQLite (`UserDatabaseHelper`) untuk menyimpan data User, Booking, Review, dan Wishlist secara lokal.

---

## Requirements

Sebelum menjalankan aplikasi, pastikan telah menginstal dan memenuhi spesifikasi berikut:

- **Android Studio** versi terbaru (Ladybug atau versi stabil lainnya).
- **JDK 17** atau versi di atasnya (biasanya sudah dibundel oleh Android Studio).
- **Android SDK** dengan API Level 30 (Android 11) atau yang lebih baru.
- **Koneksi Internet** untuk proses _syncing gradle_ pertama kali serta _loading_ gambar online menggunakan Coil.
- **Perangkat Android** fisik (aktifkan USB Debugging) atau **Emulator (AVD)** dengan API Level 30+.

---

## Cara Menjalankan Aplikasi

1. **Clone Repository**:
   ```bash
   git clone https://github.com/Aii15/PAB-AO3-SpotRent.git
   ```
   bash
2. **Buka di Android Studio**: Buka aplikasi Android Studio, pilih **Open**, lalu arahkan ke folder hasil clone.
3. **Sync Project**: Tunggu proses sinkronisasi Gradle selesai secara otomatis.
4. **Jalankan Aplikasi**: Hubungkan perangkat Android fisik (aktifkan USB Debugging) atau gunakan Emulator (direkomendasikan API Level 30+), lalu klik tombol **Run** (ikon Play hijau).
