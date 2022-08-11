package com.idg.idgcore.coe.app.service.urn.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;


import com.idg.idgcore.coe.app.service.urn.constant.*;
import com.idg.idgcore.coe.app.service.urn.dao.*;
import com.idg.idgcore.coe.common.*;
import com.idg.idgcore.coe.exception.*;
import org.springframework.stereotype.Service;

import com.idg.idgcore.infra.cache.redis.RedisCache;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UrnGeneratorService implements IUrnGeneratorService {

	private static final String CLASS_NAME = "UrnGeneratorServiceImpl.";
	private static final String ENTER_STRING = "Entered into ";
	private static final String EXIT_STRING = "Exited from ";


	public String generateTransactionUrn(SessionContext sessionContext) throws
			UrnGeneratorException {
		log.info(ENTER_STRING + CLASS_NAME + "generateTransactionUrn()");
		HashMap<String, String> resultMap = null;
		String returnValueUrn = null;
		String generatedKeyForUrn = null;
		String paramError = null;
		
		try {
			
			resultMap = new HashMap<>();
			if(sessionContext.getEngineCode().isEmpty() || sessionContext.getEngineCode() == null)
				sessionContext.setEngineCode("IDG");

			if(sessionContext.getScreenCode().isEmpty() || sessionContext.getScreenCode() == null)
				sessionContext.setScreenCode("CORE");

			paramError = validateParams(sessionContext.getBankCode(), UrnGeneratorConstants.BANKCODE_WIDTH,
					sessionContext.getEngineCode(), UrnGeneratorConstants.ENGINECODE_WIDTH,
					sessionContext.getScreenCode(), UrnGeneratorConstants.SCREENCODE_WIDTH);
			
			if (paramError.equalsIgnoreCase(UrnGeneratorConstants.SUCCESS_MSG)) {

				generatedKeyForUrn = generateKeyForUrnStoring(sessionContext.getBankCode(),
						sessionContext.getEngineCode(),sessionContext.getScreenCode());

				returnValueUrn = new UrnGeneratorDaoImpl().getNewUrnForTransaction(sessionContext.getBankCode(),sessionContext.getEngineCode(),sessionContext.getScreenCode(), generatedKeyForUrn);
				resultMap.put("urnNo", returnValueUrn);
				
				RedisCache.storeDataInCacheforUrn(generatedKeyForUrn, getCacheExpiryTimeForUrnKey(), resultMap);
				
				}else {

				returnValueUrn =  paramError;

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new UrnGeneratorException("Exception while fetching the generateTransactionUrn()", e);
		}
		log.info(ENTER_STRING + CLASS_NAME + "generateTransactionUrn()");
		return returnValueUrn;
	}

	public String validateParams(String bankCode, int bankCodeSize, String engineCode, int engineCodeSize,
			String screenCode, int screenCodesize) throws UrnGeneratorException {
		log.info(ENTER_STRING + CLASS_NAME + "validateParams()");
		String errorMsg = UrnGeneratorConstants.SUCCESS_MSG;

		if (bankCode.length() != bankCodeSize) {
			errorMsg = UrnGeneratorConstants.ERROR_MSG_BANK_CODE;
		}

		if (engineCode.length() <= engineCodeSize) {
			errorMsg = UrnGeneratorConstants.SUCCESS_MSG;
		}else{
			errorMsg = UrnGeneratorConstants.ERROR_MSG_ENGINE_CODE;
		}

		if (screenCode.length() <= screenCodesize) {
			errorMsg = UrnGeneratorConstants.SUCCESS_MSG;
		}else{
			errorMsg = UrnGeneratorConstants.ERROR_MSG_SCREEN_CODE;
		}

		log.info(EXIT_STRING + CLASS_NAME + "validateParams()");
		return errorMsg;
	}

	public static int getCacheExpiryTimeForUrnKey() throws UrnGeneratorException {
		log.info(ENTER_STRING + CLASS_NAME + "getCacheExpiryTimeForUrnKey()");

		String systemTime = null;
		final String digitalClockConstant = "23:59:59";

		try {

			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			systemTime = formatter.format(date);

		} catch (Exception e) {

			throw new UrnGeneratorException("Error while getting cache expiry time for URN key", e);
		}
		log.info(EXIT_STRING + CLASS_NAME + "getCacheExpiryTimeForUrnKey()");

		return (int) Duration.between(LocalTime.parse(systemTime), LocalTime.parse(digitalClockConstant)).getSeconds();

	}

	public static String generateKeyForUrnStoring(String bankCode,String engineCode,String screenCode)
			throws UrnGeneratorException {
		log.info(ENTER_STRING + CLASS_NAME + "generateKeyForUrnStoring()");

		log.info(EXIT_STRING + CLASS_NAME + "generateKeyForUrnStoring() ==>");
		return UrnGeneratorConstants.KEY_CNST + bankCode
				+ engineCode + screenCode;
	}

}
