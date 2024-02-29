package dao;

import lombok.RequiredArgsConstructor;
import util.SessionUtil;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class CRUD<K, E> {
    private final Class<E> eClass;

    public void save(E entity) {
        var session = SessionUtil.getSession();
        session.persist(entity);
    }

    public List<E> findAll() {
        var session = SessionUtil.getSession();
        var criteria = session.getCriteriaBuilder().createQuery(eClass);
        criteria.from(eClass);
        return session.createQuery(criteria)
                .getResultList();
    }

    public Optional<E> findById(K ID) {
        var session = SessionUtil.getSession();
        return Optional.ofNullable(session.find(eClass, ID));
    }

    public void update(E entity) {
        var session = SessionUtil.getSession();
        session.merge(entity);
    }

    public void delete(K ID) {
        var session = SessionUtil.getSession();
        findById(ID).ifPresent(session::remove);
    }
}
