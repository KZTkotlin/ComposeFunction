package com.example.composefunction.data.repository

import com.github.michaelbull.result.Result

interface SampleRepository {
    suspend fun sampleUpdate(): Result<Nothing?, Error>
}
