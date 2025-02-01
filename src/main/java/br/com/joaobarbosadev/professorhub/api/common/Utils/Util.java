package br.com.joaobarbosadev.professorhub.api.common.Utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class Util {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.of("America/Sao_Paulo"));


    public static Instant getInstance() {
        return Instant.now();
    }

    public static String getFormattedInstante() {
        Instant instant = getInstance();
        return DATE_TIME_FORMATTER.format(instant);
    }
}
