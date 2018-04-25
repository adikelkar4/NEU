package com.finalproject.dao;

import org.hibernate.Query;
import com.finalproject.pojo.User;

public class UserDao extends DAO {

	public User getUser(String email, String password) {
		try {
			Query q = getSession().createQuery("from User where email = :email AND password = :password");
			q.setString("email", email);
			q.setString("password", password);
			User user = (User) q.getSingleResult();
			return user;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public User validateUser(int id, String token) {
		Query q = getSession().createQuery("from User where uniqueToken = :token AND userID = :userID");
		q.setParameter("token", token);
		q.setParameter("userID", id);
		User usr = (User) q.getSingleResult();
		if (usr != null) {
			// User exists
			begin();
			Query q1 = getSession().createQuery("update User set isActive = 1 where uniqueToken = :token AND userID = :userID");
			q1.setParameter("token", token);
			q1.setParameter("userID", id);
			q1.executeUpdate();
			commit();
			return usr;
		}
		return null;
	}

	public boolean userExists(String email) {
		try {
			Query q = getSession().createQuery("from User where email = :email");
			q.setParameter("email", email);
			User user = (User) q.getSingleResult();
			if (user == null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			close();
			e.printStackTrace();
			return false;
		}
	}

	public User adduser(User u) {
		// TODO Auto-generated method stub
		getSession().save(u);
		return u;
	}
}
