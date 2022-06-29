package com.idg.idgcore.graphqlsdl.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicISequenceGenerator implements ISequenceGenerator {
    private final AtomicLong value = new AtomicLong(1);

    @Override
    public long getNext () {
        return value.getAndIncrement();
    }

    public String getSeq () {
        String localDateSeq;
        long seqId;
        seqId = this.getNext();
        localDateSeq = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        return "Country ref no:[" + localDateSeq + seqId + "]";
    }

}
