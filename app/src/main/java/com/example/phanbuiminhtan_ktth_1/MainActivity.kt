package com.example.phanbuiminhtan_ktth_1

import android.os.Bundle
import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phanbuiminhtan_ktth_1.ui.theme.PhanBuiMinhTan_KTTH_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhanBuiMinhTan_KTTH_1Theme {
                CurrencyConverterApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterApp() {
    var vnDong by remember { mutableStateOf("") }
    var foreignCurrency by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("USD") }
    var conversionResult by remember { mutableStateOf("") }
    var showExitDialog by remember { mutableStateOf(false) }
    BackHandler(enabled = showExitDialog) {
        showExitDialog = false
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Chuyển đổi ngoại tệ",
                    color = Color.Red,
                    fontSize = 24.sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF92FAF2)
            ),
            modifier = Modifier.fillMaxWidth(),

        )

        Text(
            text = "Chọn ngoại tệ:",
            color = Color.Red,
            fontStyle = FontStyle.Italic,
            fontSize = 18 .sp,
            modifier = Modifier
                .align(Alignment.Start)

        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedCurrency == "USD", onClick = { selectedCurrency = "USD" })
                Text(text = "USD")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedCurrency == "EUR", onClick = { selectedCurrency = "EUR" })
                Text(text = "EUR")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selectedCurrency == "JPY", onClick = { selectedCurrency = "JPY" })
                Text(text = "JPY")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 5 .dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = "VN Đồng: ", fontSize = 22 .sp, color = Color.Green, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            TextField(
                value = vnDong,
                onValueChange = { vnDong = it },
                modifier = Modifier
                    .weight(2f).padding(end = 6.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = Color.Blue,
                ),
                singleLine = true,

            )
        }


        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 6 .dp, start = 5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = "Ngoại tệ: ", fontSize = 22 .sp, color = Color.Green, fontWeight = FontWeight.Bold ,modifier = Modifier.weight(1f))
            TextField(
                value = foreignCurrency,
                onValueChange = { foreignCurrency = it },
                modifier = Modifier
                    .weight(2f).padding(end = 6.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = Color.Blue,
                ),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val vndValue = vnDong.toDoubleOrNull()
                    if (vndValue != null) {

                        val conversionRate = when (selectedCurrency) {
                            "USD" -> 1 / 22280.0
                            "EUR" -> 1 / 24280.0
                            "JPY" -> 1 / 204.0
                            else -> 0.0
                        }
                        val result = vndValue * conversionRate
                        foreignCurrency = String.format("%.1f", result)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, start = 4.dp),
                shape = RectangleShape
            ) {
                Text("VNĐ TO NGOẠI TỆ")
            }
            Button(
                onClick = {
                    val foreignValue = foreignCurrency.toDoubleOrNull()
                    if (foreignValue != null) {

                        val conversionRate = when (selectedCurrency) {
                            "USD" -> 22280.0
                            "EUR" -> 24280.0
                            "JPY" -> 204.0
                            else -> 0.0
                        }
                        val result = foreignValue * conversionRate
                        vnDong = String.format("%.1f", result)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, start = 4.dp, end = 4 .dp),
                shape = RectangleShape
            ) {
                Text("NGOẠI TỆ TO VNĐ")
            }
        }

        Button(
            onClick = {
                vnDong = ""
                foreignCurrency = ""
            },
            modifier = Modifier.padding(5.dp).fillMaxWidth(),
            shape = RectangleShape
        ) {
            Text("CLEAR")
        }


        if (conversionResult.isNotEmpty()) {
            Text(text = "Kết quả: $conversionResult", modifier = Modifier.padding(top = 16.dp))
        }
    }
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Xác nhận thoát") },
            text = { Text("Bạn có chắc muốn thoát ứng dụng?") },
            confirmButton = {
                Button(onClick = {
                    showExitDialog = false
                }) {
                    Text("Có")
                }
            },
            dismissButton = {
                Button(onClick = { showExitDialog = false }) {
                    Text("Không")
                }
            }
        )
    }
    BackHandler {
        showExitDialog = true
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhanBuiMinhTan_KTTH_1Theme {
        CurrencyConverterApp()
    }
}