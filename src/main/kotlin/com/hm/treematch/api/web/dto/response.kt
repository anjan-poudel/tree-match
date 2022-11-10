package com.hm.treematch.api.web.dto



sealed interface AnswerResponse

data class QuestionStepResponse(val question: QuestionStepDto): AnswerResponse

data class MatchResponse(val match: MatchDto): AnswerResponse