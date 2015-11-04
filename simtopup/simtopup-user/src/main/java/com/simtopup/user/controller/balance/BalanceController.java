package com.simtopup.user.controller.balance;

import com.simtopup.user.boundary.Request;
import com.simtopup.user.boundary.Response;
import com.simtopup.user.service.BalanceService;

public class BalanceController {

	private BalanceService balanceService;
	
	public BalanceController( BalanceService balanceService ) {
		this.balanceService=balanceService;
	}
	
	public void findBalanceByUserId( Request request,Response response ){
		response.setMessage(balanceService.findBalanceByUserId(request.getSession().getUserId()).toString());
	}
}
