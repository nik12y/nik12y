package com.idg.idgcore.coe.app.service.urn;

import com.idg.idgcore.coe.common.*;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.dto.context.*;
import com.idg.idgcore.infra.cache.redis.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.text.*;
import java.time.*;
import java.util.*;

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

			paramError = validateParams(sessionContext.getBankCode(), Constants.BANKCODE_WIDTH,
					sessionContext.getEngineCode(), Constants.ENGINECODE_WIDTH,
					sessionContext.getScreenCode(), Constants.SCREENCODE_WIDTH);
			
			if (paramError.equalsIgnoreCase(Constants.SUCCESS_MSG)) {

				generatedKeyForUrn = generateKeyForUrnStoring(sessionContext.getBankCode(),
						sessionContext.getEngineCode(),sessionContext.getScreenCode());

				returnValueUrn = getNewUrnForTransaction(sessionContext.getBankCode(),sessionContext.getEngineCode(),sessionContext.getScreenCode(), generatedKeyForUrn);
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
		String errorMsg = Constants.SUCCESS_MSG;

		if (bankCode.length() <= bankCodeSize) {
			errorMsg = Constants.SUCCESS_MSG;
		}else {
			return errorMsg = Constants.ERROR_MSG_BANK_CODE;
		}

		if (engineCode.length() <= engineCodeSize) {
			errorMsg = Constants.SUCCESS_MSG;
		}else{
			return errorMsg = Constants.ERROR_MSG_ENGINE_CODE;
		}

		if (screenCode.length() <= screenCodesize) {
			errorMsg = Constants.SUCCESS_MSG;
		}else{
			return errorMsg = Constants.ERROR_MSG_SCREEN_CODE;
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
		return Constants.KEY_CNST + bankCode
				+ engineCode + screenCode;
	}

	public String getNewUrnForTransaction(String bankCode,String engineCode,String screenCode , String generatedKeyForUrn)
			throws UrnGeneratorException {
		log.info(ENTER_STRING + CLASS_NAME + "getNewUrnForTransaction()");

		int redisSeqNo = 0;
		String redisKey = null;
		String urnNo = null;
		String currentDate = null;
		String seqNo = null;

		try {

			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
			currentDate = formatter.format(date);



			if (isKeyExsist(generatedKeyForUrn)) {

				redisKey = RedisCache.retrieveDataFromCacheForUrn(generatedKeyForUrn, "urnNo");
				redisSeqNo = Integer.parseInt(redisKey.substring(bankCode.length()+
						engineCode.length()+screenCode.length()+
						Constants.DATECODE_WIDTH));
				redisSeqNo = redisSeqNo + 1;
				seqNo = checkPrefix(redisSeqNo);

			} else {

				redisSeqNo = redisSeqNo + 1;
				seqNo = checkPrefix(redisSeqNo);

			}

			urnNo = bankCode + engineCode
					+ screenCode + currentDate + seqNo;

		} catch (Exception e) {
			e.printStackTrace();

			throw new UrnGeneratorException("Exception while fetching the getNewUrnForTransaction", e);
		}

		log.info(EXIT_STRING + CLASS_NAME + "getNewUrnForTransaction()");
		return urnNo;
	}

	public static boolean isKeyExsist(String key) throws UrnGeneratorException {
		log.info(ENTER_STRING + CLASS_NAME + "isKeyExsist()");

		boolean flag = false;

		try {

			flag = RedisCacheConfig.redisConnection().exists(key);

		} catch (Exception e) {
			throw new UrnGeneratorException("Exception while checking the key from redis cache", e);
		}

		log.info(EXIT_STRING + CLASS_NAME + "isKeyExsist() ==>");
		return flag;
	}

	public String checkPrefix(int seqNo) throws UrnGeneratorException {

		log.info(ENTER_STRING + CLASS_NAME + "checkPrefix()");

		String formattedPrefix = null;

		try {

			formattedPrefix = String.valueOf(seqNo);

			if (formattedPrefix.length() == 3) {

				formattedPrefix = "0" + formattedPrefix;

			} else if (formattedPrefix.length() == 2) {

				formattedPrefix = "00" + formattedPrefix;

			} else if (formattedPrefix.length() == 1) {

				formattedPrefix = "000" + formattedPrefix;

			}

		} catch (Exception e) {
			e.printStackTrace();

			throw new UrnGeneratorException("Exception while fetching the checkPrefix() ", e);
		}

		log.info(EXIT_STRING + CLASS_NAME + "checkPrefix()");
		return formattedPrefix;
	}

}
