package com.endre.kotlin.news.security

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */
@Component
class CustomAuthenticationSuccessHandler: AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(request: HttpServletRequest,
                                         response: HttpServletResponse, authentication: Authentication){

        response.status = HttpServletResponse.SC_OK
        var admin = false

        for (auth in authentication.authorities)
            if ("ROLE_ADMIN" == auth.authority){
                admin = true
                break
            }

        if(admin)
            response.sendRedirect("/admin")
        else
            response.sendRedirect("/user")
    }
}