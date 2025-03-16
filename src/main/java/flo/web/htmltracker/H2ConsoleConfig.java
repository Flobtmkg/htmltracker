package flo.web.htmltracker;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class H2ConsoleConfig {
	
    private static Logger log = LoggerFactory.getLogger(H2ConsoleConfig.class);
    
    private Server webServer;

    @Value("${h2-server.port}")
    private Integer h2ConsolePort;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws SQLException {
        log.info("starting h2 console at port "+ h2ConsolePort);
        this.webServer = Server.createWebServer("-webPort", h2ConsolePort.toString(), "-tcpAllowOthers").start();
        log.info("H2-console ==> " + webServer.getURL());
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("stopping h2 console at port "+h2ConsolePort);
        this.webServer.stop();
    }

}
