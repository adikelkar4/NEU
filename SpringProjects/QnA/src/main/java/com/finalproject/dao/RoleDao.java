package com.finalproject.dao;

import java.util.List;

import org.hibernate.Query;

import com.finalproject.pojo.Role;



public class RoleDao extends DAO {
    public Role addMovie(Role m) throws Exception {
        begin();
        getSession().save(m);
        return m;
    }

    public List<Role> getMoviesFromTitle(String title) {
        begin();

        Query q = getSession().createQuery("from Movie WHERE LOWER(TITLE) LIKE :title ");
        q.setString("title", "%" + title.toLowerCase() + "%");
        List<Role> movies = q.list();
        commit();
        return movies;

    }

    public Role getMovieFromTitle(String title) {
        begin();
        Query q = getSession().createQuery("from Movie where LOWER(TITLE) = :title");
        q.setString("title", title);
        Role movie = (Role) q.uniqueResult();

        return movie;
    }

    public List<Role> getMoviesFromActor(String actor) {
        begin();

        Query q = getSession().createQuery("from Movie WHERE LOWER(ACTOR) LIKE :actor ");
        q.setString("actor", "%" + actor.toLowerCase() + "%");
        List<Role> movies = q.list();
        commit();
        return movies;

    }

    public List<Role> getMoviesFromActress(String actress) {
        begin();
        Query q = getSession().createQuery("from Movie WHERE LOWER(ACTRESS) LIKE :actress ");
        q.setString("actress", "%" + actress.toLowerCase() + "%");
        List<Role> movies = q.list();
        commit();
        return movies;

    }
}
