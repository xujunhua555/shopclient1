package com.clientBase.model;

import java.io.Serializable;

public class ApplyModel implements Serializable{
	
    	
	private String applyId;
	private String applyUserId;
	private String applyUserName;
	private String applyPlanId;
	private String applyPlanName;
	private String applyTime;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getApplyPlanId() {
		return applyPlanId;
	}

	public void setApplyPlanId(String applyPlanId) {
		this.applyPlanId = applyPlanId;
	}

	public String getApplyPlanName() {
		return applyPlanName;
	}

	public void setApplyPlanName(String applyPlanName) {
		this.applyPlanName = applyPlanName;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
}
