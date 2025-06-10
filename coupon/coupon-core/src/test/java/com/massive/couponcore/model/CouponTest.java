package com.massive.couponcore.model;

import static com.massive.couponcore.exception.ErrorCode.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.massive.couponcore.exception.CouponIssueException;

class CouponTest {

	@Test
	@DisplayName("발급 수량이 한개 남은 경우 True 반환")
	void availableIssueQuantity_1() {
		// given
		Coupon coupon = Coupon.builder()
			.totalQuantity(100)
			.issuedQuantity(99)
			.build();

		// when
		boolean result = coupon.availableIssueQuantity();

		// then
		Assertions.assertTrue(result);

	}

	@Test
	@DisplayName("발급 수량이 남지 않은 경우 False 반환")
	void availableIssueQuantity_2() {
		// given
		Coupon coupon = Coupon.builder()
			.totalQuantity(100)
			.issuedQuantity(100)
			.build();

		// when
		boolean result = coupon.availableIssueQuantity();

		// then
		Assertions.assertFalse(result);
	}

	@Test
	@DisplayName("최대 발급 수량이 설정 되지않은 경우 True 반환")
	void availableIssueQuantity_3() {
		// given
		Coupon coupon = Coupon.builder()
			.totalQuantity(null)
			.issuedQuantity(100)
			.build();

		// when
		boolean result = coupon.availableIssueQuantity();

		// then
		Assertions.assertTrue(result);
	}


	@Test
	@DisplayName("발급 기간 시작 전에 쿠폰 발급시 False 반환")
	void availableIssueQuantity_5() {
		// given
		Coupon coupon = Coupon.builder()
			.dateIssueStart(LocalDateTime.now().plusDays(1))
			.dateIssueEnd(LocalDateTime.now().plusDays(2))
			.build();

		// when
		boolean result = coupon.availableIssueDate();

		// then
		Assertions.assertFalse(result);
	}

	@Test
	@DisplayName("발행 기간 해당 시 쿠폰 발급시 True 반환")
	void availableIssueQuantity_6() {
		// given
		Coupon coupon = Coupon.builder()
			.dateIssueStart(LocalDateTime.now().minusDays(1))
			.dateIssueEnd(LocalDateTime.now().plusDays(1))
			.build();

		// when
		boolean result = coupon.availableIssueDate();

		// then
		Assertions.assertTrue(result);
	}

	@Test
	@DisplayName("발행 종료 시 쿠폰 발급시 False 반환")
	void availableIssueQuantity_7() {
		// given
		Coupon coupon = Coupon.builder()
			.dateIssueStart(LocalDateTime.now().minusDays(1))
			.dateIssueEnd(LocalDateTime.now().minusHours(1))
			.build();

		// when
		boolean result = coupon.availableIssueDate();

		// then
		Assertions.assertFalse(result);
	}

	@Test
	@DisplayName("발급 수량을 초과하면 예외를 반환한다")
	void issue_2() {
		// given
		Coupon coupon = Coupon.builder()
			.totalQuantity(100)
			.issuedQuantity(100)
			.dateIssueStart(LocalDateTime.now().minusDays(1))
			.dateIssueEnd(LocalDateTime.now().plusDays(2))
			.build();
		// when & then
		CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, coupon::issue);
		Assertions.assertEquals(exception.getErrorCode(), INVALID_COUPON_ISSUE_QUANTITY);
	}

	@Test
	@DisplayName("발급 기간이 아니면 예외를 반환한다")
	void issue_3() {
		// given
		Coupon coupon = Coupon.builder()
			.totalQuantity(100)
			.issuedQuantity(99)
			.dateIssueStart(LocalDateTime.now().plusDays(1))
			.dateIssueEnd(LocalDateTime.now().plusDays(2))
			.build();
		// when & then
		CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, coupon::issue);
		Assertions.assertEquals(exception.getErrorCode(), INVALID_COUPON_ISSUE_DATE);
	}

	@Test
	@DisplayName("발급 기간이 종료되면 true를 반환한다")
	void isIssueComplete_1() {
		// given
		Coupon coupon = Coupon.builder()
			.dateIssueStart(LocalDateTime.now().minusDays(2))
			.dateIssueEnd(LocalDateTime.now().minusDays(1))
			.totalQuantity(100)
			.issuedQuantity(0)
			.build();
		// when
		boolean result = coupon.isIssueComplete();
		// then
		Assertions.assertTrue(result);
	}

	@Test
	@DisplayName("잔여 발급 가능 수량이 없다면 true를 반환한다")
	void isIssueComplete_2() {
		// given
		Coupon coupon = Coupon.builder()
			.totalQuantity(100)
			.issuedQuantity(100)
			.dateIssueStart(LocalDateTime.now().minusDays(1))
			.dateIssueEnd(LocalDateTime.now().plusDays(2))
			.build();
		// when
		boolean result = coupon.isIssueComplete();
		// then
		Assertions.assertTrue(result);
	}

	@Test
	@DisplayName("발급 기한과 수량이 유효하면 false를 반환한다")
	void isIssueComplete_3() {
		// given
		Coupon coupon = Coupon.builder()
			.dateIssueStart(LocalDateTime.now().minusDays(1))
			.dateIssueEnd(LocalDateTime.now().plusDays(2))
			.totalQuantity(100)
			.issuedQuantity(0)
			.build();
		// when
		boolean result = coupon.isIssueComplete();
		// then
		Assertions.assertFalse(result);
	}
}