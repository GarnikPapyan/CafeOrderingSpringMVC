package org.example.cafe.controllers;

import org.example.cafe.dao.MenuItemDAO;
import org.example.cafe.models.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
        List<MenuItem> menuItems = menuItemDAO.getAll();
        model.put("menuItems", menuItems);
        return "menu/items";
    }
    @PostMapping("/add")
    public String setMenuItems(@RequestBody MenuItem menuItem) {
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
