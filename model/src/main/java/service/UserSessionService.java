package service;

import dao.UserSessionDao;
import dto.UserSessionCreateDto;
import dto.UserSessionReadDto;
import entity.UserSession;
import lombok.AllArgsConstructor;
import mapper.UserSessionMapper;
import mapper.UserSessionReadMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.SessionUtil;

import java.util.Optional;

@AllArgsConstructor
public class UserSessionService {
    private final UserSessionDao userSessionDao;
    private final UserSessionMapper userSessionMapper;
    private final UserSessionReadMapper userSessionReadMapper;
    private final SessionFactory sessionFactory;

    public void create(UserSessionCreateDto userSessionDto) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = SessionUtil.openSession(sessionFactory);
            transaction = session.beginTransaction();

            UserSession userSession = userSessionMapper.mapFrom(userSessionDto);
            userSessionDao.save(userSession);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                SessionUtil.closeSession();
            }
        }
    }

    public Optional<UserSessionReadDto> findToken(Integer customer_id) {
        Session session = null;
        try {
            session = SessionUtil.openSession(sessionFactory);

            return userSessionDao.findToken(customer_id)
                    .map(userSessionReadMapper::mapFrom);
        } finally {
            if (session != null) {
                SessionUtil.closeSession();
            }
        }

    }
}
