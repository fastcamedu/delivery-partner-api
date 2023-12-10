package com.fastcampus.deliverypartnerapi.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter



@Component
class JwtExceptionFilter(
    val objectMapper: ObjectMapper
): OncePerRequestFilter() {

    companion object {
        private const val CONTENT_TYPE = "application/json; charset=UTF-8"
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: ExpiredJwtException) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex)
        } catch (ex: JwtException) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex)
        }
    }

    fun setErrorResponse(status: HttpStatus, res: HttpServletResponse, ex: Throwable) {
        res.status = status.value()
        res.contentType = CONTENT_TYPE
        val jwtExceptionResponse = ex.message?.let {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(it)
        }
        res.writer.write(objectMapper.writeValueAsString(jwtExceptionResponse))
    }
}