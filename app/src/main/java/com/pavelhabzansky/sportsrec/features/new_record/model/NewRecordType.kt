package com.pavelhabzansky.sportsrec.features.new_record.model

import androidx.annotation.StringRes
import com.pavelhabzansky.domain.core.common.SportsRecordType
import com.pavelhabzansky.sportsrec.R

enum class RecordType(@StringRes val textResource: Int) {
    WEIGHTLIFTING(textResource = R.string.record_type_weightlifting),
    SPRINT(textResource = R.string.record_type_sprint),
    ROPE_JUMP(textResource = R.string.record_type_rope_jump),
    CUSTOM(textResource = R.string.record_type_custom),
    NONE(textResource = R.string.record_type_none)
}

fun RecordType.toDomain(): SportsRecordType {
    return when(this) {
        RecordType.WEIGHTLIFTING -> SportsRecordType.WEIGHTLIFTING
        RecordType.SPRINT -> SportsRecordType.SPRINT
        RecordType.ROPE_JUMP -> SportsRecordType.ROPE_JUMP
        RecordType.CUSTOM -> SportsRecordType.CUSTOM
        else -> throw IllegalStateException("$this can't be mapped")
    }
}
