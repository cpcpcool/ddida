package com.runner.ddida.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

	/*
	 * @Bean protected WebSecurityCustomizer webSecurityCustomizer() { return web ->
	 * web.ignoring() .requestMatchers("/static/**","/css/**", "/js/**","/img/**",
	 * "/fonts/**", "/slick/**");
	 * 
	 * // PathRequest.toStaticResources().atCommonLocations() }
	 */
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorizeRequest) ->
                        authorizeRequest
                                .requestMatchers("/", "sports").permitAll()
                                .requestMatchers("/static/**", "/css/**", "/js/**","/img/**", "/fonts/**", "/slick/**" ).permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                				
                .formLogin((login) ->
                        login
                        	.loginPage("/login")
                            .loginProcessingUrl("/login")
                        	.failureUrl("/login?error=true")
                            .defaultSuccessUrl("/", true)
                            .usernameParameter("username")
                            .passwordParameter("password"))
                .logout(logout -> 
                		logout
                        	.logoutUrl("logout")
                        	.logoutSuccessUrl("/")
                        	.invalidateHttpSession(true));
				
		return http.build();
	}
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
		auth.inMemoryAuthentication() .withUser("test").password("{noop}1111").roles("USER")
		.and()
		.withUser("admin1").password("{noop}1111").roles("ADMIN"); }
	

}
