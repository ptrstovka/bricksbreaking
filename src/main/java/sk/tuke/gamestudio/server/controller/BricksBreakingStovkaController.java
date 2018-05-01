package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.game.bricksbreaking.stovka.webui.WebUI;
import sk.tuke.gamestudio.server.service.ScoreService;

//http://localhost:8080/bricksbreaking-stovka
@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class BricksBreakingStovkaController {
    private WebUI webUI = new WebUI();

    @Autowired
    private ScoreService scoreService;

    @RequestMapping("/bricksbreaking-stovka")
    public String bricksbreaking(@RequestParam(value = "command", required = false) String command,
                                 @RequestParam(value = "row", required = false) String row,
                                 @RequestParam(value = "column", required = false) String column, Model model) {
        webUI.processCommand(command, row, column);
        model.addAttribute("webUI", webUI);

        return "bricksbreaking-stovka";
    }
}
