package com.hm.treematch.api.web.service

import com.hm.treematch.api.dao.IdNotFound
import com.hm.treematch.api.model.*
import com.hm.treematch.api.web.dto.AnswerDto
import com.hm.treematch.api.web.dto.MatchDto
import com.hm.treematch.api.web.dto.QuestionStepDto
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import com.hm.treematch.api.dao.TreeMatchDao as TreeMatchDao

@ExtendWith(MockitoExtension::class)
internal class TreeMatchServiceImplTest {

    private lateinit var service:TreeMatchServiceImpl
   private  lateinit var dao:TreeMatchDao
    @BeforeEach
    fun setup(){
        dao = getDao()
        service = TreeMatchServiceImpl(dao)
    }

    @Test
    fun getFirstStep() {
        assertEquals(1, service.getFirstStep().stepId)

    }

    @Test
    fun `test getNextStep - next question`() {
        val nextStep = service.getNextStep(AnswerDto(1,"no")) as QuestionStepDto
        assertEquals(3, nextStep.stepId)
    }

    @Test
    fun `test getNextStep - match result`() {
        val  matchResult = service.getNextStep(AnswerDto(3,"yes")) as MatchDto
        assertEquals(dao.getResult(1).name, matchResult.name)
    }

    @Test
    fun `test getNextStep with invalid step id - non-existent id`() {
        assertThrows(IdNotFound::class.java) {service.getNextStep(AnswerDto(-1,"yes")) }
    }
    @Test
    fun `test getNextStep with invalid step id - with match result stepId`() {
        assertThrows(IllegalArgumentException::class.java) {service.getNextStep(AnswerDto(5,"yes")) }
    }


    fun getDao(): TreeMatchDao {

         val questions = listOf(
            Question(1,"yes/no" ,listOf("yes", "no")),
            Question(2,"0/1" ,listOf("0", "1")),
            Question(3,"more for yes?" ,listOf("yes", "no"))
        ).associateBy{it.id}

         val results = listOf(
            TreeMatchResult(1, "blue gum", "great"),
            TreeMatchResult(2, "red gum", "great too")
        ).associateBy{it.id}

          var steps = listOf(
            QuestionStep(id=1 , questionId=1, answers=mapOf("yes" to 2, "no" to 3)),
            QuestionStep(id=2 , questionId=2, answers=mapOf("0" to 3, "1" to 4)),
            QuestionStep(id=3 , questionId=3, answers=mapOf("yes" to 4, "no" to 5)),
            ResultStep(id=4, resultId = 1),
            ResultStep(id=5, resultId = 3)
        ).associateBy{it.id}

       return object:TreeMatchDao {
           override fun getStep(id: StepId): Step = steps[id] ?: throw IdNotFound(id)
           override fun getQuestion(id: QuestionId): Question = questions[id] ?: throw IdNotFound(id)
           override fun getResult(id: ResultId): TreeMatchResult = results[id] ?: throw IdNotFound(id)
       }
    }
}