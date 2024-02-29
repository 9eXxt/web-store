package listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import util.ConnectionUtil;

public class SessionFactoryListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        ConnectionUtil.getSessionFactory().close();
    }
}
