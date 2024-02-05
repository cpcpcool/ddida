package com.runner.ddida.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.runner.ddida.service.DdidaUserDetailsService;

import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final DdidaUserDetailsService ddidaUserDetailsService;
	private final LoginSuccessHandler loginSuccessHandler;
	
	@Bean	
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/join/**", "/newAdmin", "/login/**", "/logout/**", "/success", "/sports", "/ddimap/**").permitAll()
						.requestMatchers("/static/**", "/css/**", "/js/**", "/img/**", "/fonts/**", "/slick/**").permitAll()
						.requestMatchers("/admin/login", "/access/denied").permitAll() // 별도의 관리자 로그인 페이지 허용
						.requestMatchers("/sports/{rsrcNo}").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
					 	.anyRequest().authenticated()) 
				
				.exceptionHandling(error -> error
						.accessDeniedPage("/access/denied"))
				
				.formLogin(login -> login
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.failureUrl("/login?error=true")
						.usernameParameter("username")	
						.passwordParameter("password")
						.successHandler(loginSuccessHandler))
				
				.logout(logout -> logout
						.logoutUrl("/logout")	
						.logoutSuccessUrl("/login")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")) // 로그아웃 후  쿠키삭제
				
				.rememberMe(remember -> remember // 자동 로그인 
						.key("ddida")
						.tokenValiditySeconds(600)
						.userDetailsService(ddidaUserDetailsService)
						.rememberMeParameter("remember-me"));
					
//				.sessionManagement(session -> session
//						.maximumSessions(1) // 동일 계정 최대 세션 갯수 제한
//						.maxSessionsPreventsLogin(false)
//						.expiredUrl("/")); // 세션만료 이동url
//								
		
		return http.build();
	}

}
