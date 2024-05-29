package com.nashss.se.teaminsynchservice.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.regex.Pattern;

public final class TeamInSynchServiceUtils {
    private static final Pattern INVALID_CHARACTER_PATTERN = Pattern.compile("[\"'\\\\]");
    static final int PLAYLIST_ID_LENGTH = 5;

    private TeamInSynchServiceUtils() {
    }

    /**
     * Validates a string to ensure it is not blank and does not contain invalid characters.
     *
     * @param stringToValidate the string to validate
     * @return true if the string is valid, false otherwise
     */
    public static boolean isValidString(String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return false;
        } else {
            return !INVALID_CHARACTER_PATTERN.matcher(stringToValidate).find();
        }
    }

    /**
     * Validates if a date string is in ISO 8601 format.
     *
     * @param dateString the date string to validate
     * @return true if the date string is valid, false otherwise
     */
    public static boolean isValidIsoDate(String dateString) {
        try {
            ZonedDateTime.parse(dateString);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

        /**
         * Generates a new unique identifier (GUID) for a member.
         *
         * @return a new GUID as a string
         */
        public static String generateMemberId () {
            return UUID.randomUUID().toString();
        }
    }

