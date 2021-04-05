package org.zhaoxi.reader.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/management")
public class ManagementController {
    @GetMapping("/")
    public ModelAndView showIndex() {
        return new ModelAndView("/management/index");
    }
}
