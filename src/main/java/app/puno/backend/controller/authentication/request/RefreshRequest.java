package app.puno.backend.controller.authentication.request;

import java.util.UUID;

public record RefreshRequest(UUID refreshToken) {

}
