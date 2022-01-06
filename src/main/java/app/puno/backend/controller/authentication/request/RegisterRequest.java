package app.puno.backend.controller.authentication.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record RegisterRequest(@Length(min = 3, max = 64) String username, @Email String email,
							  @NotEmpty String password) {

}
