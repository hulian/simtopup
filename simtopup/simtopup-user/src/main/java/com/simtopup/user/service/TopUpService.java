package com.simtopup.user.service;

import java.math.BigDecimal;
import com.simtopup.user.entity.TopUpOrder;

public interface TopUpService {
	
	String createToUpId( TopUpOrder topUpOrder );

	void topUp( TopUpOrder topUpOrder );
	
	BigDecimal findMerchantBalance( Integer merchantId );
	
}
