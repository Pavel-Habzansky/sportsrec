package com.pavelhabzansky.sportsrec.features.record_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pavelhabzansky.sportsrec.R

@Composable
fun ControlBar(
    clickHandler: (ControlBarEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .height(32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { clickHandler(ControlBarEvent.FilterClickEvent) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter_list),
                contentDescription = stringResource(id = R.string.content_description_filter_list)
            )
        }
        Spacer(modifier = Modifier.width(6.dp))
        IconButton(onClick = { clickHandler(ControlBarEvent.UploadClickEvent) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_synchronize),
                contentDescription = stringResource(id = R.string.content_description_synchronize)
            )
        }
        Spacer(modifier = Modifier.width(6.dp))
        IconButton(onClick = { clickHandler(ControlBarEvent.SynchronizeClickEvent) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = stringResource(id = R.string.content_description_refresh)
            )
        }
    }
}