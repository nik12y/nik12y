package com.idg.idgcore.coe.app.service.urn.constant;

public class UrnGeneratorConstants {

	public static final int BANKCODE_WIDTH = 4;
	public static final int ENGINECODE_WIDTH = 2;
	public static final int SCREENCODE_WIDTH = 2;
	public static final int DATECODE_WIDTH = 6;
	public static final int SEQ_NO_WIDTH = 4;

	public static final String ERROR_MSG_BANK_CODE = "Bank Code is not of a specified size which should be 4";
	public static final String ERROR_MSG_ENGINE_CODE = "Engine Code is not of a specified size which should be 2";
	public static final String ERROR_MSG_SCREEN_CODE = "Screen Code is not of a specified size which should be 2";
	public static final String SUCCESS_MSG = "Success";

	public static final String KEY_CNST = "KEY_";

	private UrnGeneratorConstants() {
		throw new IllegalStateException("UrnGeneratorConstants class");
	}

}
