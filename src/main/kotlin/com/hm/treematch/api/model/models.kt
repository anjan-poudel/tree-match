package com.hm.treematch.api.model

//creating alias as stepId is used as value in Map
typealias StepId = Int
//using alias as answer choice/option used in a list
typealias AnswerOption = String
//below ones or consistency
typealias QuestionId = Int
typealias ResultId = Int

/* Internal  Data Models */

data class Question(
    val id: QuestionId,
    val question: String,
    val validation: List<AnswerOption>
)

data class TreeMatchResult(
    val id: ResultId,
    val name: String,
    val description: String
)

sealed class Step(open val id: StepId)

data class QuestionStep(
    override val id: StepId,
    val questionId: QuestionId,
    val answers: Map<AnswerOption, StepId>

) : Step(id)

data class ResultStep(override val id: StepId, val resultId: ResultId) : Step(id)






