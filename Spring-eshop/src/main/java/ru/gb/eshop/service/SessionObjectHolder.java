package ru.gb.eshop.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionObjectHolder {
    private long amountClicks = 0;
    // Клики нужны для примера того , что они считаются отдельно для каждой ессии и пользователя. Просто как пример.

    public SessionObjectHolder() {
        System.out.println("Session object created!");
    }

    public long getAmountClicks() {
        return amountClicks;
    }

    public void addClick(){
        amountClicks++;
    }
}
