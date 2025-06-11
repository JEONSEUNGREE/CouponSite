package com.massive.couponapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.massive.couponapi.dto.CouponIssueRequestDto;
import com.massive.couponapi.dto.CouponIssueResponseDto;
import com.massive.couponapi.service.CouponIssueRequestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CouponIssueController {

	private final CouponIssueRequestService couponIssueRequestService;

	@PostMapping("/v1/issue")
	public CouponIssueResponseDto issueV1(@RequestBody CouponIssueRequestDto body) {
		couponIssueRequestService.issueRequestV1(body);
		return new CouponIssueResponseDto(true, null);
	}

}
