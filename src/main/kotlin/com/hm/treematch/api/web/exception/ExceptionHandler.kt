package com.hm.treematch.api.web.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Generic Exception handler to map application exceptions to http error/exception
 */
@ControllerAdvice
class TreeMatchApiExceptionHandler {
    private val logger = LoggerFactory.getLogger(TreeMatchApiExceptionHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun handle(  ex: Exception?,  request: HttpServletRequest?, response: HttpServletResponse?   ): ResponseEntity<Any> {

        return when (ex) {
            is RuntimeException -> {
                logger.warn("request error: ${ex.message}, url : ${request?.requestURI}")
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
            }
            else -> {
                logger.error("unhandled error: ${ex?.message}, url : ${request?.requestURI}", ex)
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }
        }
    }
}
