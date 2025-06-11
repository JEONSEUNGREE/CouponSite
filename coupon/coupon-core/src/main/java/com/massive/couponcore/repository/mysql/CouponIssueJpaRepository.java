package com.massive.couponcore.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.massive.couponcore.model.CouponIssue;

public interface CouponIssueJpaRepository extends JpaRepository<CouponIssue, Long> {
}
