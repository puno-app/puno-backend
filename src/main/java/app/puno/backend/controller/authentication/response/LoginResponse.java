package app.puno.backend.controller.authentication.response;

import java.util.UUID;

public record LoginResponse(UUID refreshToken, String token) {

}
