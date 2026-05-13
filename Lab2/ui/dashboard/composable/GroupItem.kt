package ua.nure.smartlight.ui.dashboard.composable

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.nure.smartlight.R
import ua.nure.smartlight.db.entity.Group
import ua.nure.smartlight.db.entity.LampEntity
import ua.nure.smartlight.ui.dashboard.Dashboard
import ua.nure.smartlight.ui.theme.AppTheme

private val TAG by lazy { "GroupItem" }

@Composable
fun GroupItem(
    modifier: Modifier = Modifier,
    group: Group,
    isAdmin: Boolean = false,
    onAction: (Dashboard.Action) -> Unit
) {
    var showLamps by remember { mutableStateOf(true) }
    Column(

    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = AppTheme.color.grey, shape = AppTheme.shape.cardShape)
                .clickable {
                    showLamps = !showLamps
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimension.small),
                text = group.group.groupId.toString(),
                style = AppTheme.typography.regular
            )
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = group.group.name ?: "",
                    style = AppTheme.typography.regular
                )
                Text(
                    text = group.group.description ?: "",
                    style = AppTheme.typography.small.copy(
                        color = AppTheme.color.grey
                    )
                )
            }
            if (isAdmin) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = AppTheme.dimension.small)
                        .clickable {
                            onAction(
                                Dashboard.Action.OnAddLampDialogDialog(
                                    group = group,
                                    lamp = null
                                )
                            )
                        },
                    painter = painterResource(R.drawable.lamp_active),
                    contentDescription = null,
                    tint = AppTheme.color.foreground
                )
            }

            Icon(
                modifier = Modifier
                    .padding(end = AppTheme.dimension.small)
                    .clickable {
                        onAction(Dashboard.Action.OnEditGroupDialog(group = group))
                    },
                painter = painterResource(R.drawable.edit),
                contentDescription = null,
                tint = AppTheme.color.foreground
            )

            if (isAdmin) {
                Icon(
                    modifier = Modifier
                        .padding(end = AppTheme.dimension.small)
                        .clickable {
                            onAction(Dashboard.Action.OnDeleteGroupDialog(group = group))
                        },
                    painter = painterResource(R.drawable.close),
                    contentDescription = null,
                    tint = AppTheme.color.foreground
                )

            }

        }
        AnimatedVisibility(
            visible = showLamps && group.lamps.isNotEmpty()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                group.lamps.forEach { lamp ->

                    var showColorPicker by remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 30.dp,
                                    top = AppTheme.dimension.extraSmall,
                                    bottom = AppTheme.dimension.extraSmall
                                )
                                .border(
                                    width = 1.dp,
                                    color = AppTheme.color.grey,
                                    shape = AppTheme.shape.cardShape
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(start = AppTheme.dimension.normal)
                                    .size(24.dp)
                                    .clickable {
                                        onAction(
                                            Dashboard.Action.OnLampAddOrChange(
                                                lamp = lamp.copy(
                                                    active = !lamp.active
                                                )
                                            )
                                        )
                                    },
                                painter = painterResource(R.drawable.power),
                                contentDescription = null,
                                tint = if (lamp.active) AppTheme.color.active else Color.Gray

                            )
                            Column(
                                modifier = Modifier
                                    .padding(start = AppTheme.dimension.normal)
                                    .weight(1F)
                            ) {
                                Text(
                                    text = lamp.name ?: "",
                                    style = AppTheme.typography.regular
                                )
                                Text(
                                    text = lamp.description ?: "",
                                    style = AppTheme.typography.regular
                                )
                                Text(
                                    modifier = Modifier.clickable {
                                        showColorPicker = !showColorPicker
                                    },
                                    text = "R: ${lamp.r}, G: ${lamp.g}, B: ${lamp.b}",
                                    style = AppTheme.typography.regular
                                )
                                Text(
                                    modifier = Modifier.clickable {
                                        showColorPicker = !showColorPicker
                                    },
                                    text = "Brightness: ${lamp.brightness}",
                                    style = AppTheme.typography.regular
                                )
                            }

                            Icon(
                                modifier = Modifier
                                    .padding(end = AppTheme.dimension.small)
                                    .clickable {
                                        onAction(Dashboard.Action.OnEditLampDialog(group = group, lamp = lamp))
                                    },
                                painter = painterResource(R.drawable.edit),
                                contentDescription = null,
                                tint = AppTheme.color.foreground
                            )

                            if (isAdmin) {
                                Icon(
                                    modifier = Modifier
                                        .padding(end = AppTheme.dimension.small)
                                        .clickable {
                                            onAction(Dashboard.Action.OnDeleteLampDialog(lamp = lamp))
                                        },
                                    painter = painterResource(R.drawable.close),
                                    contentDescription = null,
                                    tint = AppTheme.color.foreground
                                )
                            }
                        }

                        if (showColorPicker) {
                            SmartLineColorPicker(
                                modifier = Modifier,
                                color = Color(
                                    red = (lamp.r ?: 0) / 255f,
                                    green = (lamp.g ?: 0) / 255f,
                                    blue = (lamp.b ?: 0) / 255f,
                                    alpha = 1f
                                ),
                                brightness = lamp.brightness ?: 0,
                                onColorChange = { color ->
                                    onAction(
                                        Dashboard.Action.OnLampAddOrChange(
                                            lamp = lamp.copy(
                                                r = (255 * color.red).toInt(),
                                                g = (255 * color.green).toInt(),
                                                b = (255 * color.blue).toInt()
                                            )
                                        )
                                    )
                                },
                                onBrightnessChange = {
                                    onAction(
                                        Dashboard.Action.OnLampAddOrChange(
                                            lamp = lamp.copy(
                                                brightness = it
                                            )
                                        )
                                    )
                                }
                            )
                        }

                    }
                }

            }

        }

    }


}

@Preview
@Composable
private fun GroupItemPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = modifier.background(color = AppTheme.color.background)
        ) {
            GroupItem(
                group = Group.groupPreview.first().copy(
                    lamps = listOf(
                        Group.groupPreview.first().lamps.first().copy(
                            name = "New name"
                        ),
                        Group.groupPreview.first().lamps.first(),
                        Group.groupPreview.first().lamps.first(),
                    )
                ),
                isAdmin = true
            ) {}

        }
    }

}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GroupItemDarkPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = modifier.background(color = AppTheme.color.background)
        ) {
            GroupItem(
                group = Group.groupPreview.first().copy(
                    lamps = listOf(
                        Group.groupPreview.first().lamps.first().copy(
                            name = "New name"
                        ),
                        Group.groupPreview.first().lamps.first(),
                        Group.groupPreview.first().lamps.first(),
                    )
                ),
                isAdmin = true,
            ) {}

        }
    }

}