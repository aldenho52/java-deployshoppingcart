package com.lambdaschool.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Starting class for testing
 */
@EnableWebMvc
// @EnableJpaAuditing
@SpringBootApplication
public class ShoppingCartApplicationTest
{
    /**
     * Main method to start the application.
     *
     * @param args Not used in this application.
     */
    public static void main(String[] args)
    {
        SpringApplication.run(ShoppingCartApplicationTest.class,
            args);
    }
}




//public class ShoppingCartApplication
//{
//    private static final Logger logger = LoggerFactory.getLogger(com.lambdaschool.shoppingcart.ShoppingCartApplication.class);
//
//    private static boolean stop = false;
//
//    @Autowired
//    private static Environment env;
//
//    private static void checkEnvironmentVariable(String envvar)
//    {
//        if (System.getenv(envvar) == null)
//        {
//            logger.error("Environment Variable " + envvar + " missing");
//            stop = true;
//        }
//    }
//
//    public static void main(String[] args)
//    {
//        checkEnvironmentVariable("OAUTHCLIENTID");
//        checkEnvironmentVariable("OAUTHCLIENTSECRET");
//
//        if (!stop)
//        {
//            ApplicationContext ctx = SpringApplication.run(com.lambdaschool.shoppingcart.ShoppingCartApplication.class,
//                args);
//
//            DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
//            dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
//        }
//    }
//
//}
