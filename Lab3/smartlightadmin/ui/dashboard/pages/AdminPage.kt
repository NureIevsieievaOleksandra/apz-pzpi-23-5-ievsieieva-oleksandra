package ua.nure.smartlightadmin.ui.dashboard.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ua.nure.data.admin.BackupDto
import ua.nure.smartlightadmin.ui.dashboard.Dashboard
import ua.nure.smartlightadmin.ui.dashboard.compose.BackupItem
import ua.nure.smartlightadmin.ui.theme.AppTheme

@Composable
fun AdminPage(
    modifier: Modifier,
    backups: List<BackupDto>? = null,
    onAction: (Dashboard.Action) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = AppTheme.color.active, shape = CircleShape)
                    .clickable {
                        onAction(Dashboard.Action.OnCreateBackup)
                    }
                ,
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = AppTheme.color.active
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimension.normal)
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.small)
        ) {
            items(items = backups ?: emptyList(), key = { it.fileName }) { backup ->
                BackupItem(
                    modifier = Modifier,
                    backup = backup,
                    onAction = onAction,
                )
            }

        }

    }
}