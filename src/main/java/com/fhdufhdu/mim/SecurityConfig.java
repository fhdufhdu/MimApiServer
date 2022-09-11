package com.fhdufhdu.mim;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fhdufhdu.mim.security.CustomAccessDeniedHandler;
import com.fhdufhdu.mim.security.CustomAuthenticationEntryPoint;
import com.fhdufhdu.mim.security.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
        public final JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .cors().configurationSource(corsConfigurationSource()).and()
                                .formLogin().disable()// 1 - formLogin 인증방법 비활성화
                                .httpBasic().disable()// 2 - httpBasic 인증방법 비활성화(특정 리소스에 접근할 때 username과password 물어봄)
                                .csrf().disable()

                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                                .and()

                                .exceptionHandling()
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                .accessDeniedHandler(new CustomAccessDeniedHandler())
                                .and()

                                .authorizeRequests()
                                .antMatchers("/swagger-ui/**",
                                                "/swagger-resources/**", "/v2/**")
                                .permitAll()
                                .antMatchers("/login", "/sign-up", "/users/id/{id}",
                                                "/users/nick-name/{nickName}")
                                .permitAll()
                                .anyRequest().permitAll()
                                .and()

                                .addFilterBefore(
                                                jwtFilter,
                                                UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.addAllowedOrigin("*");
                configuration.addAllowedHeader("*");
                configuration.addAllowedMethod("*");
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

}
