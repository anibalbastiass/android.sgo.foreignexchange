package com.anibalbastias.android.foreignexchange.presentation

import java.io.File

abstract class BaseDataManager {

    companion object {

        fun getErrorMessage(showError: Boolean, methodName: String): Throwable? {
            return if(showError) RuntimeException("Non-fatal error thrown by the Unit Test in method: $methodName")
            else null
        }

        fun getJson(classLoader: ClassLoader?, fileName: String): String {
            val uri = classLoader?.getResource(fileName)
            val file = File(uri?.path)

            return String(file.readBytes())
        }
    }
}