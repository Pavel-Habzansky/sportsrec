package com.pavelhabzansky.sportsrec.features.new_record.model

import androidx.annotation.StringRes
import com.pavelhabzansky.domain.features.sports_records.model.StorageType
import com.pavelhabzansky.sportsrec.R

enum class StorageTypeView(@StringRes val textId: Int) {
    LOCAL(textId = R.string.storage_type_local),
    REMOTE(textId = R.string.storage_type_remote)
}

fun StorageTypeView.toDomain(): StorageType {
    return when (this) {
        StorageTypeView.LOCAL -> StorageType.LOCAL
        else -> StorageType.REMOTE
    }
}