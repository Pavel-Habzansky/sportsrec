package com.pavelhabzansky.sportsrec.features.record_detail.model

import com.google.android.gms.maps.model.LatLng
import com.pavelhabzansky.sportsrec.features.new_record.model.RecordType

sealed class RecordDetail(
    val id: String,
    val name: String,
    val type: RecordType,
    val location: LatLng?
) {

    class Weightlifting(
        id: String,
        name: String,
        type: RecordType,
        location: LatLng?,
        val weight: String,
        val sets: Int,
        val repsPerSet: Map<Int, String>
    ) : RecordDetail(id, name, type, location)

    class Sprint(
        id: String,
        name: String,
        type: RecordType,
        location: LatLng?,
        val distance: String,
        val time: String
    ) : RecordDetail(id, name, type, location)

    class RopeJump(
        id: String,
        name: String,
        type: RecordType,
        location: LatLng?,
        val jumps: String,
        val time: String
    ) : RecordDetail(id, name, type, location)

    class Custom(
        id: String,
        name: String,
        type: RecordType,
        location: LatLng?,
        val performance: String,
        val time: String
    ) : RecordDetail(id, name, type, location)

}