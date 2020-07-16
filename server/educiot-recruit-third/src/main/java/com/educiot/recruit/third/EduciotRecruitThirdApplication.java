package com.educiot.recruit.third;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EduciotRecruitThirdApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduciotRecruitThirdApplication.class, args);
	}
	/**
	 * 配置一个TomcatEmbeddedServletContainerFactory bean
	 * @return
	 */
	//http://100.168.2.207:8605/swagger-ui.html#/%E5%BE%AE%E4%BF%A1%E6%8E%A5%E5%8F%A3
//	@Bean
//	public EmbeddedServletContainerFactory servletContainerFactory() {
//		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//			@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint securityConstraint = new SecurityConstraint();
//				securityConstraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				securityConstraint.addCollection(collection);
//				context.addConstraint(securityConstraint);
//			}
//		};
//		tomcat.addAdditionalTomcatConnectors(httpConnector());
//		return tomcat;
//	}

	/**
	 * 让我们的应用支持HTTP是个好想法，但是需要重定向到HTTPS，
	 * 但是不能同时在application.properties中同时配置两个connector，
	 * 所以要以编程的方式配置HTTP connector，然后重定向到HTTPS connector
	 * @return Connector
	 */
//	@Bean
//	public Connector httpConnector() {
//		Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setScheme("http");
//		connector.setPort(8088);
//		connector.setSecure(false);
//		connector.setRedirectPort(8443);//端口
//		return connector;
//	}
}
