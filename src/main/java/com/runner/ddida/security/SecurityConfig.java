package com.runner.ddida.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.runner.ddida.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberAuthenticatorProvider memberAuthenticatorProvider;

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(memberAuthenticatorProvider);
	}

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests((authorizeRequest) -> authorizeRequest
						.requestMatchers("/", "/manage","/admin","/join/**", "/login/**", "/logout/**", "/sports", "/ddimap/**").permitAll()
						.requestMatchers("/static/**", "/css/**", "/js/**", "/img/**", "/fonts/**", "/slick/**")
						.permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
					 	.anyRequest().authenticated())

				.formLogin((login) -> login
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.failureUrl("/login?error=true")
						// .failureHandler()
						.defaultSuccessUrl("/", true)
						.usernameParameter("username")
						.passwordParameter("password"))

				.logout(logout -> logout
						.logoutUrl("/logout")	
						.logoutSuccessUrl("/login")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")); // 로그아웃 후 쿠키삭제
				
//				.rememberMe(remember -> remember // 자동 로그인 
//						.key("ddida")
//						.tokenValiditySeconds(1800)
//						.userDetailsService(memberService)
//						.rememberMeParameter("remember-me"))
//					
//				.sessionManagement(session -> session
//						.maximumSessions(1)
//						.maxSessionsPreventsLogin(false)
//						.expiredUrl("/")); // 세션만료 이동url
								
		
		return http.build();
	}

	//관리자계정 생성
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin")
			.password("{bcrypt}1111")
			.roles("ADMIN");
	}

}
