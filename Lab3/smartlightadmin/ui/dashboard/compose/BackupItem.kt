package ua.nure.smartlightadmin.ui.dashboard.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import ua.nure.data.admin.BackupDto
import ua.nure.smartlightadmin.extension.toLocalDateTime
import ua.nure.smartlightadmin.extension.toPrettyBytes
import ua.nure.smartlightadmin.ui.dashboard.Dashboard
import ua.nure.smartlightadmin.ui.theme.AppTheme

@OptIn(FormatStringsInDatetimeFormats::class)
@Composable
fun BackupItem(
    modifier: Modifier,
    backup: BackupDto,
    onAction: (Dashboard.Action) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppTheme.shape.cardShape)
            .border(width = 1.dp, color = AppTheme.color.grey, shape = AppTheme.shape.cardShape)
            .padding(vertical = AppTheme.dimension.normal, horizontal = AppTheme.dimension.normal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1F),
            text = backup.fileName,
            style = AppTheme.typography.regular
        )
        Text(
            modifier = Modifier
                .padding(horizontal = AppTheme.dimension.normal),
            text = backup.size.toPrettyBytes(),
            style = AppTheme.typography.regular
        )
        Text(
            text = LocalDateTime.Format {
                byUnicodePattern("yyyy.MM.dd HH:mm:ss")
            }.format(backup.modified.toLocalDateTime()),
            style = AppTheme.typography.regular
        )
        Icon(
            modifier = Modifier
                .padding(start = AppTheme.dimension.large)
                .clickable {
                    onAction(Dashboard.Action.OnRestoreBackupConfirm(file = backup.fileName))
                },
            imageVector = Icons.Default.SettingsBackupRestore,
            contentDescription = null,
            tint = AppTheme.color.foreground
        )
        Icon(
            modifier = Modifier
                .padding(horizontal = AppTheme.dimension.small)
                .clickable {
                    onAction(Dashboard.Action.OnDeleteBackup(file = backup.fileName))
                },
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = AppTheme.color.foreground
        )
    }
}

@Preview
@Composable
private fun BackupItemPreview() {
    AppTheme {
        Box(modifier = Modifier.background(color = AppTheme.color.background)) {
            BackupItem(
                modifier = Modifier,
                backup = BackupDto.backupPreview,
                onAction = {}
            )
        }
    }
}

@Preview
@Composable
private fun BackupItemDarkPreview() {
    AppTheme(darkTheme = true) {
        Box(modifier = Modifier.background(color = AppTheme.color.background)) {
            BackupItem(
                modifier = Modifier,
                backup = BackupDto.backupPreview,
                onAction = {}
            )
        }
    }
}