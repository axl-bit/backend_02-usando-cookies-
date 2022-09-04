package pe.company.pmmcourier.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pe.company.pmmcourier.jwt.AuthEntryPointJwt;
import pe.company.pmmcourier.jwt.AuthTokenFilter;
import pe.company.pmmcourier.service.UserDetailsServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
	
	@Override
	  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.cors().and().csrf().disable()
	      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
	      .antMatchers("/api/test/**").permitAll()
	      .anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		*/
		
		http.authorizeRequests().and().httpBasic();
		http.authorizeRequests().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
	      .antMatchers("/api/test/**").permitAll()
	      //.antMatchers("/Distrito/**").permitAll()
	      //.antMatchers("/Tarifa/**").permitAll()

	      .anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	

}
