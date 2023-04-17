package com.binark.school.usermanagement.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "${proxy.owner.name}", url = "${proxy.owner.url}")
public interface OwnerProxy {

    public static final String NAME = "create-owner";

   // @PostMapping
    @GetMapping
    String createOwner();

}
