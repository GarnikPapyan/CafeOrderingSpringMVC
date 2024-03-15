package org.example.cafe.controllers;

import org.example.cafe.dao.BillingDAO;
import org.example.cafe.models.Billing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/billing")
public class BillingController {
    private final BillingDAO billingDAO;

    public BillingController(BillingDAO billingDAO){
        this.billingDAO = billingDAO;
    }

    @GetMapping("/generate/{orderId}")
    public String viewBilling(ModelMap modelMap,@PathVariable("orderId") Long orderId){
        Billing billing = billingDAO.view(orderId);
        modelMap.put("billing",billing);
        return "bill/view";
    }
}
