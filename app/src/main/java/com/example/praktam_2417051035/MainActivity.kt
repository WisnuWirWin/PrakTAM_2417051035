package com.example.praktam_2417051035

import android.os.Bundle
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.compose.foundation.clickable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import com.example.praktam_2417051035.model.ActivitySource
import com.example.praktam_2417051035.ui.theme.PrakTAM2_2417051035Theme
import model.Activity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM2_2417051035Theme {
                val navController = rememberNavController()
                Box(modifier = Modifier.fillMaxSize()){
                    Image(
                        painter = painterResource(id = R.drawable.bg),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
//                HomeScreen()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun HomeScreen(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(top = 50.dp, start = 20.dp, end = 20.dp)) {
        Levelling(modifier =Modifier
            .align(Alignment.TopStart))
        Coin(modifier = Modifier
            .align(Alignment.TopEnd))
    }
}

@Composable
fun Levelling(modifier: Modifier = Modifier){
    val levelling = painterResource(R.drawable.levelling)
    val Poppins = FontFamily(
        Font(R.font.poppins_bold)
    )
    Box(modifier = Modifier
        .width(100.dp)
        .height(100.dp)
    ) {
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
            fontFamily = Poppins,
            fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp, bottom = 3.dp)
        )
    }
}

@Composable
fun Coin(modifier: Modifier = Modifier){
    val levelling = painterResource(R.drawable.levelling)
    Box(modifier = Modifier
        .width(400.dp)
        .height(100.dp)
        .padding(start = 260.dp)
    ) {
        Image(
            painter = levelling,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun DaftarKegiatanScreen(navController: NavController){
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .statusBarsPadding()
//        .verticalScroll(rememberScrollState())
//        .padding( 24.dp)
//    ) {
//        ActivitySource.dummyData.forEach{ activity ->
//            DetailScreen(activity = activity)
//            Spacer(modifier = Modifier.height(24.dp))
//        }
//    }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = "Rekomendasi Populer",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                contentPadding = PaddingValues(horizontal = 2.dp)
            ) {
                items(ActivitySource.dummyData){ activity ->
                    Box(modifier = Modifier.width(50.dp))
                        ActivityItem(activity = activity, navController = navController)
                }
            }

            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "Daftar Kegiatan",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(ActivitySource.dummyData){ activity ->
            ActivityItem(activity = activity, navController)
        }
    }
}

@Composable
fun ActivityRowItem(activity: Activity, navController: NavController) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Image(
                painter = painterResource(activity.imageRes),
                contentDescription = activity.nama,
                modifier = Modifier
                    .fillMaxSize()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = activity.nama,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Deskripsi : ${activity.deskripsi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ActivityItem(activity: Activity, navController: NavController) {
    Card(
        modifier = Modifier.width(290.dp)
            .padding(horizontal = 4.dp)
            .clickable {
                navController.navigate("detail/${activity.nama}")
            },
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(painter = painterResource(id = activity.imageRes),
                contentDescription = activity.nama,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.nama,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Deskripsi : ${activity.deskripsi}",
                    maxLines = 2,
                )
            }

        }
    }
}

@Composable
fun AppNavigation(navController: androidx.navigation.NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            DaftarKegiatanScreen(navController)
        }

        composable("detail/{nama}") { backStackEntry ->
            val nama = backStackEntry.arguments?.getString("nama")
            val activity = ActivitySource.dummyData.find { it.nama == nama }
            if (activity != null) {
                DetailScreen(activity = activity, navController = navController, isFullScreen = true)
            }
        }
    }
}

@Composable
fun DetailScreen(activity: Activity, navController: NavController, isFullScreen : Boolean = false){
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var isFavorite by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box {
                    Image(
                        painter = painterResource(id = activity.imageRes),
                        contentDescription = activity.nama,
                        modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite Icon",
                            tint = if (isFavorite) Color.Red else Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = activity.nama,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = activity.deskripsi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = activity.poin.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (isFullScreen) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                isLoading = true
                                delay(2000)
                                snackbarHostState.showSnackbar("Aktivitas Berhasil dipilih!")
                                isLoading = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tunggu Sebentar...")
                        } else {
                            Text("Pilih")
                        }
                    }
                }

                Button(
                    onClick = {
                        if (isFullScreen) {
                            navController.popBackStack()
                        } else {
                            navController.navigate("detail/${activity.nama}")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (isFullScreen) "Kembali" else "Lihat Detail",
                        color = Color.White
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

    @Preview(showBackground = true, showSystemUi = true,)
    @Composable
    fun GreetingPreview() {
        PrakTAM2_2417051035Theme {
            PrakTAM2_2417051035Theme {
                Box(modifier = Modifier.fillMaxSize()){
                    Image(
                        painter = painterResource(id = R.drawable.bg),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                }

//                  HomeScreen()
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }