package com.simtopup.user.service.impl.thaitopup;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serverlite.core.service.impl.AbstractService;
import com.serverlite.core.transaction.TransactionManager;
import com.simtopup.common.util.HttpClientUtil;
import com.simtopup.user.dao.TopUpOrderDao;
import com.simtopup.user.entity.ApiConfig;
import com.simtopup.user.entity.TopUpOrder;
import com.simtopup.user.service.ApiConfigService;
import com.simtopup.user.service.TopUpService;

public class ThaiTopUpServiceImpl extends AbstractService implements TopUpService{
	
	private static final Logger logger = LoggerFactory.getLogger(ThaiTopUpServiceImpl.class);
	
	public static final String NETWORK_OPERATOR_AIS="1";
	public static final String NETWORK_OPERATOR_DTAC="2";
	public static final String NETWORK_OPERATOR_TRUE="3";
	
	private ApiConfigService apiConfigService;
	
	private TopUpOrderDao topUpOrderDao;

	public ThaiTopUpServiceImpl(TransactionManager transactionManager , 
								ApiConfigService apiConfigService,
								TopUpOrderDao topUpOrderDao) {
		super(transactionManager);
		this.apiConfigService=apiConfigService;
		this.topUpOrderDao=topUpOrderDao;
	}
	
	@Override
	public String createToUpId(TopUpOrder topUpOrder) {
		
		ApiConfig apiConfig = apiConfigService.findApiConfigById(ApiConfig.THAI_TOP_UP_API_ID);
		
		Map<String, String > formToken = new HashMap<>();
		formToken.put("number",topUpOrder.getMobileNumber());
		ThaiTopUpResponse response = HttpClientUtil.httpPostForm(apiConfig.getApiAddress()+"get-topup-token", formToken,ThaiTopUpResponse.class,apiConfig.getKey1(),"");
		
		//失败处理
		if(response==null || response.getUid()==null || response.getError()!=null ){
			logger.error("get token error:"+response);
			throw new RuntimeException();
		}
		
		transactionManager.doInTransaction(()->{
			topUpOrderDao.updateTopUpTransactionId(topUpOrder.getId(), response.getUid());
			return null;
		});
		
		return response.getUid();
	}

	@Override
	public void topUp(TopUpOrder topUpOrder) {
		
		ApiConfig apiConfig = apiConfigService.findApiConfigById(ApiConfig.THAI_TOP_UP_API_ID);
		
		transactionManager.doInTransaction(()->{
			
			TopUpOrder topUpOrderf = topUpOrderDao.findTopUpOrderById(topUpOrder.getId(), true );
			
			if(topUpOrderf==null){
				logger.error("cannot find topUpOrder order id:"+topUpOrder.getId());
				throw new RuntimeException();
			}
			
			if( topUpOrderf.getStatus()==TopUpOrder.TOPUPED){
				logger.warn("topUpOrder order id:"+topUpOrder.getId()+" status is topuped");
				return null;
			}
			
			if( topUpOrderf.getStatus()!=TopUpOrder.PAID){
				logger.warn("topUpOrder order id:"+topUpOrder.getId()+" status is not paid");
				return null;
			}
		
	
			//先检查状态
			ThaiTopUpResponse response=HttpClientUtil.httpGet(apiConfig.getApiAddress()+"retrieve-topup" , ThaiTopUpResponse.class,topUpOrderf.getTopUpTransactionId(),"");
			//失败处理
			if(response==null || response.getError()!=null){
				logger.error("top up error:"+response);
				throw new RuntimeException();		
			}
			if(response.getStatus()!=null && response.getStatus().equals("SUCCESS")){
				topUpOrderDao.updateOrderStatus( topUpOrder.getId() , TopUpOrder.TOPUPED );
				logger.warn("topUpOrder order id:"+topUpOrder.getId()+" remote status is successed");
				return null;
			}
			
			
			String network = null;
			switch (topUpOrder.getNetworkOperator()) {
				case TopUpOrder.NETWORK_OPERATOR_THAI_AIS:
					network=NETWORK_OPERATOR_AIS;
					break;
				case TopUpOrder.NETWORK_OPERATOR_THAI_DTAC:
					network=NETWORK_OPERATOR_DTAC;		
					break;
				case TopUpOrder.NETWORK_OPERATOR_THAI_TRUE:
					network=NETWORK_OPERATOR_TRUE;
					break;
			default:
				break;
			}
			
			Map<String, String > form = new HashMap<>();
			form.put("amount", String.valueOf(topUpOrder.getTopUpAmount().intValue()));
			form.put("network", network);
			response=HttpClientUtil.httpPostForm(apiConfig.getApiAddress()+"topup", form, ThaiTopUpResponse.class,topUpOrderf.getTopUpTransactionId(),"");
			
			//失败处理
			if(response==null || response.getError()!=null){
				logger.error("top up error:"+response);
				throw new RuntimeException();		
			}
			
			//充值成功，更新状态
			topUpOrderDao.updateTopUpOrderIdAndStatus(topUpOrder.getId(), response.getOrder_id() , TopUpOrder.TOPUPED);
			
			return null;
		});
		
	}

	@Override
	public BigDecimal findMerchantBalance(Integer merchantId) {
		// TODO Auto-generated method stub
		return null;
	}

}
