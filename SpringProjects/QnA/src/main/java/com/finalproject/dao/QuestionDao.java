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
		return q;
	}

	public List<Question> getAllQuestions() {
		List<Question> allQuestions = getSession().createQuery("from Question order by postedDate desc").list();
		return allQuestions;
	}

	public Question getQuestionByName(String questionText) {
		try {
			Criteria crit = getSession().createCriteria(Question.class);
			crit.add(Restrictions.like("questionEncoded", questionText, MatchMode.EXACT));
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
}
