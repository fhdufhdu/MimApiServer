package com.fhdufhdu.mim;

import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.security.CustomAccessDeniedHandler;
import com.fhdufhdu.mim.security.CustomAuthenticationEntryPoint;
import com.fhdufhdu.mim.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@ComponentScan
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String ADMIN = Role.ADMIN.name();
    private static final String USER = Role.USER.name();

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .formLogin().disable()// 1 - formLogin 인증방법 비활성화
                .httpBasic().disable()// 2 - httpBasic 인증방법 비활성화(특정 리소스에 접근할 때 username과 password 물어봄)
                .csrf().disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()

                .authorizeRequests()
                .antMatchers("/login", "/sign-up").permitAll()
                .antMatchers("/users/{id}").hasAnyRole(USER, ADMIN)
                .antMatchers("/users").hasAnyRole(ADMIN)

                .antMatchers("/movies/**", "/scean", "line").permitAll()
                .anyRequest().authenticated()
                .and()

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        // http.addFilterBefore(jsonUsernamePasswordLoginFilter(), LogoutFilter.class);

    }
}
