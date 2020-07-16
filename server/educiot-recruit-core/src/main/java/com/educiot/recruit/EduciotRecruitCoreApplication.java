package com.educiot.recruit;

import com.educiot.recruit.common.api.APIRequest;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.util.JSONRedisUtil;
import com.educiot.recruit.data.common.util.ValidateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Administrator
 */
@SpringBootApplication
public class EduciotRecruitCoreApplication {

	public static void main(String[] args) throws MyExcption {
		SpringApplication.run(EduciotRecruitCoreApplication.class, args);
		APIRequest.apiUrl = "http://47.112.129.244:8081/recruit";
//		JSONRedisUtil.main(null);
		ValidateUtil.validateSemester(1L);
	}

}
