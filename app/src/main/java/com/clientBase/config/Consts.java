package com.clientBase.config;


public class Consts {

	public static int TIME_OUT = 30000;

	public final static String URL = "http://192.168.43.203:8080/ShopService/";

	public final static String URL_IMAGE = "http://192.168.43.203:8080/ShopService/upload/";



	public static class APP {

		public static final String RegisterAction = "servlet/RegisterAction";
		public static final String RegAction = "servlet/RegAction";
		public static final String loginUser = "servlet/LoginServlet";
		public static final String ImAction = "servlet/ImAction";
		public static final String NewsAction = "servlet/NewsAction";
		public static final String MessageAction = "servlet/MessageAction";

	}

	public static class actionId {
		public static final int resultFlag = 1;
		public static final int resultCode = 2;
		public static final int resultMsg = 3;
		public static final int resultState = 4;
		public static final int resultFive = 5;
		public static final int resultSix = 6;
		public static final int resultSeven = 7;
	}
}
