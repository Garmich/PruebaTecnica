package com.michael;

import com.michael.csvapp.application.cli.OrderCli;
import com.michael.starwars.domain.model.FilmResponse;
import com.michael.starwars.infra.adapter.StarWarsConsumerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource(value = { "classpath:application.properties" })
public class SpringApplication implements CommandLineRunner {
    private static Logger LOG = LoggerFactory.getLogger(SpringApplication.class);

    public static void main(final String[] args) {
        LOG.info("STARTING THE APPLICATION");
        org.springframework.boot.SpringApplication application = new org.springframework.boot.SpringApplication(SpringApplication.class);
        // uncomment to run just the console application
//        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
        LOG.info("APPLICATION FINISHED");
    }

//    @Autowired
//    public StarWarsConsumerAdapter filmConsumerAdapter;
    @Autowired
    public OrderCli orderController;
    @Autowired
    public ConfigurableApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");
        String path = args[0];
        orderController.importCsv(path);
//        uncomment to stop the context when execution is done
//        context.close();
    }
}
