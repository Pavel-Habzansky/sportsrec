package com.pavelhabzansky.sportsrec.features.record_list.model

import androidx.compose.ui.graphics.Color

sealed class RecordListItem(
    val id: String,
    val name: String,
    val createTime: String,
    val color: Color,
    val storageType: StorageListType
) {

    class WeightliftingListItem(
        id: String,
        name: String,
        createTime: String,
        color: Color,
        storageType: StorageListType,
        val weight: Int,
        val sets: Int,
        val totalWeight: Int
    ) : RecordListItem(id, name, createTime, color, storageType)

    class SprintListItem(
        id: String,
        name: String,
        createTime: String,
        color: Color,
        storageType: StorageListType,
        val distance: Int,
        val time: Float
    ) : RecordListItem(id, name, createTime, color, storageType)

    class RopeJumpListItem(
        id: String,
        name: String,
        createTime: String,
        color: Color,
        storageType: StorageListType,
        val jumps: Int,
        val time: Float
    ) : RecordListItem(id, name, createTime, color, storageType)

    class CustomListItem(
        id: String,
        name: String,
        createTime: String,
        color: Color,
        storageType: StorageListType,
        val performance: String,
        val time: Float
    ) : RecordListItem(id, name, createTime, color, storageType)

}