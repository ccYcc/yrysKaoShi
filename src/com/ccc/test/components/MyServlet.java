package com.ccc.test.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.ListUtil;

public class MyServlet extends DispatcherServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void initStrategies(ApplicationContext context) {
		super.initStrategies(context);
		IUserService userService = context.getBean("userService",IUserService.class);
		Properties yrysProperties = new Properties();
		InputStream is = getClass().getResourceAsStream("/config/yrys.properties");
		try {
			if ( is == null ){
				System.out.println("配置文件路径有误");
			} else {
				yrysProperties.load(is);
				String admins = yrysProperties.getProperty("administrators");
				List<String> adminlist = ListUtil.stringsToListSplitBy(admins, ",");
				if ( ListUtil.isNotEmpty(adminlist) ){
					for ( String admin : adminlist ){
						String[] tmp = admin.split(":");
						if ( tmp == null || tmp.length != 2 ){
							System.out.println("管理员配置有误");
						} else {
							String password = tmp[1];
							String name = tmp[0];
							String type = "管理员";
							try {
								Serializable ret = userService.register(name, password, password, type);
								System.out.println(name+":"+ret.toString());
							} catch (Exception e) {
								System.out.println("管理员注册失败");
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("加载软件配置失败");
			e.printStackTrace();
		}
	}

}
