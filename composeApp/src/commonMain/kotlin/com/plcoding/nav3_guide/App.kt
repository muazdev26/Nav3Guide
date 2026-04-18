package com.plcoding.nav3_guide

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.nav3_guide.navigation.NavigationRoot
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold { innerPadding ->
            NavigationRoot(
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }
}