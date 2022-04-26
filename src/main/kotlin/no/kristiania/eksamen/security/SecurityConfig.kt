package no.kristiania.eksamen.security

import no.kristiania.eksamen.security.filter.CustomAuthenticationFilter
import no.kristiania.eksamen.security.filter.CustomAuthorizationFilter
import no.kristiania.eksamen.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
@Configuration
class SecurityConfig(
    @Autowired private val userDetailsService: UserDetailsService,
    @Autowired private val passwordEncoder: BCryptPasswordEncoder
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        val authenticationFilter = CustomAuthenticationFilter(authenticationManagerBean())
        authenticationFilter.setFilterProcessesUrl("/api/login")


        http.csrf().disable()
        http.sessionManagement().disable()
        http.authorizeRequests()
            .antMatchers("/api/login").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/shelter/all/**").hasAnyAuthority("USER", "EMPLOYEE", "ADMIN")
            .antMatchers(HttpMethod.GET, "/api/shelter/**").hasAnyAuthority("USER", "EMPLOYEE", "ADMIN")
            .antMatchers("/api/user/**").hasAnyAuthority("EMPLOYEE", "ADMIN")
            .antMatchers("/api/shelter/**").hasAnyAuthority("EMPLOYEE", "ADMIN")
            .antMatchers("/api/authority/**").hasAuthority("ADMIN")
            .antMatchers("/api/animal/new").hasAuthority("ADMIN")
        http.authorizeHttpRequests().anyRequest().authenticated()
        http.addFilter(authenticationFilter)
        http.addFilterBefore(CustomAuthorizationFilter(), CustomAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}