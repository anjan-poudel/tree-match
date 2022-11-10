package com.hm.treematch.api.web.controller

import com.hm.treematch.api.web.dto.*
import com.hm.treematch.api.web.service.TreeMatchService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class QuestionStepController(private val treeMatchService: TreeMatchService) {

    @GetMapping("/begin")
    fun begin(): QuestionStepResponse {
         return QuestionStepResponse(treeMatchService.getFirstStep())
    }

    @PostMapping(value= ["/answer"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun answer(answer: AnswerDto): AnswerResponse {
        return when(val nextStep = treeMatchService.getNextStep(answer)){
            is QuestionStepDto -> QuestionStepResponse(nextStep)
            is MatchDto -> MatchResponse(nextStep)
        }
    }

}