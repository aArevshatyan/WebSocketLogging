package am.aca.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionScope
public class MvcController {

    private String data;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView index = new ModelAndView("index");
        index.addObject("sessionId", RequestContextHolder.currentRequestAttributes().getSessionId());
        return index;
    }

    @PostMapping
    public ModelAndView migrationPage(@RequestBody String data) {
        this.data = data;
        return new ModelAndView("ahsdfuidsa");
    }

}