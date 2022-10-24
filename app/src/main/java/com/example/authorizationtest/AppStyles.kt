package com.example.authorizationtest

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.composetest.R

object AppStyles {
    val textStyle = TextStyle(
        fontSize = 15.sp,
        fontFamily = FontFamily(
            Font(
                resId = R.font.sf_ui_display,
                weight = FontWeight(400)
            )
        ),
        //steel-black rgba(51, 51, 51, 1)
        color = Color(51, 51, 51)
    )
}