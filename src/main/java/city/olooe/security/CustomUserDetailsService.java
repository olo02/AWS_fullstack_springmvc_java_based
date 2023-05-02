package city.olooe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import city.olooe.domain.CustomUser;
import city.olooe.domain.MemberVO;
import city.olooe.mapper.MemberMapper;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*log.warn(String.format("CustomUserDetailsService.loadUserByUsername(%s)", username));*/
		
		MemberVO vo = mapper.read(username);
		
		return vo == null ? null : new CustomUser(vo);
	}

}
