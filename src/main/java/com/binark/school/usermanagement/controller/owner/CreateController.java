package com.binark.school.usermanagement.controller.owner;

import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import com.binark.school.usermanagement.exception.AccountIdentifierUsedException;
import com.binark.school.usermanagement.mapper.AccountMapper;
import com.binark.school.usermanagement.service.account.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/owner")
@RequiredArgsConstructor
public class CreateController {

    private final AccountMapper mapper;

    @Autowired
    @Qualifier("createOwner")
    private AccountService createOwnerService;

    @GetMapping("/new")
    public String createOwner(Model model) {
     //   this.createAccountService.testCircuitBreacker();
        model.addAttribute("account", new OwnerAccountDTO());
        return "owner/create";
    }

    /**
     * Create an owner and redirect to the owner listing page
     * @param account {@link OwnerAccountDTO} data
     * @param result used to get errors
     * @param model to send data to the view
     * @param response
     * @return
     */
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
            this.createOwnerService.create(mapper.toOwnerEntity(account));
        } catch (AccountIdentifierUsedException e) {
            e.printStackTrace();
            model.addAttribute("appError", e.getMessage());
            return "owner/create";
        }

        return "redirect:/admin/owner";
    }
}