
package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



import ru.kata.spring.boot_security.demo.model.Users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class DAOImpl implements DAO {

    @PersistenceContext()
    private EntityManager entityManager;

    @Override
    public Users getUserById(Long id) {
        return entityManager
                .createQuery("SELECT u FROM Users u WHERE u.id = :id", Users.class)
                .setParameter("id", id)
                .getResultList().stream().findAny().orElse(null);
    }

    @Override
    public Users getUserByUserName(String userName) {
        return entityManager
                .createQuery("SELECT u FROM Users u join fetch u.roles WHERE u.name = :userName", Users.class)
                .setParameter("userName", userName)
                .getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<Users> getAllUsers() {
        return entityManager.createQuery("FROM Users", Users.class).getResultList();
    }

    @Override
    public void saveUser(Users user) {
        entityManager.persist(user);
    }

    @Override
    public Users update(Users user) {
        return entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        if(getUserById(id) != null) {
            entityManager.remove(getUserById(id));
        }
    }
}
