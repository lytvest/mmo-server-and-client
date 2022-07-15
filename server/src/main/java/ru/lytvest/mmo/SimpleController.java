package ru.lytvest.mmo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class SimpleController {

    WorldController worldController = new WorldController();

    @PostMapping("/")
    public String index(Model model){
        model.addAttribute("world", worldController.toWrite());
        return "jsonTemplate";
    }
    @Scheduled(fixedRate = 100)
    @Async
    public void updateWorld(){
        worldController.update();
    }


}
