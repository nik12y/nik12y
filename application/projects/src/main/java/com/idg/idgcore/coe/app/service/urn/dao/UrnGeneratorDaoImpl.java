package com.idg.idgcore.coe.app.service.urn.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.idg.idgcore.coe.app.service.urn.constant.*;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.infra.cache.redis.*;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UrnGeneratorDaoImpl implements UrnGeneratorDao {

	private static final String CLASS_NAME = "UrnGeneratorDaoImpl.";
	private static final String ENTER_STRING = "Entered into ";
	private static final String EXIT_STRING = "Exited from ";

	@Override
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
						UrnGeneratorConstants.DATECODE_WIDTH));
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
