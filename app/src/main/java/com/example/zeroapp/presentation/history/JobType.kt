package com.example.zeroapp.presentation.history

import kotlinx.coroutines.Job

sealed class JobType(val job: Job) {
    class AllDays(job: Job) : JobType(job)
    class Month(job: Job) : JobType(job)
    class Week(job: Job) : JobType(job)
    class Filter(job: Job) : JobType(job)
}