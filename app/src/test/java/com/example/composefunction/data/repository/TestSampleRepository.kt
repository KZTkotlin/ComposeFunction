package com.example.composefunction.data.repository

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class TestSampleRepository : SampleRepository {
    override suspend fun sampleUpdate(): Result<Nothing?, Error> {
        return Ok(null)
    }
}
