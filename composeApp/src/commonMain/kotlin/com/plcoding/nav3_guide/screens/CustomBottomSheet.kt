package com.plcoding.nav3_guide.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(
  sheetState: SheetState = rememberModalBottomSheetState(),
  onDismissed: () -> Unit
) {
  val scope = rememberCoroutineScope()
  fun dismiss() = scope.launch { sheetState.hide() }
    .invokeOnCompletion {
      onDismissed()
    }

  ModalBottomSheet(
    sheetState = sheetState,
    onDismissRequest = { dismiss() }
  ) {
    Box(
      modifier = Modifier.fillMaxHeight(),
      contentAlignment = Alignment.TopCenter
    ) {
      Text("Bottom Sheet")
    }
  }
}