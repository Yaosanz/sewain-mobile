package com.sewain.mobileapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateRangePicker(state : DateRangePickerState, isShow : MutableState<Boolean>) {
// Decoupled snackbar host state from scaffold state for demo purposes.
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier.zIndex(1f))


    if(isShow.value){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        // Add a row with "Save" and dismiss actions.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { isShow.value = false}) {
                Icon(Icons.Filled.Close, contentDescription = "Localized description")
            }
            TextButton(
                onClick = {
                    snackScope.launch {
                        snackState.showSnackbar(
                            "Saved range (timestamps): " +
                                    "${state.selectedStartDateMillis!!..state.selectedEndDateMillis!!}"
                        )
                    }
                    isShow.value = false
                },
                enabled = state.selectedEndDateMillis != null
            ) {
                Text(text = "Save")
            }
        }
        DateRangePicker(state = state, modifier = Modifier.height(600.dp))
     }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewCustomDateRangePicker() {
    val state = rememberDateRangePickerState()
    LaunchedEffect(state.selectedStartDateMillis) {
        state.selectedStartDateMillis?.let { startDate ->
            val calendar = Calendar.getInstance().apply {
                timeInMillis = startDate
                add(Calendar.DAY_OF_MONTH, 3) // Add 3 days to the start date
            }
            state.setSelection(startDate, calendar.timeInMillis)
        }
    }
    CustomDateRangePicker(state, remember { mutableStateOf(true) })
}