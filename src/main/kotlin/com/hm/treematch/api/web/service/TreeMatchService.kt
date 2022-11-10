package com.hm.treematch.api.web.service

import com.hm.treematch.api.web.dto.AnswerDto
import com.hm.treematch.api.web.dto.QuestionStepDto
import com.hm.treematch.api.web.dto.StepDto

/**
 * Minimal service definition for out api
 */
interface TreeMatchService {

    fun getFirstStep(): QuestionStepDto

    fun getNextStep(answer: AnswerDto): StepDto
}