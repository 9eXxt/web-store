package util;


import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@UtilityClass
public class SessionUtil {
    private static final ThreadLocal<Session> SessionThreadLocal = new ThreadLocal<>();

    public static Session getSession() {
        return SessionThreadLocal.get();
    }

    public static Session openSession(SessionFactory sessionFactory) {
        Session currentSession = sessionFactory.openSession();
        SessionThreadLocal.set(currentSession);
        return currentSession;
    }

    public static void closeSession() {
        Session currentSession = SessionThreadLocal.get();
        if (currentSession != null) {
            currentSession.close();
        }
        SessionThreadLocal.remove();
    }
}
