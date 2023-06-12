package com.example.uniffidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uniffidemo.ui.theme.UniffiDemoTheme
import uniffi.Hello.rustGreeting

class MainActivity : ComponentActivity() {

    init {
        System.loadLibrary("hello")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniffiDemoTheme {
               MyApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MyApp() {
    var navController = rememberNavController()
    var currentSelect by remember {
        mutableStateOf(0)
    }

    val menuData = listOf(
        BottomItemData("首页", Icons.Filled.Home),
        BottomItemData("设置", Icons.Filled.Settings)
    )

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        NavigationBar() {
            menuData.forEachIndexed { index, bottomItemData ->
                NavigationBarItem(
                    selected = index == currentSelect,
                    onClick = {
                        currentSelect = index

                        if (currentSelect == 0) {
                            navController.navigate("MainPage") {
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate("SettingPage") {
                                launchSingleTop = true
                            }
                        }
                              },
                    icon = {
                        Icon(
                            imageVector = bottomItemData.icon,
                            contentDescription = "点击按钮"
                        )
                    },
                    label = {
                        Text(text = (bottomItemData.label))
                    },
                )

            }
        }
    }) { innerPadding ->
        println(innerPadding)
        NavHost(navController = navController, startDestination = "MainPage") {
            composable("MainPage") {
                MainPage() {
                    navController.navigate("SettingPage")
                }
            }
            composable("SettingPage") {
                SettingPage()
            }
        }

    }
}

@Composable
fun MainPage(toSettingPage: () -> Unit) {
    Column() {
        Greeting(name = "Rust")
        Button(onClick = { toSettingPage.invoke() }) {
            Text("跳转到设置界面")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingPage() {
    Column() {
        Text(text = "TODO")
    }
}

data class BottomItemData(val label: String, val icon: ImageVector)

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = rustGreeting(name),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UniffiDemoTheme {
        Greeting("Android")
    }
}