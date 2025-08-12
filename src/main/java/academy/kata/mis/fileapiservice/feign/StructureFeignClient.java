package academy.kata.mis.fileapiservice.feign;

import academy.kata.mis.fileapiservice.dto.feign.OrganizationDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "structure-service")
public interface StructureFeignClient {

    @GetMapping("/internal/structure/organization/address")
    OrganizationDto getOrganizationById(@RequestParam(name = "id") long medOrgId);
}