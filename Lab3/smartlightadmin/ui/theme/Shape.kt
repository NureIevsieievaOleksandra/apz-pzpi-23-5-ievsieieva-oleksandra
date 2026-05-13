package ua.nure.smartlightadmin.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class AppShape(
    val inputShape: Shape = RoundedCornerShape(size = 15.dp),
    val buttonShape: Shape = RoundedCornerShape(size = 10.dp),
    val accentShape: Shape = RoundedCornerShape(size = 20.dp),
    val cardShape: Shape = RoundedCornerShape(8.dp)
)