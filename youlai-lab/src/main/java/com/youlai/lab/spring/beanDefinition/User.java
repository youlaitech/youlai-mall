package com.youlai.lab.spring.beanDefinition;

/**
 *
 *  需要注册的bean模型
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/5 0005 12:20
 */
public class User {
	private String userName;
	private String email;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User{" +
				"userName='" + userName + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
