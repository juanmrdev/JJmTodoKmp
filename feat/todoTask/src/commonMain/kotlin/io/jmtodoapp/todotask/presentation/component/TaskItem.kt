package io.jmtodoapp.todotask.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.jmtodoapp.todotask.domain.model.TaskModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TaskItem(
    task: TaskModel,
    onClick: () -> Unit = {},
    onToggleCompleted: () -> Unit = {},
    onToggleFavorite: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (task.completed) Color.Gray else Color.Black
                )
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
            }
            Row {
                Checkbox(
                    checked = task.completed,
                    onCheckedChange = { onToggleCompleted() }
                )
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (task.favorite) Icons.Default.Star else Icons.Default.Star.apply {
                            tintColor.copy(alpha = 0.5F)
                        },
                        contentDescription = "Favorite",
                        tint = if (task.favorite) Color.Yellow else Color.Gray
                    )
                }
            }
        }
    }
}