/**
 * 
 */
package com.demo2do.darth.entity.stat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.demo2do.core.utils.DateUtils;

/**
 * @author Downpour
 */
public class DateRange {

	private Date startDate;
	
	private Date endDate;
	
	/**
	 * The default constructor
	 */
	public DateRange() {
		
	}
	
	/**
	 * The constructor using startDate and endDate
	 * 
	 * @param startDate
	 * @param endDate
	 */
	public DateRange(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasValue() {
		return this.startDate != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public DateRange last7days() {
		this.endDate = new Date();
		this.startDate = DateUtils.addWeeks(this.endDate, -1);
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getIntervalDays() {
		return DateUtils.compareTwoDatesOnDay(this.endDate, this.startDate);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getIntervalDates() {
		List<Date> days = DateUtils.getIntervalDays(this.startDate, this.endDate);
		List<String> results = new ArrayList<String>();
		for(Date day : days) {
			results.add("'" + DateUtils.format(day, "yyyy/MM/dd") + "'");
		}
		return StringUtils.join(results, ",");
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return DateUtils.format(this.startDate, "yyyy/MM/dd") + " ~ " + DateUtils.format(this.endDate, "yyyy/MM/dd");
	}
}
