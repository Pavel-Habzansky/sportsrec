package com.pavelhabzansky.domain.features.new_record.usecase

import java.util.UUID

class GenerateRandomIdUseCase {
    operator fun invoke(): String {
        return UUID.randomUUID().toString()
    }
}