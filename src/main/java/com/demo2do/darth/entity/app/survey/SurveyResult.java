/**
 * 
 */
package com.demo2do.darth.entity.app.survey;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Downpour
 */
@Entity
@Table(name = "survey_result")
public class SurveyResult {

	@Id
	@GeneratedValue
	private Long id;
	
	private String nickName;
	
	private String mobile;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Question question;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Option option;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Survey survey;
	
	private Date surveyTime;
	
	/**
	 * 
	 */
	public SurveyResult() {
		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * @return the option
	 */
	public Option getOption() {
		return option;
	}

	/**
	 * @return the survey
	 */
	public Survey getSurvey() {
		return survey;
	}
	
	/**
	 * @return the surveyTime
	 */
	public Date getSurveyTime() {
		return surveyTime;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * @param option the option to set
	 */
	public void setOption(Option option) {
		this.option = option;
	}

	/**
	 * @param survey the survey to set
	 */
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	/**
	 * @param surveyTime the surveyTime to set
	 */
	public void setSurveyTime(Date surveyTime) {
		this.surveyTime = surveyTime;
	}
	
}
