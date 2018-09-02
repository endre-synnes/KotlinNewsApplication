package com.endre.kotlin.news.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * Created by Endre on 02.09.2018.
 * news
 * Verson:
 */

@Configuration
class Security : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var customLoginSuccessHandler: CustomAuthenticationSuccessHandler

    override fun configure(http: HttpSecurity): Unit {
        http
                .authorizeRequests()
                .antMatchers("/*").permitAll()
                .antMatchers("/articles/api/articles").permitAll()
                .and()
                .cors().disable()
                .csrf().disable()
//                .antMatchers("/admin").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().successHandler(customLoginSuccessHandler)
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()

        //http.exceptionHandling().accessDeniedPage("/403")
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder): Unit {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("user").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN")
    }
}