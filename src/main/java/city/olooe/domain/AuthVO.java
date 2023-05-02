package city.olooe.domain;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("auth")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthVO {

	private String userid;
	private String auth;
}
