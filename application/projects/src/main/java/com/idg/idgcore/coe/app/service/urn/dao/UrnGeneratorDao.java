package com.idg.idgcore.coe.app.service.urn.dao;


import com.idg.idgcore.coe.exception.*;

public interface UrnGeneratorDao {

	public String getNewUrnForTransaction(String bankCode, String engineCode, String screenCode, String generatedKeyForUrn)
			throws UrnGeneratorException;

}
