package com.finalproject.dao;

import org.hibernate.Query;
import com.finalproject.pojo.User;

public class UserDao extends DAO {

	public User getUser(String email, String password) {
		try {
			begin();
			Query q = getSession().createQuery("from User where email = :email AND password = :password");
			q.setString("email", email);
			q.setString("password", password);
			User user = (User) q.getSingleResult();
			close();
			return user;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public boolean userExists(String email) {
		try {
			begin();
			Query q = getSession().createQuery("from User where email = :email");
			q.setString("email", email);
			User user = (User) q.getSingleResult();
			if (user == null) {
				close();
				return false;
			} else {
				close();
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
		begin();
		getSession().save(u);
		return u;
	}
}
