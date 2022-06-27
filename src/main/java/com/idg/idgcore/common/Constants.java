package com.idg.idgcore.common;

public final class Constants {
    private Constants () {
    }

    public static final String APP_NAME = "coreEngine";

    public static final String Y = "Y";
    public static final String N = "N";
    public final class Status {
        public static final String DRAFT = "draft";
        public static final String NEW = "new";
        public static final String INACTIVE = "inactive";
        public static final String DELETED = "deleted";
        public static final String UPDATED = "updated";

        private Status () {
        }
    }

    public final class TaskCode {
        public static final String COUNTRY = "COUNTRY";
        public static final String BRANCHTYPE = "BRANCHTYPE";

        private TaskCode () {
        }
    }

    public final class MappingConfig {
        public static final String DRAFT = "draft";
        public static final String ADD = "add";
        public static final String NEW = "new";
        public static final String ACTIVE = "active";
        public static final String REJECTED = "rejected";
        public static final String REJECT = "reject";
        public static final String REOPENED = "reopened";
        public static final String REOPEN = "reopen";
        public static final String INACTIVE = "inactive";
        public static final String CLOSE = "close";
        public static final String CLOSED = "closed";
        public static final String DELETE = "delete";
        public static final String DELETED = "deleted";
        public static final String MODIFY = "modify";
        public static final String UPDATED = "updated";
        public static final String AUTHORIZED = "Authorized";
        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String AUTHORIZE = "authorize";
        public static final String MAKER = "maker";
        public static final String CHECKER = "checker";
        public static final String AUTHORIZED_Y = "Y";
        public static final String AUTHORIZED_N = "N";

        private MappingConfig () {
        }

    }

}