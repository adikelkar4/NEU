package com.finalproject.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.finalproject.pojo.Question;

public class QuestionDao extends DAO {

	public Question addQuestion(Question q) {
		// TODO Auto-generated method stub
		begin();
		getSession().save(q);
		commit();
		close();
		return q;
	}

	public List<Question> getAllQuestions() {
		begin();
		List<Question> allQuestions = getSession().createQuery("from Question").list();
		close();
		return allQuestions;
	}

	public Question getQuestionByName(String questionText) {
		try {
			Criteria crit = getSession().createCriteria(Question.class);
			crit.add(Restrictions.ilike("question", questionText, MatchMode.EXACT));
			crit.setMaxResults(1);
			Question quest = (Question) crit.uniqueResult();
			if (quest != null) {
				return quest;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Question getQuestionById(int id) {
		try {
			Criteria crit = getSession().createCriteria(Question.class);
			crit.add(Restrictions.eq("questionID", id));
			crit.setMaxResults(1);
			Question quest = (Question) crit.uniqueResult();
			if (quest != null) {
				return quest;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	// public List<Question> getSome() {
	// begin();
	//
	// Criteria crit = getSession().createCriteria(Question.class);
	//
	// Criteria crit2 = getSession().createCriteria(Question.class, "quest");
	//
	// crit2.add(Restrictions.sqlRestriction("quest.description like '%asd%'"));
	//
	// crit.add(Restrictions.eq("questionID", "2"));
	// crit.add(Restrictions.ilike("qDescription", "asd", MatchMode.ANYWHERE));
	//
	// Criterion equaslID = Restrictions.eq("questionID", 2);
	// Criterion isLike = Restrictions.eq("qDescription", "someval");
	//
	// Criteria ct = getSession().createCriteria(Question.class);
	// ct.add(Restrictions.ilike("qDescription", "asdasdas", MatchMode.ANYWHERE));
	// ct.addOrder(Order.asc("qDescription"));
	//
	//
	// LogicalExpression conditionsAND = Restrictions.and(equaslID, isLike);
	// LogicalExpression conditionsOR = Restrictions.or(equaslID, conditionsAND);
	//
	// crit.add(conditionsOR);
	//
	// List<String> list1 = (List<String>) crit.list();
	//
	//
	// List<Question> someQuestions = (List<Question>) crit.list();
	// return someQuestions;
	// }

	// public Question getAllQuestions(String email, String password) {
	// try {
	// begin();
	// Query q = getSession().createQuery("from User where email = :email AND
	// password = :password");
	// q.setString("email", email);
	// q.setString("password", password);
	// User user = (User) q.getSingleResult();
	// close();
	// return user;
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// return null;
	// }
	// }
}
