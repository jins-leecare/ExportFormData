package com.leecare.extract.utils;

/*
 * StringUtils.java
 *
 * Copyright Â© 2023 Leecare. All Rights Reserved.
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This is used to do common things with Strings.
 *
 * @author Jins Joy
 */
public class StringUtils {
    public static final String COMMA = ",";
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String HYPHEN = "-";
    public static final String NEWLINE = "\n";

    /**
     * Appends the string repeat a number of times.
     *
     * @param aBuilder a builder (not null)
     * @param aString a string (not null)
     * @param aNumber a number (&gt;= 0)
     * @return the passed in builder (not null)
     */
    public static StringBuilder appendRepeats(StringBuilder aBuilder, String aString, int aNumber) {
        assert aBuilder != null && aString != null && aNumber >= 0;
        for (int i = 0; i < aNumber; i++) {
            aBuilder.append(aString);
        }
        return aBuilder;
    }

    /**
     * Returns a version of the supplied string with the first letter of each word capitalized
     *
     * @param str the String to capitalize, may be null
     * @param delimiters set of characters to determine capitalization, null means whitespace
     * @return capitalized String, <code>null</code> if null String input
     */
    public static String capitalize(String str, char... delimiters) {
        int delimLen = delimiters == null ? -1 : delimiters.length;
        if (str == null || str.length() == 0 || delimLen == 0) {
            return str;
        }
        int strLen = str.length();
        StringBuilder buffer = new StringBuilder(strLen);
        boolean capitalizeNext = true;
        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);
            if (isDelimiter(ch, delimiters)) {
                buffer.append(ch);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer.append(Character.toUpperCase(ch));
                capitalizeNext = false;
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    /**
     * Tests to see if the parameter contains at least 1 digit.
     *
     * <p>A digit can be 0 to 9 or the Arabic or full width equivalents etc.
     *
     * @param aString a string to test (not null)
     * @return true if the parameter contains at least 1 digit
     */
    public static boolean containsAtLeastOneDigit(String aString) {
        assert aString != null;
        // This performs better than using a Pattern
        int length = aString.length();
        for (int i = 0; i < length; i++) {
            if (Character.isDigit(aString.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests to see if the parameter contains at least 1 letter.
     *
     * <p>A letter can be a letter in most languages.
     *
     * @param aString a string to test (not null)
     * @return true if the parameter contains at least 1 letter
     */
    public static boolean containsAtLeastOneLetter(String aString) {
        assert aString != null;
        // This performs better than using a Pattern
        int length = aString.length();
        for (int i = 0; i < length; i++) {
            if (Character.isLetter(aString.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests to see if the parameter contains at least 1 special character.
     *
     * <UL>
     *   <LI>A special character is defined as not a digit, not a letter, and not whitespace.
     *   <LI>A digit can be 0 to 9 or the Arabic or full width equivalents etc.
     *   <LI>A letter can be a letter in most languages.
     * </UL>
     *
     * @param aString a string to test (not null)
     * @return true if the parameter contains at least 1 special character
     */
    public static boolean containsAtLeastOneSpecialCharacter(String aString) {
        assert aString != null;
        // This performs better than using a Pattern
        int length = aString.length();
        for (int i = 0; i < length; i++) {
            char character = aString.charAt(i);
            if (!(Character.isLetterOrDigit(character) || Character.isWhitespace(character))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests to see if the parameter contains only digits.<br>
     * A digit can be 0 to 9 or the Arabic or full width equivalents etc.
     *
     * @param aString a string to test (not null)
     * @return true if this contains only digits
     */
    public static boolean containsOnlyDigits(String aString) {
        assert aString != null;
        // This performs better than using a Pattern
        int length = aString.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(aString.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests to see if the parameter contains only Latin digits.<br>
     * A digit can be 0 to 9.
     *
     * @param aString a string to test (not null)
     * @return true if this contains only Latin digits
     */
    public static boolean containsOnlyLatinDigits(String aString) {
        assert aString != null;
        // This performs better than using a Pattern
        int length = aString.length();
        for (int i = 0; i < length; i++) {
            char c = aString.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests to see if the parameter contains only Latin digits and possibly a decimal point.<br>
     * A digit can be 0 to 9.<br>
     * If there is a decimal point it can not be at the start or end of the string.
     *
     * @param aString a string to test (not null)
     * @return true if this contains only Latin digits and possibly a decimal point
     */
    public static boolean containsOnlyLatinDigitsAndDecimalPoint(String aString) {
        assert aString != null;
        // This performs better than using a Pattern
        int length = aString.length();
        boolean foundDecimalPoint = false;
        for (int i = 0; i < length; i++) {
            char c = aString.charAt(i);
            if (c < '0' || c > '9') {
                if (c == '.' && !(foundDecimalPoint || i == 0 || i == length - 1)) {
                    foundDecimalPoint = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Tests to see if the parameter contains only Latin digits and letters.<br>
     * A digit can be 0 to 9.<br>
     * A letter can be a to z or A to Z.
     *
     * @param aString a string to test (not null)
     * @return true if this contains only only Latin digits or letters
     */
    public static boolean containsOnlyLatinDigitsAndLetters(String aString) {
        assert aString != null;
        // This performs better than using a Pattern
        int length = aString.length();
        for (int i = 0; i < length; i++) {
            if (!isLatinDigitOrLetter(aString.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if a string is not null and it starts with a prefix.
     *
     * @param aString a string (can be null)
     * @param aPrefix a prefix (not null)
     * @return true if a string is not null and it starts with a prefix
     */
    public static boolean startsWith(String aString, String aPrefix) {
        assert aPrefix != null;
        return aString != null && aString.startsWith(aPrefix);
    }

    /**
     * Returns a ASCII representation of the specified Hex string.
     *
     * @param aHexString a HEX format string (not null)
     * @return the ASCII representation of the specified Hex string (not null)
     */
    public static String convertToAscii(String aHexString) {
        assert aHexString != null;
        StringBuilder asciiBuilder = new StringBuilder("");
        for (int i = 0; i < aHexString.length(); i += 2) {
            String str = aHexString.substring(i, i + 2);
            asciiBuilder.append((char) Integer.parseInt(str, 16));
        }
        return asciiBuilder.toString();
    }

    /**
     * Returns a Hex representation of the specified ASCII string.
     *
     * @param aAsciiString a ASCII string (not null)
     * @return the Hex representation of the specified ASCII string (not null)
     */
    public static String convertToHexString(String aAsciiString) {
        assert aAsciiString != null;
        char[] chars = aAsciiString.toCharArray();
        StringBuilder hexBuilder = new StringBuilder();
        for (char ch : chars) {
            hexBuilder.append(Integer.toHexString((int) ch));
        }
        return hexBuilder.toString();
    }

    /**
     * Return first N characters of a string. Trows error if string is null.
     *
     * @param aString an input string.
     * @param aNoOfChars number of characters.
     * @return sub string
     */
    public static String getFirstNChars(String aString, int aNoOfChars) {
        assert aString != null;
        String aSubString;
        if (aString.length() < aNoOfChars) {
            aSubString = aString;
        } else {
            aSubString = aString.substring(0, aNoOfChars);
        }
        return aSubString;
    }

    /**
     * @param aString string to get required value
     * @param aLength length of the required string
     * @return aString value substring if aString is greater than length
     */
    public static String getRequiredValue(String aString, int aLength) {
        if (aString != null && aString.length() > aLength) {
            return aString.substring(0, aLength);
        }
        return aString;
    }

    /**
     * Tests to see if the parameter is a Latin digit or a letter.<br>
     * A digit can be 0 to 9.<br>
     * A letter can be a to z or A to Z.
     *
     * @param aChar a char to test (not null)
     * @return true if it is a Latin digit or a letter
     */
    public static boolean isLatinDigitOrLetter(char aChar) {
        return aChar >= 'a' && aChar <= 'z'
                || aChar >= 'A' && aChar <= 'Z'
                || aChar >= '0' && aChar <= '9';
    }

    /**
     * Return true if a value is not null and is not empty.
     *
     * @param aValue a value (can be null)
     * @return true if a value is not null and is not empty
     */
    public static boolean isNotEmpty(String aValue) {
        return aValue != null && !aValue.isEmpty();
    }

    /**
     * Parses comma separated integers.
     *
     * @param aString a string to parse (not null)
     * @return the list of integers or null if aString is not comma separated integers.
     */
    public static List<Integer> parseCommaSeparatedIntegers(String aString) {
        List<Integer> result = new LinkedList<Integer>();
        for (String value : aString.split(",")) {
            Integer integer = parseInteger(value);
            if (integer == null) {
                return null;
            }
            result.add(integer);
        }
        return result;
    }

    /**
     * Parses comma separated strings.
     *
     * @param aString a string to parse (not null)
     * @return the list of integers or null if aString is not comma separated strings.
     */
    public static List<String> parseCommaSeparatedStrings(String aString) {
        List<String> result = new LinkedList<String>();
        for (String value : aString.split(",")) {
            if (value == null) {
                return new ArrayList<String>();
            }
            result.add(value);
        }
        return result;
    }

    /**
     * Parse a Double that optionally starts with a - then has 1 or more Latin digits optionally
     * followed by a decimal point which is followed by 1 or more Latin digits.<br>
     * A digit can be 0 to 9.
     *
     * @param aString the string to parse (not null)
     * @return the Double (maybe null if a String is not of the correct format, maybe approximate if
     *     there are more than 15 digits of precision)
     */
    public static Double parseDouble(String aString) {
        assert aString != null;
        int length = aString.length();
        if (length == 0) {
            return null;
        }
        char c = aString.charAt(0);
        if (c == '-') {
            Double value = parseDouble(aString.substring(1));
            return value == null ? null : -value.doubleValue();
        }
        if (c < '0' || c > '9') {
            return null;
        }
        double integerPart = c - '0';
        for (int i = 1; i < length; i++) {
            c = aString.charAt(i);
            if (c < '0' || c > '9') {
                if (c == '.') {
                    int j = length - 1;
                    c = aString.charAt(j--);
                    if (c < '0' || c > '9') {
                        return null;
                    }
                    double fractionPart = c - '0';
                    while (j > i) {
                        c = aString.charAt(j--);
                        if (c < '0' || c > '9') {
                            return null;
                        }
                        fractionPart = fractionPart / 10 + c - '0';
                    }
                    return integerPart + fractionPart / 10;
                }
                return null;
            }
            integerPart = integerPart * 10 + c - '0';
        }
        return integerPart;
    }

    /**
     * Parse a Hex Byte Array.
     *
     * <p>If aHexString is not an even number of digits or the digits are not hex digits (0 to 9 or a
     * to f or A to F) then null will be returned.
     *
     * @param aHexString the hex string to parse (not null)
     * @return the Byte Array or null if aHexString is not in the format described above
     */
    public static byte[] parseHexByteArray(String aHexString) {
        assert aHexString != null;
        int length = aHexString.length();
        if (length % 2 != 0) {
            return null;
        }
        byte[] result = new byte[length / 2];
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            char c = aHexString.charAt(index++);
            int highByte;
            if (c >= '0' && c <= '9') {
                highByte = c - '0';
            } else if (c >= 'a' && c <= 'f') {
                highByte = c - 'a' + 10;
            } else if (c >= 'A' && c <= 'F') {
                highByte = c - 'A' + 10;
            } else {
                return null;
            }
            c = aHexString.charAt(index++);
            if (c >= '0' && c <= '9') {
                result[i] = (byte) (highByte * 16 + c - '0');
                continue;
            }
            if (c >= 'a' && c <= 'f') {
                result[i] = (byte) (highByte * 16 + c - 'a' + 10);
                continue;
            }
            if (c >= 'A' && c <= 'F') {
                result[i] = (byte) (highByte * 16 + c - 'A' + 10);
                continue;
            }
            return null;
        }
        return result;
    }

    /**
     * Parse a Hex Integer created using Integer.toHexString(int aValue)
     *
     * @param aHexString the hex string to parse (not null)
     * @return the Integer or null if aHexString is not of the format created by
     *     Integer.toHexString(int aValue)
     */
    public static Integer parseHexInteger(String aHexString) {
        assert aHexString != null;
        int length = aHexString.length();
        if (length == 0 || length > 8) {
            return null;
        }
        int result = 0;
        for (int i = 0; i < length; i++) {
            char c = aHexString.charAt(i);
            if (c >= '0' && c <= '9') {
                result = result * 16 + c - '0';
                continue;
            }
            if (c >= 'a' && c <= 'f') {
                result = result * 16 + c - 'a' + 10;
                continue;
            }
            if (c >= 'A' && c <= 'F') {
                result = result * 16 + c - 'A' + 10;
                continue;
            }
            return null;
        }
        return result;
    }

    /**
     * Parse a Hex Long created using Long.toHexString(long aValue)
     *
     * @param aHexString the hex string to parse (not null)
     * @return the Long or null if aHexString is not of the format created by Long.toHexString(long
     *     aValue)
     */
    public static Long parseHexLong(String aHexString) {
        assert aHexString != null;
        int length = aHexString.length();
        if (length == 0 || length > 16) {
            return null;
        }
        long result = 0;
        for (int i = 0; i < length; i++) {
            char c = aHexString.charAt(i);
            if (c >= '0' && c <= '9') {
                result = result * 16 + c - '0';
                continue;
            }
            if (c >= 'a' && c <= 'f') {
                result = result * 16 + c - 'a' + 10;
                continue;
            }
            if (c >= 'A' && c <= 'F') {
                result = result * 16 + c - 'A' + 10;
                continue;
            }
            return null;
        }
        return result;
    }

    /**
     * Parse a Byte created using Byte.toString(byte aValue)
     *
     * @param aString the string to parse (not null)
     * @return the Byte or null if a String is not of the format created by Byte.toString(byte aValue)
     */
    public static Byte parseByte(String aString) {
        assert aString != null;
        Integer resultAsInteger = parseInteger(aString);
        if (resultAsInteger != null) {
            int resultAsInt = resultAsInteger.intValue();
            byte result = (byte) resultAsInt;
            if (result == resultAsInt) {
                return result;
            }
        }
        return null;
    }

    /**
     * Parse an Integer created using Integer.toString(int aValue)
     *
     * @param aString the string to parse (not null)
     * @return the Integer or null if a String is not of the format created by Integer.toString(int
     *     aValue)
     */
    public static Integer parseInteger(String aString) {
        assert aString != null;
        int length = aString.length();
        if (length == 0) {
            return null;
        }
        char c = aString.charAt(0);
        if (c == '-') {
            if (length == 1) {
                return null;
            }
            c = aString.charAt(1);
            if (c < '0' || c > '9') {
                return null;
            }
            int result = '0' - c;
            for (int i = 2; i < length; i++) {
                c = aString.charAt(i);
                if (c < '0' || c > '9' || (result = result * 10 + '0' - c) > 0) {
                    return null;
                }
            }
            return result;
        }
        if (c < '0' || c > '9') {
            return null;
        }
        int result = c - '0';
        for (int i = 1; i < length; i++) {
            c = aString.charAt(i);
            if (c < '0' || c > '9' || (result = result * 10 + c - '0') < 0) {
                return null;
            }
        }
        return result;
    }

    /**
     * Parse a Long created using Long.toString(long aValue)
     *
     * @param aString the string to parse (not null)
     * @return the Long or null if a String is not of the format created by Long.toString(long aValue)
     */
    public static Long parseLong(String aString) {
        assert aString != null;
        int length = aString.length();
        if (length == 0) {
            return null;
        }
        char c = aString.charAt(0);
        if (c == '-') {
            if (length == 1) {
                return null;
            }
            c = aString.charAt(1);
            if (c < '0' || c > '9') {
                return null;
            }
            long result = '0' - c;
            for (int i = 2; i < length; i++) {
                c = aString.charAt(i);
                if (c < '0' || c > '9' || (result = result * 10 + '0' - c) > 0) {
                    return null;
                }
            }
            return result;
        }
        if (c < '0' || c > '9') {
            return null;
        }
        long result = c - '0';
        for (int i = 1; i < length; i++) {
            c = aString.charAt(i);
            if (c < '0' || c > '9' || (result = result * 10 + c - '0') < 0) {
                return null;
            }
        }
        return result;
    }

    /**
     * Strip number character in front of a String </br> Consider the first space: value is lookupitem
     * seqno + lookupitem text
     *
     * @param value the String to strip
     * @return value String value with no number in front
     */
    public static String stripRank(String value) {
        if (value == null || value.length() == 0) {
            return value;
        }
        int index = value.indexOf(' ');
        if (index != -1) {
            value = value.substring(index + 1);
            return value;
        }
        return value;
    }

    /**
     * Remove comma and trim string
     *
     * @param str
     * @return
     */
    public static String trimAndRemoveComma(String str) {
        if (str == null) return "";
        return str.trim().replace(',', ' ');
    }

    /**
     * Replace comma with empty character
     *
     * @param aString value to replace (can be null)
     * @return String value with no spaces
     */
    public static String replaceCommaWithEmpty(String aString) {
        if (aString == null) return EMPTY;
        return aString.trim().replace(COMMA, EMPTY);
    }

    /**
     * Replace space with empty character
     *
     * @param aString
     * @return String
     */
    public static String replaceSpaceWithEmpty(String aString) {
        if (aString == null) return EMPTY;
        return aString.trim().replace(SPACE, EMPTY);
    }

    /**
     * Appends the escaped XML.
     *
     * <p>This will perform the following replacements:
     *
     * <ul>
     *   <li>{@code "} with {@code &quot;}
     *   <li>{@code '} with {@code &apos;}
     *   <li>{@code <} with {@code &lt;}
     *   <li>{@code >} with {@code &gt;}
     *   <li>{@code &} with {@code &amp;}
     * </ul>
     *
     * <p>Similar to {@code SafeHtmlUtils} it will not escape other characters such as unprintable
     * ones as specified in XML 1.0 and XML 1.1.
     *
     * @param aBuilder a builder to append to (not null)
     * @param aString a string to escape (not null)
     * @return the passed in builder (not null)
     */
    public static StringBuilder appendEscapedXML(StringBuilder aBuilder, String aString) {
        assert aBuilder != null && aString != null;
        int length = aString.length();
        int startIndex = 0;
        for (int i = 0; i < length; i++) {
            switch (aString.charAt(i)) {
                case '"':
                {
                    startIndex = appendEscapedXML(aBuilder, aString, startIndex, i, "&quot;");
                    continue;
                }
                case '\'':
                {
                    startIndex = appendEscapedXML(aBuilder, aString, startIndex, i, "&apos;");
                    continue;
                }
                case '<':
                {
                    startIndex = appendEscapedXML(aBuilder, aString, startIndex, i, "&lt;");
                    continue;
                }
                case '>':
                {
                    startIndex = appendEscapedXML(aBuilder, aString, startIndex, i, "&gt;");
                    continue;
                }
                case '&':
                {
                    startIndex = appendEscapedXML(aBuilder, aString, startIndex, i, "&amp;");
                    continue;
                }
            }
        }
        return startIndex != length ? aBuilder.append(aString.substring(startIndex)) : aBuilder;
    }

    private static int appendEscapedXML(
            StringBuilder aBuilder, String aString, int aStartIndex, int aEndIndex, String aReplacement) {
        if (aStartIndex != aEndIndex) {
            aBuilder.append(aString.substring(aStartIndex, aEndIndex));
        }
        aBuilder.append(aReplacement);
        return aEndIndex + 1;
    }

    /**
     * Unescapes a String.
     *
     * <p>This will perform the following replacements:
     *
     * <ul>
     *   <li>{@code &quot;} with {@code "}
     *   <li>{@code &apos;} with {@code '}
     *   <li>{@code &#39;} with {@code '}
     *   <li>{@code &lt;} with {@code <}
     *   <li>{@code &gt;} with {@code >}
     *   <li>{@code &amp;} with {@code &}
     * </ul>
     *
     * This reverses what is done by common XML escapers such as {@code SafeHtmlUtils} and {@code
     * StringEscapeUtils}, and also the appendEscapedXML method above.
     *
     * <p>It will not reverse other characters such as unprintable ones as specified in XML 1.0 and
     * XML 1.1.
     *
     * @param aString a string (not null)
     * @return a string with the XML unescaped (not null)
     */
    public static String unescapeXML(String aString) {
        assert aString != null;
        return aString.contains("&") && aString.contains(";")
                ? aString
                .replaceAll("&quot;", "\"")
                .replaceAll("&apos;", "\'")
                .replaceAll("&#39;", "\'")
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&")
                : aString;
    }

    /**
     * Is the character a delimiter.
     *
     * @param ch the character to check
     * @param delimiters the delimiters
     * @return true if it is a delimiter
     */
    private static boolean isDelimiter(char ch, char[] delimiters) {
        if (delimiters == null) {
            return ch == ' ';
        }
        for (int i = 0, isize = delimiters.length; i < isize; i++) {
            if (ch == delimiters[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if is not blank.
     *
     * @param aValue the value
     * @return true, if is not blank
     */
    public static boolean isNotBlank(String aValue) {
        return aValue != null && !aValue.isEmpty();
    }

    /**
     * Checks if is blank.
     *
     * @param aValue the value
     * @return true, if is blank
     */
    public static boolean isBlank(String aValue) {
        return aValue == null || aValue.isEmpty();
    }

    /**
     * Returns the leftmost characters up to given length.
     *
     * @param aValue the value
     * @param aLength the length
     * @return the string
     */
    public static String left(String aValue, int aLength) {
        return aValue == null
                ? null
                : aLength >= aValue.length() ? aValue : aValue.substring(0, aLength);
    }

    /**
     * Default if empty.
     *
     * @param aValue the value
     * @param aDefault the default
     * @return the string
     */
    public static String defaultIfEmpty(String aValue, String aDefault) {
        return aValue == null || aValue.isEmpty() ? aDefault : aValue;
    }

    /**
     * Return the value when it is not null, or an empty string when it is null.
     *
     * @param aValue a value (can be null)
     * @return the value when it is not null, or an empty string when it is null (not null)
     */
    public static String nullToEmpty(String aValue) {
        return aValue == null ? "" : aValue;
    }

    /**
     * Trim a given string.
     *
     * @param aValue the value
     * @return the string
     */
    public static String trim(String aValue) {
        return aValue == null ? null : aValue.trim();
    }

    /**
     * Convert to upper case.
     *
     * @param aValue the value
     * @return the string
     */
    public static String upperCase(String aValue) {
        return aValue == null ? null : aValue.toUpperCase();
    }

    public static String[] split(String aValue, String aDelimiter) {
        if (aDelimiter == null) {
            return new String[] {aValue};
        }
        return aValue == null ? null : aValue.trim().split(aDelimiter);
    }

    /**
     * Checking if String is null or empty
     *
     * @param aValue the string to be checked
     * @return true if the string is null or empty otherwise false
     */
    public static boolean isNullOrEmpty(String aValue) {
        return aValue == null || aValue.trim().isEmpty();
    }

    /**
     * Validates Medicare Number.
     *
     * @param aMedicareNumber the medicare number (not null)
     * @return true if it is a valid Medicare Number
     */
    public static boolean validateMedicareNumber(String aMedicareNumber) {
        if (aMedicareNumber.length() != 10) {
            return false;
        }
        if (!StringUtils.containsOnlyLatinDigits(aMedicareNumber)) {
            return false;
        }
        if (getDigit(aMedicareNumber, 1) < 2 || getDigit(aMedicareNumber, 1) > 6) {
            return false;
        }
        final int checkDigit =
                (((getDigit(aMedicareNumber, 1))
                        + (getDigit(aMedicareNumber, 2) * 3)
                        + (getDigit(aMedicareNumber, 3) * 7)
                        + (getDigit(aMedicareNumber, 4) * 9)
                        + (getDigit(aMedicareNumber, 5))
                        + (getDigit(aMedicareNumber, 6) * 3)
                        + (getDigit(aMedicareNumber, 7) * 7)
                        + (getDigit(aMedicareNumber, 8) * 9))
                        % 10);
        return checkDigit == getDigit(aMedicareNumber, 9);
    }

    private static int getDigit(final String number, final int index) {
        return Integer.parseInt(number.substring(index - 1, index));
    }

    /**
     * Replace new line characters with single spaces.
     *
     * @param aString value to replace (can be null)
     * @return String value with no new line characters (\n) not null
     */
    public static String replaceNewLineCharsWithSpace(String aString) {
        if (aString == null) return EMPTY;
        return aString.trim().replaceAll(NEWLINE, SPACE);
    }
}

