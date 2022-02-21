package com.genenakagaki.sample.solid

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono

class UnauthorizedException(): Exception()

@RestControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleException(e: UnauthorizedException): Mono<String> {
        return Mono.just("Unauthorized")
    }
}
