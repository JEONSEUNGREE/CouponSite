package com.massive.couponapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.massive.couponcore.CouponCoreConfiguration;

@Import(CouponCoreConfiguration.class) // application에 클래스에 컴포넌트 스캔을 위해 import 필요
@SpringBootApplication
public class CouponApiApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application-core, application-api");

		SpringApplication.run(CouponApiApplication.class, args);
	}

}
