package com.test.EveryDayHelp.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {
        @ModelAttribute
    public void setTitle(Model model)
        {
            model.addAttribute("title","EveryDayHelp");
        }
}
