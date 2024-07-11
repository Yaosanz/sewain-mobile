package com.sewain.mobileapp.ui.component

import android.content.res.Configuration
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sewain.mobileapp.ui.theme.DarkThemeButtonColor
import com.sewain.mobileapp.ui.theme.DarkThemeTextColor
import com.sewain.mobileapp.ui.theme.LightThemeButtonColor
import com.sewain.mobileapp.ui.theme.LightThemeTextColor
import com.sewain.mobileapp.ui.theme.SewainAppTheme

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )

    Button(
        onClick = onClick,
        colors = colors,
//        shape = RoundedCornerShape(8.dp),
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyCustomButton(){
    SewainAppTheme() {
        CustomButton(text = "Button", onClick = {})
    }
}

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewDarkMyCustomButton(){
    SewainAppTheme() {
        CustomButton(text = "Button", onClick = {})
    }
}