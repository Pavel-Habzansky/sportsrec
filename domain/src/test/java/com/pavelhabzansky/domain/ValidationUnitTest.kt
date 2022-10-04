package com.pavelhabzansky.domain

import com.pavelhabzansky.domain.features.new_record.usecase.*
import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ValidationUnitTest {

    @Test
    fun uuid_isGenerated() {
        val generateId = GenerateRandomIdUseCase()
        val uuid = generateId()

        assert(uuid.isNotEmpty())
    }

    @Test
    fun repsInput_isValid() {
        val useCase = IsRepsInputValidUseCase()

        val paramsValid = IsRepsInputValidUseCase.Params(repsInput = "12")
        assert(useCase(paramsValid))

        val paramsLow = IsRepsInputValidUseCase.Params(repsInput = "-12")
        assertFalse(useCase(paramsLow))

        val paramsHigh = IsRepsInputValidUseCase.Params(repsInput = "666")
        assertFalse(useCase(paramsHigh))

        val paramsInvalid = IsRepsInputValidUseCase.Params(repsInput = "hello world")
        assertFalse(useCase(paramsInvalid))
    }

    @Test
    fun ropeJumpsInput_isValid() {
        val useCase = IsRopeJumpsInputValidUseCase()

        val paramsValid = IsRopeJumpsInputValidUseCase.Params(jumpsInput = "20")
        assert(useCase(paramsValid))

        val paramsEmpty = IsRopeJumpsInputValidUseCase.Params(jumpsInput = "")
        assert(useCase(paramsEmpty))

        val paramsInvalid = IsRopeJumpsInputValidUseCase.Params(jumpsInput = "hello world")
        assertFalse(useCase(paramsInvalid))
    }

    @Test
    fun sprintDistance_isValid() {
        val useCase = IsSprintDistanceInputValidUseCase()

        val paramsValid = IsSprintDistanceInputValidUseCase.Params("100")
        assert(useCase(paramsValid))

        val paramsEmpty = IsSprintDistanceInputValidUseCase.Params("")
        assert(useCase(paramsEmpty))

        val paramsHigh = IsSprintDistanceInputValidUseCase.Params("1000")
        assertFalse(useCase(paramsHigh))

        val paramsInvalid = IsSprintDistanceInputValidUseCase.Params("the meaning of life")
        assertFalse(useCase(paramsInvalid))
    }

    @Test
    fun timeInput_isValid() {
        val useCase = IsTimeInputValidUseCase()

        val paramsValid = IsTimeInputValidUseCase.Params(timeInput = "20")
        assert(useCase(paramsValid))

        val paramsDot = IsTimeInputValidUseCase.Params(timeInput = "20.0")
        assert(useCase(paramsDot))

        val paramsInvalidDot = IsTimeInputValidUseCase.Params(timeInput = "20,0")
        assertFalse(useCase(paramsInvalidDot))

        val paramsInvalid = IsTimeInputValidUseCase.Params(timeInput = "hello")
        assertFalse(useCase(paramsInvalid))
    }

    @Test
    fun weightInput_isValid() {
        val useCase = IsWeightInputValidUseCase()

        val paramsValid = IsWeightInputValidUseCase.Params("50")
        assert(useCase(paramsValid))

        val paramsEmpty = IsWeightInputValidUseCase.Params("")
        assert(useCase(paramsEmpty))

        val paramsLow = IsWeightInputValidUseCase.Params("-24")
        assertFalse(useCase(paramsLow))

        val paramsHigh = IsWeightInputValidUseCase.Params("1001")
        assertFalse(useCase(paramsHigh))

        val paramsInvalid = IsWeightInputValidUseCase.Params("skjdfh")
        assertFalse(useCase(paramsInvalid))
    }

}