package am.aca.controllers;

import am.aca.work.HardWork;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping()
public class LogController {
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @PostMapping("/")
    public ModelAndView indexPost() {
        HardWork.test();
        return new ModelAndView("redirect:");
    }

}
