package com.example.countdowntimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Timer10Select
import androidx.compose.material.icons.filled.Timer3Select
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.countdowntimer.ui.theme.CountDownTimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountDownTimerTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android", modifier = Modifier.padding(innerPadding)
//                    )
//                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExampleScaffold()
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!", modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CountDownTimerTheme {
//        Greeting("Android")
//    }
//}

@Composable
fun ExampleScaffold(viewModel: ExampleViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val uiState = viewModel.uiState
    val iconOnClick: (Int) -> Unit = { time: Int ->
        viewModel.addTime(time)
    }
    val toggleTimer = {
        if (uiState.isRunning) {
            viewModel.stopTimer()
        } else {
            viewModel.startTimer(viewModel.uiState.time)
        }
    }
    Scaffold(topBar = { TopBar(iconOnClick = iconOnClick) },
        bottomBar = { BottomBar(onClick = toggleTimer, iconOnClick = iconOnClick) },
        floatingActionButton = {
            FloatingActionButton(onClick = toggleTimer, content = {
                Icon(
                    imageVector = Icons.Filled.Timer,
                    contentDescription = "Timer"
                )
            })
        },
        content = { innerPadding ->
            Box(
                // このmodifierをすることでTopBarに重なることを防いでいる
                modifier = Modifier
                    .padding(innerPadding)
                    .background(color = MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Arc(
                    color = MaterialTheme.colorScheme.primary,
                    timeLeft = uiState.timeLeft.toFloat() / uiState.time.toFloat()
                )
                val minute = uiState.timeLeft / 1000L / 60L
                val second = uiState.timeLeft / 1000L % 60L
                Text(
                    text = "%1$02d:%2$02d".format(minute, second),
                    fontSize = 100.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        })
}

@Preview(widthDp = 300, heightDp = 500, showBackground = true)
@Composable
private fun ExamoleScaffoldPreview() {
    CountDownTimerTheme {
        ExampleScaffold()
    }

}

/* マテリアルデザインが実験的になっているため以下アノテーションOptInを追加する */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    iconOnClick: (Int) -> Unit
) {
    TopAppBar(title = {
        Text(
            "Simple TopAppBar", maxLines = 1, overflow = TextOverflow.Ellipsis
        ) //文字列が長い場合折り返さない
    }, navigationIcon = {
        IconButton(onClick = { /* TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.Timer, contentDescription = "Timer"
            )
        }
    }, actions = {
        IconButton(onClick = { iconOnClick(3) }) {
            Icon(
                imageVector = Icons.Filled.Timer3Select,
                contentDescription = "Timer3Select"
            )
        }
        IconButton(onClick = { iconOnClick(10) }) {
            Icon(
                imageVector = Icons.Filled.Timer10Select,
                contentDescription = "Timer10Select"
            )
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary
    )
    )
}

@Preview
@Composable
private fun TopBarPreview() {
    CountDownTimerTheme {
        TopBar(iconOnClick = {})
    }
}

@Composable
fun BottomBar(
    onClick: () -> Unit,
    iconOnClick: (Int) -> Unit
) {
    BottomAppBar(actions = {
        IconButton(onClick = { iconOnClick(3) }) {
            Icon(
                imageVector = Icons.Filled.Timer3Select,
                contentDescription = "Timer3Select"
            )
        }
        IconButton(onClick = { iconOnClick(10) }) {
            Icon(
                imageVector = Icons.Filled.Timer10Select,
                contentDescription = "Timer10Select"
            )
        }
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Timer,
                        contentDescription = "Timer"
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun BottomBarPreview() {
    CountDownTimerTheme {
        BottomBar(onClick = {}, iconOnClick = {})
    }
}