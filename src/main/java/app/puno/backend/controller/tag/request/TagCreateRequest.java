package app.puno.backend.controller.tag.request;

import javax.validation.constraints.Size;

public record TagCreateRequest(@Size(min = 3, max = 64) String tag) {

}
