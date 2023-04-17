package com.binark.school.usermanagement.controller.owner;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.exception.EmailUsedException;
import com.binark.school.usermanagement.mapper.AccountMapper;
import com.binark.school.usermanagement.service.account.CreateAccountService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/owner")
@AllArgsConstructor
public class CreateController {

    private final AccountMapper mapper;

    private final CreateAccountService createAccountService;

    @GetMapping("/new")
    public String createOwner(Model model) {
        this.createAccountService.testCircuitBreacker();
        model.addAttribute("account", new OwnerAccountDTO());
        return "owner/create";
    }

    @PostMapping("/new")
    public String createOwner(@ModelAttribute("account") @Valid OwnerAccountDTO account, BindingResult result, Model model, HttpServletResponse response) {
        System.out.println("account = " + account);
        if (result.hasFieldErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            Map<String, String> errorMap = new HashMap<>();
            fieldErrors.forEach(error -> {
                String errorName = error.getField() + "Error";
                errorMap.put(errorName, error.getDefaultMessage());
            });
            model.addAllAttributes(errorMap);

            return "owner/create";
        }
        //System.out.println("result = " + result.getFieldErrors());


        try {
            this.createAccountService.createOwner(account);
        } catch (EmailUsedException e) {
            e.printStackTrace();
            model.addAttribute("appError", e.getMessage());
            return "owner/create";
        }

        return "redirect:/public/owner";
    }
}
