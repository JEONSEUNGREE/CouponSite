package com.massive.couponcore.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.massive.couponcore.model.Coupon;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
}
