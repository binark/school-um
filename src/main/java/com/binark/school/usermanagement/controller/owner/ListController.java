package com.binark.school.usermanagement.controller.owner;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/owner")
@AllArgsConstructor
public class ListController {

    private final OwnerService ownerService;

    @GetMapping
    public String getAllOwners(Model model) {
        List<OwnerAccountDTO> owners = this.ownerService.getAll();
        model.addAttribute("owners", owners);
        return "owner/list";
    }
}
