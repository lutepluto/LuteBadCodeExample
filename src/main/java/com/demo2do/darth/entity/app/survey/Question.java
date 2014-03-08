/**
 * 
 */
package com.demo2do.darth.entity.app.survey;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Downpour
 */
@Entity
@Table(name = "survey_question")
public class Question {

	@Id
	@GeneratedValue
	private Long id;
	
	private String content;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Option> options;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Survey survey;
	
	/**
	 * The default constructor
	 */
	public Question() {
		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the options
	 */
	public List<Option> getOptions() {
		return options;
	}

	/**
	 * @return the survey
	 */
	public Survey getSurvey() {
		return survey;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<Option> options) {
		this.options = options;
	}

	/**
	 * @param survey the survey to set
	 */
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

}
