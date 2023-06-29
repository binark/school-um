package com.binark.school.usermanagement.controller.owner;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.mapper.AccountMapper;
import com.binark.school.usermanagement.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/owner")
@AllArgsConstructor
public class ListController {

    private final OwnerService ownerService;

    private final AccountMapper mapper;

    @GetMapping
    public String getAllOwners(Model model) {
        List<OwnerAccountDTO> owners = this.ownerService.getAll()
                                                        .stream()
                                                        .map(mapper::toOwnerDto)
                                                        .collect(Collectors.toList());
        model.addAttribute("owners", owners);
        return "owner/list";
    }
}
