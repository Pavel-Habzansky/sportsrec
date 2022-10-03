package com.pavelhabzansky.sportsrec.features.record_list.model

import androidx.annotation.DrawableRes

sealed class RecordListItem(
    val id: String,
    val name: String,
    val createTime: String,
//    val color: Color,
    @DrawableRes val image: Int,
    val storageType: StorageListType
) {

    class WeightliftingListItem(
        id: String,
        name: String,
        createTime: String,
        @DrawableRes image: Int,
        storageType: StorageListType,
        val weight: Int,
        val sets: Int,
        val totalWeight: Int
    ) : RecordListItem(id, name, createTime, image, storageType)

    class SprintListItem(
        id: String,
        name: String,
        createTime: String,
        @DrawableRes image: Int,
        storageType: StorageListType,
        val distance: Int,
        val time: Float
    ) : RecordListItem(id, name, createTime, image, storageType)

    class RopeJumpListItem(
        id: String,
        name: String,
        createTime: String,
        @DrawableRes image: Int,
        storageType: StorageListType,
        val jumps: Int,
        val time: Float
    ) : RecordListItem(id, name, createTime, image, storageType)

    class CustomListItem(
        id: String,
        name: String,
        createTime: String,
        @DrawableRes image: Int,
        storageType: StorageListType,
        val performance: String,
        val time: Float
    ) : RecordListItem(id, name, createTime, image, storageType)

}