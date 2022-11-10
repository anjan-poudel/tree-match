package com.hm.treematch.api.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hm.treematch.TreeMatchApplication
import com.hm.treematch.api.dao.IdNotFound
import com.hm.treematch.api.web.dto.AnswerDto
import com.hm.treematch.api.web.dto.QuestionStepDto
import com.hm.treematch.api.web.dto.QuestionStepResponse
import com.hm.treematch.api.web.service.TreeMatchService
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [TreeMatchApplication::class])
@WebMvcTest(QuestionStepController::class)
internal class QuestionStepControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var  service: TreeMatchService

    @BeforeEach
    fun setup(){
       `when`(service.getFirstStep()).thenReturn( QuestionStepDto(1,"q1",listOf("a","b")) )

    }
    @Test
    fun `begin - success`() {
        mockMvc.perform(get("/begin")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("q1")))
    }

    @Test
    @Disabled("doesn't work yet")
    fun `answer - invalid id results in 400 error`() {
        val invalidAnswer = AnswerDto(100,"invalid")
        `when`(service.getNextStep(invalidAnswer)).thenThrow(IdNotFound::class.java)

        mockMvc.perform(post("/answer").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidAnswer)))
            .andExpect(status().`is`(400))
            .andDo(print());


    }
}