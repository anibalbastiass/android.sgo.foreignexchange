package com.anibalbastias.android.foreignexchange.domain.base.executor

import io.reactivex.Scheduler

interface APIPostExecutionThread {
    val scheduler: Scheduler
}
