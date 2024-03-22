package org.example.cafe.controllers;

import jakarta.validation.Valid;
import org.example.cafe.dao.MenuItemDAO;
import org.example.cafe.models.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {
    private final MenuItemDAO menuItemDAO;


    public MenuController(MenuItemDAO menuItemDAO) {
        this.menuItemDAO = menuItemDAO;
    }

    @GetMapping("/items")
    public String getAllMenuItems(ModelMap model){
        List<MenuItem> menuItems = menuItemDAO.view();
        model.put("menuItems", menuItems);
        return "menu/items";
    }
    @PostMapping("/add")
    public String setMenuItems(@RequestBody @Valid MenuItem menuItem, BindingResult bindingResult) {
            if(bindingResult.hasErrors()){
                return "menu/binding";
            }
            menuItemDAO.save(menuItem);
            return "redirect:/menu/items";
    }

    @PutMapping(value = "/update/{itemId}")
    public String updateMenuItems(@PathVariable("itemId") Long itemId,@RequestBody MenuItem menuItem){
        menuItemDAO.update(itemId,menuItem);
        return "redirect:/menu/items";
    }

    @DeleteMapping("/delete/{itemId}")
    public String deleteMenuItems(@PathVariable("itemId") Long itemId) {
        menuItemDAO.delete(itemId);
        return "redirect:/menu/items";
    }


}
