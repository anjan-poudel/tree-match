package com.hm.treematch.api.web.dto

import com.hm.treematch.api.model.AnswerOption

/* public DTOs */

/** question answer option choice */
typealias AnswerOptionDto = AnswerOption
/** User answer */
data class AnswerDto(val stepId: Int, val answer: AnswerOptionDto)

/** Steps permitted in the matching process */
sealed interface StepDto
data class QuestionStepDto(val stepId: Int, val question: String, val answers: List<AnswerOptionDto>) : StepDto
data class MatchDto(val name: String, val description: String) : StepDto


