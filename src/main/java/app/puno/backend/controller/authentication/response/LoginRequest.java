package app.puno.backend.controller.authentication.response;

import javax.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty String identifier, @NotEmpty String password) {

}
