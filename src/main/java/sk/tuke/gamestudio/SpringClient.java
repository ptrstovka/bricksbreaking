package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.server.service.*;

@Configuration
@SpringBootApplication
public class SpringClient {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(false).run(args);
    }

    @Bean
    public CommandLineRunner runner(sk.tuke.gamestudio.game.bricksbreaking.stovka.ui.console.ConsoleMenu consoleMenu) {
        return args -> consoleMenu.start();
    }

    @Bean
    public sk.tuke.gamestudio.game.bricksbreaking.stovka.ui.console.ConsoleMenu
        bricksBreakingStovkaConsoleMenu(sk.tuke.gamestudio.game.bricksbreaking.stovka.di.IoC container) {
        return new sk.tuke.gamestudio.game.bricksbreaking.stovka.ui.console.ConsoleMenu(container);
    }

    @Bean
    public sk.tuke.gamestudio.game.bricksbreaking.stovka.di.IoC
        bricksBreakingStovkaContainer() {
        return new sk.tuke.gamestudio.game.bricksbreaking.stovka.di.IoC();
    }

    @Bean
    public ScoreService scoreService() {
//        return new ScoreServiceJPA();
        return new ScoreServiceRestClient();
    }

    @Bean
    public RatingService ratingService() {
//        return new RatingServiceJPA();
        return new RatingServiceRestClient();
    }

    @Bean
    public CommentService commentService() {
//        return new CommentServiceJPA();
        return new CommentServiceRestClient();
    }

}
