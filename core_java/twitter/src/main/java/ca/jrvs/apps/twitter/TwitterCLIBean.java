package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.HttpHelper;
import ca.jrvs.apps.twitter.dao.TwitterDAO;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwitterCLIBean {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(TwitterCLIBean.class);
        TwitterCLIApp app = context.getBean(TwitterCLIApp.class);
        try {
            app.run(args);
        } catch (IllegalArgumentException e) {
            System.out.println(TwitterCLIApp.USAGE_ERROR_MESSAGE + "\n" + e.getMessage());
        }
    }

    @Bean
    TwitterCLIApp twitterCLIApp(Controller controller) {
        return new TwitterCLIApp(controller);
    }

    @Bean
    Controller controller(Service service) {
        return new TwitterController(service);
    }

    @Bean
    Service service(CrdDao dao) {
        return new TwitterService(dao);
    }

    @Bean
    CrdDao crdDao(HttpHelper httpHelper) {
        return new TwitterDAO(httpHelper);
    }

    @Bean
    HttpHelper httpHelper() {

        return new TwitterHttpHelper(System.getenv("TWITTER_API_KEY"),
                System.getenv("TWITTER_API_SECRET"), System.getenv("TWITTER_ACCESS_TOKEN"),
                System.getenv("TWITTER_ACCESS_SECRET"));
    }
}
