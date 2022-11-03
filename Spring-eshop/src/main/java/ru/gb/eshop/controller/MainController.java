package ru.gb.eshop.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.eshop.service.SessionObjectHolder;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class MainController {

    private final SessionObjectHolder sessionObjectHolder;

    public MainController(SessionObjectHolder sessionObjectHolder) {
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @RequestMapping({"","/"})
    public String index(Model model, HttpSession httpSession){
        model.addAttribute("amountClicks", sessionObjectHolder.getAmountClicks());

        // Эта фишка с UUID для бемонстрации того как можно создавать обьекты в рамках сессии.
        // Например собирать статистику по пользователям
        if(httpSession.getAttribute("myID") == null){
            String uuid = UUID.randomUUID().toString();
            httpSession.setAttribute("myID", uuid);
            System.out.println("Generated UUID -> " + uuid);
        }
        model.addAttribute("uuid", httpSession.getAttribute("myID"));

        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login-error")        // чтобы пользователь попал на 404 пейдж
    public String loginError(Model mOdel){
        mOdel.addAttribute("loginError", true);
        return "login";
    }

}
