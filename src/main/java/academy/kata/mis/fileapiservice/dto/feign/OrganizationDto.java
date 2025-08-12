package academy.kata.mis.fileapiservice.dto.feign;

import lombok.Builder;

@Builder
public record OrganizationDto(long id,
                              String name,
                              String address) {
}