package com.hm.treematch.api.web.service

import com.hm.treematch.api.dao.TreeMatchDao
import com.hm.treematch.api.model.*
import com.hm.treematch.api.web.dto.AnswerDto
import com.hm.treematch.api.web.dto.MatchDto
import com.hm.treematch.api.web.dto.QuestionStepDto
import com.hm.treematch.api.web.dto.StepDto


class TreeMatchServiceImpl(private val stepDao: TreeMatchDao) : TreeMatchService {

    override fun getFirstStep(): QuestionStepDto {
        val firstStep = stepDao.getStep(1) as QuestionStep
        val question = stepDao.getQuestion(firstStep.questionId)
        return QuestionStepDto(firstStep.id, question.question, question.validation)
    }

    override fun getNextStep(answer: AnswerDto): StepDto {

        return  when(val currentStep = stepDao.getStep(answer.stepId)) {
            is   QuestionStep -> nextStep(currentStep, answer)
           else -> throw IllegalArgumentException("invalid step ${currentStep.id}")
        }
    }

    private  fun nextStep(currentStep:QuestionStep, answer:AnswerDto):StepDto {
        fun toQuestion(s: QuestionStep) = stepDao.getQuestion(s.questionId)
        fun toResult(s: ResultStep) = stepDao.getResult(s.resultId)

        val nextStepId = currentStep.answers[answer.answer]!!

        return when (val nextStep = stepDao.getStep(nextStepId)) {
            is QuestionStep -> toQuestion(nextStep).toQuestionStepDto(nextStep.id)
            is ResultStep -> toResult(nextStep).toMatchDto()
        }
    }

    private fun Question.toQuestionStepDto(stepId: StepId): QuestionStepDto {
        return QuestionStepDto(stepId, question, validation)
    }

    private fun TreeMatchResult.toMatchDto(): MatchDto {
        return MatchDto(name, description)
    }
}