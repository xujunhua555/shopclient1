package com.clientBase.model;

import java.io.Serializable;

/**
 * 
 * @author wangxuan
 * 
 */
public class UserModel implements Serializable{


	/**
	 * uid : 92
	 * utime : 2019-11-15 10:15
	 * upswd : 123456
	 * uphone : 15249241001
	 * uname : pony
	 * uImg : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573793677517&di=8f136b19af321b5b33e2b47c03e54df6&imgtype=0&src=http%3A%2F%2Fimg01.jituwang.com%2F170504%2F256966-1F504092T158.jpg
	 * ucard : 123456
	 * utype : 1
	 * utoken : 1IbkIPHZ4ql+337R4ala/CvwBvuqr97RjQi5uLqItZQHbQeOQtvD7mGGro2GXplL6A4Ffw8eCIH4qW5FoOzRkw==
	 */

	private int uid;
	private String utime;
	private String upswd;
	private String uphone;
	private String uname;
	private String uImg;
	private String ucard;
	private String utype;
	private String utoken;

	private String udpName;
	private String udpId;
	private String uno;
	private String uflag;

	public String getUflag() {
		return uflag;
	}

	public void setUflag(String uflag) {
		this.uflag = uflag;
	}

	public String getuImg() {
		return uImg;
	}

	public void setuImg(String uImg) {
		this.uImg = uImg;
	}

	public String getUdpName() {
		return udpName;
	}

	public void setUdpName(String udpName) {
		this.udpName = udpName;
	}

	public String getUdpId() {
		return udpId;
	}

	public void setUdpId(String udpId) {
		this.udpId = udpId;
	}

	public String getUno() {
		return uno;
	}

	public void setUno(String uno) {
		this.uno = uno;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUtime() {
		return utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getUpswd() {
		return upswd;
	}

	public void setUpswd(String upswd) {
		this.upswd = upswd;
	}

	public String getUphone() {
		return uphone;
	}

	public void setUphone(String uphone) {
		this.uphone = uphone;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUImg() {
		return uImg;
	}

	public void setUImg(String uImg) {
		this.uImg = uImg;
	}

	public String getUcard() {
		return ucard;
	}

	public void setUcard(String ucard) {
		this.ucard = ucard;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	public String getUtoken() {
		return utoken;
	}

	public void setUtoken(String utoken) {
		this.utoken = utoken;
	}
}
