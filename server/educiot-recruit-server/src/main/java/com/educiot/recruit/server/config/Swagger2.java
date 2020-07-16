package com.educiot.recruit.server.config;

import com.educiot.recruit.common.constant.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2相关配置
 * 
 * @author qgs
 * @date 2018年10月26日
 */
@EnableSwagger2
@Configuration
public class Swagger2 {
	@Value("${swagger.api.version}")
	private String apiVersion;

	@Bean
	public Docket createRestApi() {
		ParameterBuilder pb = new ParameterBuilder();
		pb.name(Constant.Sys.TOKEN_STR).description("Token").modelRef(new ModelRef("string")).parameterType("header")
				.required(false).defaultValue(Constant.Sys.DEFAULT_TOKEN).build();
		List<Parameter> par = new ArrayList<Parameter>();
		par.add(pb.build());//
		return new Docket(DocumentationType.SWAGGER_2).enable(true).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.educiot.recruit.server.controller")).paths(PathSelectors.any())
				.build().globalOperationParameters(par);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("育联网招生接口设计文档").version(apiVersion).build();
	}

	@Bean
	public Docket planApi() {
		ParameterBuilder pb = new ParameterBuilder();
		pb.name("FDToken").description("Token").modelRef(new ModelRef("string")).parameterType("header")
				.required(false).defaultValue(Constant.Sys.DEFAULT_TOKEN).build();
		List<Parameter> par = new ArrayList<Parameter>();
		par.add(pb.build());//
		return new Docket(DocumentationType.SWAGGER_2).groupName("招生计划相关API").
				enable(true).
				apiInfo(apiInfo()).
				select()
				.apis(RequestHandlerSelectors.basePackage("com.educiot.recruit.server.controller.plan")).paths(PathSelectors.any())
				.build().globalOperationParameters(par);
	}
}
