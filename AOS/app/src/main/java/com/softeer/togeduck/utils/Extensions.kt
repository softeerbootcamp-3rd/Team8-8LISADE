package com.softeer.togeduck.utils

import android.content.res.Resources
import android.util.Log
import java.text.DecimalFormat


fun Int.fromDpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()

fun String.convertS3Url(): String {
    val prefix = "s3://"
    val httpPrefix = "https://"
    val s3Domain = ".s3.amazonaws.com/"

    if (!this.startsWith(prefix)) {
        throw IllegalArgumentException("URL must start with $prefix")
    }
    val withoutPrefix = this.removePrefix(prefix)

    val bucketName = withoutPrefix.substringBefore("/")
    val objectKey = withoutPrefix.substringAfter("/")

    return "$httpPrefix$bucketName$s3Domain$objectKey"
}

fun Int.addCommas(): String {
    val dec = DecimalFormat("#,###")

    return dec.format(this)
}