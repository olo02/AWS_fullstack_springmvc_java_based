package city.olooe.config;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import city.olooe.security.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new CharacterEncodingFilter("utf-8", true), CsrfFilter.class);
		
        http.authorizeRequests()
        .antMatchers("/sample/admin").access("hasRole('ROLE_ADMIN')")
        .antMatchers("/sample/admin").access("hasRole('ROLE_MANAGER')");
		
		http.formLogin().loginPage("/member/login");
		http.logout().invalidateHttpSession(true).logoutUrl("/member/logout").logoutSuccessUrl("/");
		
        http.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(60 * 60 * 24 * 7);

	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    	auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
//    	auth.inMemoryAuthentication().withUser("member").password("{noop}member").roles("MANAGER");
    	
/*    	String users_by_username_query="select userid username, userpw password, enabled from tbl_member where userid = ?";
    	String authorities_by_username_query="select userid username, auth authority from tbl_auth where userid = ?";
*/    	
/*    	auth.jdbcAuthentication()
    	.dataSource(dataSource)
    	.passwordEncoder(passwordEncoder())
    	.usersByUsernameQuery(users_by_username_query)
    	.authoritiesByUsernameQuery(authorities_by_username_query);*/
    	
    	auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    	
    }	
    
    @Bean
    public UserDetailsService userDetailService() {
    	return new CustomUserDetailsService();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }	
	
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

}
