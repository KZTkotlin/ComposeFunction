package com.example.composefunction.data.repository.impl

import com.example.composefunction.data.repository.SampleRepository
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class DefaultSampleRepository : SampleRepository {
    override suspend fun sampleUpdate(): Result<Nothing?, Error> {
        // TODO("Not yet implemented")
        return Ok(null)
    }
}
