package com.binark.school.usermanagement.proxy;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${proxy.owner.name}", url = "${proxy.owner.url}")
public interface OwnerProxy {

    @PostMapping
    void createOwner(OwnerAccountDTO accountDTO);
}
