package com.simtopup.user.service;

import java.math.BigDecimal;

public interface BalanceService {
	BigDecimal findBalanceByUserId( Integer userId );
}
