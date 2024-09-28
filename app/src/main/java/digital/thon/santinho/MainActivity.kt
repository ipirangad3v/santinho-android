package digital.thon.santinho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import digital.thon.santinho.presentation.SantinhoView
import digital.thon.santinho.ui.theme.SantinhoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SantinhoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SantinhoView(innerPadding)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SantinhoPreview() {
    SantinhoTheme {
        SantinhoView(innerPadding = PaddingValues(16.dp))

    }
}