package com.example.praktam_2417051035

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.praktam_2417051035.ui.theme.PrakTAM2_2417051035Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.praktam_2417051035.data.model.Activity
import com.example.praktam_2417051035.data.api.RetrofitClient
import com.example.praktam_2417051035.data.repository.ActivityRepository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM2_2417051035Theme {
                val navController = rememberNavController()
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.bg),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    AppNavigation(navController)
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 50.dp, start = 20.dp, end = 20.dp)
    ) {
        Levelling(modifier = Modifier.align(Alignment.TopStart))
        Coin(modifier = Modifier.align(Alignment.TopEnd))
    }
}

@Composable
fun Levelling(modifier: Modifier = Modifier) {
    val levelling = painterResource(R.drawable.levelling)
    val poppins = FontFamily(Font(R.font.poppins_bold))
    Box(modifier = modifier.width(100.dp).height(100.dp)) {
        Image(
            painter = levelling,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "Level 1",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = poppins,
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 12.dp, bottom = 3.dp)
        )
    }
}

@Composable
fun Coin(modifier: Modifier = Modifier) {
    val levelling = painterResource(R.drawable.levelling)
    Box(modifier = modifier.width(120.dp).height(100.dp)) {
        Image(
            painter = levelling,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}


@Composable
fun DaftarKegiatanScreen(navController: NavController, onActivitiesLoaded: (List<Activity>) -> Unit = {}) {
    var activities by remember { mutableStateOf(emptyList<Activity>()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var retryTrigger by remember { mutableStateOf(0) }
    val repository = remember { ActivityRepository() }

    LaunchedEffect(retryTrigger) {
        isLoading = true
        isError = false
        try {
            val response = repository.getActivity()
            activities = response
            onActivitiesLoaded(response)
            isLoading = false
        } catch (e: Exception) {
            Log.e("API_ERROR", "Gagal load JSON: ${e.message}")
            isLoading = false
            isError = true
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFFE64A19))
        }
    } else if (isError) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Gagal Memuat Data",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFE64A19),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Pastikan koneksi internet Anda menyala",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { retryTrigger++ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE64A19)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.width(150.dp)
                ) {
                    Text("Coba Lagi", color = Color.White)
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().statusBarsPadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    text = "Rekomendasi Populer",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(activities) { activity ->
                        ActivityRowItem(activity = activity, navController = navController)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Daftar Menu Lengkap",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            items(activities) { activity ->
                ActivityItem(activity = activity, navController = navController)
            }
        }
    }
}

@Composable
fun ActivityRowItem(activity: Activity, navController: NavController) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable { navController.navigate("detail/${activity.nama}") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(activity.image_url) // Pastikan JSON berisi URL lengkap (http...)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.book), // Placeholder icon buku
                error = painterResource(R.drawable.gym)        // Gambar gym muncul jika URL salah
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = activity.nama, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(text = "poin ${activity.poin}", color = Color(0xFFE64A19), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ActivityItem(activity: Activity, navController: NavController) {
    var isFavorite by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("detail/${activity.nama}") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(activity.image_url)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.book),
                    error = painterResource(R.drawable.gym)
                )
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) Color.Red else Color.White
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = activity.nama, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(text = activity.deskripsi, style = MaterialTheme.typography.bodyMedium, color = Color.Gray, maxLines = 2)
                Text(text = "Jumlah Poin ${activity.poin}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
                Button(
                    onClick = { navController.navigate("detail/${activity.nama}") },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE64A19)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Pesan", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    var activities by remember { mutableStateOf(emptyList<Activity>()) }
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Column(modifier = Modifier.fillMaxSize()) {
                HomeScreen()
                DaftarKegiatanScreen(navController) { activities = it }
            }
        }
        composable("detail/{nama}") { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama")
            val activity = activities.find { it.nama == nama }
            if (activity != null) {
                DetailScreen(activity = activity, navController = navController, isFullScreen = true)
            }
        }
    }
}

@Composable
fun DetailScreen(activity: Activity, navController: NavController, isFullScreen: Boolean = false) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var isFavorite by remember { mutableStateOf(false) }
    var isLoadingButton by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground)
        ) {
            Column {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(activity.image_url)
                            .crossfade(true)
                            .build(),
                        contentDescription = activity.nama,
                        modifier = Modifier.fillMaxWidth().height(250.dp),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.book),
                        error = painterResource(R.drawable.gym)
                    )
                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isFavorite) Color.Red else Color.White
                        )
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = activity.nama, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = activity.deskripsi, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Jumlah Poin ${activity.poin}", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    if (isFullScreen) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    isLoadingButton = true
                                    delay(2000)
                                    snackbarHostState.showSnackbar("Berhasil dipesan!")
                                    isLoadingButton = false
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isLoadingButton,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE64A19))
                        ) {
                            if (isLoadingButton) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                            } else {
                                Text("Pesan Sekarang")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth(),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White)
                    ) {
                        Text("Kembali", color = Color.White)
                    }
                }
            }
        }
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}