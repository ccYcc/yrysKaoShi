package com.ccc.test.components;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.ccc.test.service.interfaces.IUserService;
import com.ccc.test.utils.ListUtil;
import com.ccc.test.utils.PropertiesUtil;

public class MyServlet extends DispatcherServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void initStrategies(ApplicationContext context) {
		super.initStrategies(context);
		IUserService userService = context.getBean("userService",IUserService.class);
		String admins = PropertiesUtil.getString("administrators");
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

}
