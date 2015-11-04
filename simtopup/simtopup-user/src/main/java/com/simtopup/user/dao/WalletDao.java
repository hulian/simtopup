package com.simtopup.user.dao;

import java.math.BigDecimal;

public interface WalletDao {
	
	void createWallet( Integer userId );
	void increaseBalance( Integer userId , BigDecimal amount );
	void decreaseBalance( Integer userId , BigDecimal amount );
	BigDecimal findBalanceByUserId( Integer userId );
}
