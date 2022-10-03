package com.pavelhabzansky.sportsrec.core.utils

import com.google.android.gms.maps.model.LatLng
import timber.log.Timber

fun String?.toLatLng(): LatLng? {
    Timber.i("Parsing $this to LatLng")
    if (this == null) return null
    if (!(this.startsWith('[') && this.endsWith(']'))) return null

    val split = substring(1, length - 1).split(';')
    if (split.size != 2) return null

    val lat = split.first().toDouble()
    val lng = split.last().toDouble()

    return LatLng(lat, lng)
}