package ru.job4j.cars.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Конвертер из String в LocalDateTime
 */
@Component
@Validated
@Slf4j
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    /**
     * Список поддерживаемых для конвертации форматы в виде строк
     */
    private static final List<String> SUPPORTED_FORMATS = Arrays.asList("yyyy-MM-dd", "dd.MM.yyyy, HH:mm");
    /**
     * Список поддерживаемых для конвертации форматы в виде DateTimeFormatter
     */
    private static final List<DateTimeFormatter> DATE_TIME_FORMATTERS = SUPPORTED_FORMATS
            .stream()
            .map(DateTimeFormatter::ofPattern)
            .toList();

    @Override
    public LocalDateTime convert(String s) {
        log.debug("StringToLocalDateTimeConverter, string = " + s);

        try {
            LocalDate ld = LocalDate.parse(s, DATE_TIME_FORMATTERS.get(0));
            LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.MIN.toLocalTime());
            log.debug("StringToLocalDateTimeConverter, LocalDateTime = " + ldt);
            return ldt;
        } catch (DateTimeParseException ex) {
            /* deliberate empty block so that all parsers run*/
        }

        try {
            LocalDateTime ldt = LocalDateTime.parse(s, DATE_TIME_FORMATTERS.get(1));
            log.debug("StringToLocalDateTimeConverter, LocalDateTime = " + ldt);
            return ldt;
        } catch (DateTimeParseException ex) {
            /* deliberate empty block so that all parsers run*/
        }

        throw new DateTimeException(String.format("unable to parse (%s) supported formats are %s",
                s, String.join(", ", SUPPORTED_FORMATS)));
    }
}
