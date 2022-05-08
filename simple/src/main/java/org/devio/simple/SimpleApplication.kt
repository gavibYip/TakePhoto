package org.devio.simple

import android.app.Application
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog

class SimpleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        XLog.init(LogLevel.ALL)
    }

}