package com.finalproject.dao;

import java.util.ArrayList;
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
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.finalproject.controller.QnA.HomeController;
import com.finalproject.pojo.Answer;
import com.finalproject.pojo.Question;
import com.finalproject.pojo.User;

public class AnswerDao extends DAO {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	public void deleteAnswer(int questionId, int userId) {
		begin();
		String hql = "delete from Answer where questionID= :questionId AND userID= :userId";
		Query query = getSession().createQuery(hql);
		query.setParameter("questionId", questionId);
		query.setParameter("userID", userId);
		commit();
		close();
	}
	
	public Answer saveAnswer(String answerContent, Question q, User u) {
		Answer a = new Answer();
		a.setAnswerContent(answerContent);
		a.setQuestion(q);
		a.setUser(u);
		try {
			begin();
			getSession().save(a);
			commit();
			close();
			return a;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public ArrayList<Answer> getAnswersByQuestion(Question q) {
		int qId = q.getQuestionID();
		logger.info("question id here " + qId);
		Query query = getSession().createQuery("from Answer where questionID = ? order by answerDateTime DESC");
		query.setInteger(0, qId);
		logger.info(String.valueOf(query));
		query.setComment("HQL " + query);

		// Criteria crit = getSession().createCriteria(Answer.class);
		// crit.add(Restrictions.eq("questionID", qId));
		ArrayList<Answer> answerList = (ArrayList<Answer>) query.list();
		return answerList;
	}

	// public Answer addQuestion(Question q, Answer a) {
	// // TODO Auto-generated method stub
	// begin();
	// getSession().save(a);
	// commit();
	// close();
	// return a;
	// }

	// public List<Question> getAllQuestions() {
	// begin();
	// List<Question> allQuestions = getSession().createQuery("from
	// Question").list();
	// close();
	// return allQuestions;
	// }
	//
	// public Question getQuestionByName(String questionText) {
	// try {
	// Criteria crit = getSession().createCriteria(Question.class);
	// crit.add(Restrictions.ilike("question", questionText, MatchMode.EXACT));
	// crit.setMaxResults(1);
	// Question quest = (Question) crit.uniqueResult();
	// if (quest != null) {
	// return quest;
	// } else {
	// return null;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

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
