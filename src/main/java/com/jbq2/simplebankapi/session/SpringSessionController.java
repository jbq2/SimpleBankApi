package com.jbq2.simplebankapi.session;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/sessions")
@Controller
public class SpringSessionController {

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request){
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
        if(messages == null){
            messages = new ArrayList<>();
            request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
        }
        messages.add(msg);
        request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
        return "redirect:/";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }
}
