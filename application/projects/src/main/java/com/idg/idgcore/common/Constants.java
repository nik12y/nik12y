package com.idg.idgcore.common;

public final class Constants {
    private Constants () {
    }

    public static final String EMPTY_STRING = "";

    public static final char CHAR_Y = 'Y';
    public static final char CHAR_N = 'N';

    public static final String STRING_Y = "Y";
    public static final String STRING_N = "N";

    public final class Status {
        public static final String INACTIVE = "inactive";
        public static final String DELETED = "deleted";

        private Status () {
        }
    }

    public final class TaskCode {
        public static final String COUNTRY = "COUNTRY";
        public static final String CITY = "CITY";

        private TaskCode () {
        }
    }

    public final class MappingConfig {
        public static final String AUTHORIZE = "authorize";
        public static final String MAKER = "maker";
        public static final String CHECKER = "checker";

        private MappingConfig () {
        }

    }

}