/**
 * 
 */
package com.demo2do.darth.entity.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo2do.darth.entity.protocol.material.Article;
import com.demo2do.darth.entity.protocol.material.Music;

/**
 * @author Downpour
 */
public class ResponseMessage extends Message {
	
	private String content;

	private Music music;

	private List<Article> articles;

	/**
	 * Private constructor from requestMessage
	 * 
	 * @param requestMessage
	 */
	private ResponseMessage(RequestMessage requestMessage) {
		this.from = requestMessage.getTo();
		this.to = requestMessage.getFrom();
		this.createTime = new Date().getTime();
	}

	/**
	 * The constructor of text message
	 * 
	 * @param content
	 */
	public ResponseMessage(RequestMessage requestMessage, String content) {
		this(requestMessage);
		this.type = MessageType.TEXT.getKey();
		this.content = content;
	}

	/**
	 * The constructor of music message
	 * 
	 * @param requestMessage
	 * @param music
	 */
	public ResponseMessage(RequestMessage requestMessage, Music music) {
		this(requestMessage);
		this.type = MessageType.MUSIC.getKey();
		this.music = music;
	}

	/**
	 * The constructor of single news message
	 * 
	 * @param requestMessage
	 * @param article
	 */
	public ResponseMessage(RequestMessage requestMessage, Article article) {
		this(requestMessage);
		this.type = MessageType.NEWS.getKey();
		this.articles = new ArrayList<Article>(1);
		this.articles.add(article);
	}

	/**
	 * The constructor of multiple news message
	 * 
	 * @param requestMessage
	 * @param articles
	 */
	public ResponseMessage(RequestMessage requestMessage, List<Article> articles) {
		this(requestMessage);
		this.type = MessageType.NEWS.getKey();
		this.articles = new ArrayList<Article>();
		this.articles.addAll(articles);
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the music
	 */
	public Music getMusic() {
		return music;
	}

	/**
	 * @return the articles
	 */
	public List<Article> getArticles() {
		return articles;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param music
	 *            the music to set
	 */
	public void setMusic(Music music) {
		this.music = music;
	}

	/**
	 * @param articles
	 *            the articles to set
	 */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}
