package com.pavelhabzansky.sportsrec.features.new_record.model

import androidx.annotation.StringRes
import com.pavelhabzansky.domain.features.sports_records.model.RecordType
import com.pavelhabzansky.sportsrec.R

enum class NewRecordType(@StringRes val textResource: Int) {
    WEIGHTLIFTING(textResource = R.string.record_type_weightlifting),
    SPRINT(textResource = R.string.record_type_sprint),
    ROPE_JUMP(textResource = R.string.record_type_rope_jump),
    CUSTOM(textResource = R.string.record_type_custom),
    NONE(textResource = R.string.record_type_none)
}

fun NewRecordType.toDomain(): RecordType {
    return when(this) {
        NewRecordType.WEIGHTLIFTING -> RecordType.WEIGHTLIFTING
        NewRecordType.SPRINT -> RecordType.SPRINT
        NewRecordType.ROPE_JUMP -> RecordType.ROPE_JUMP
        NewRecordType.CUSTOM -> RecordType.CUSTOM
        else -> throw IllegalStateException("$this can't be mapped")
    }
}
