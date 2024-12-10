package com.data.util

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

fun getCurrentTimeInMilliSeconds(): Long {
    return (NSDate().timeIntervalSince1970 * 1000).toLong()
}