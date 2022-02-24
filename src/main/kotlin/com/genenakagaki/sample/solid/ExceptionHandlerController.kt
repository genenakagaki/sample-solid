package com.genenakagaki.sample.solid

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono

class UnauthorizedException(): Exception()
class CustomException(message: String): Exception(message)

@RestControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleException(e: UnauthorizedException): Mono<String> {
        return Mono.just("Unauthorized")
    }

    @ExceptionHandler(CustomException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: CustomException): Mono<String> {
        return Mono.just(e.message ?: "")
    }
}
