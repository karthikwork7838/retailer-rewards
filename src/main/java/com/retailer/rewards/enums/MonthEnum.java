package com.retailer.rewards.enums;

/**
 * Enumeration representing the twelve months of the calendar year.
 * 
 * This enum provides a type-safe way to handle month values throughout the
 * rewards
 * application. Each month constant is associated with its numeric
 * representation
 * (1-12) and its full name. This enum is particularly useful for generating
 * monthly
 * reward summaries and organizing transaction data by month.
 * 
 * <p>
 * <b>Usage:</b>
 * <ul>
 * <li>Access specific months as constants: {@code MonthEnum.JANUARY},
 * {@code MonthEnum.DECEMBER}</li>
 * <li>Iterate through all months: {@code MonthEnum.values()}</li>
 * <li>Get month name by number: {@code MonthEnum.getMonthNameByNumber(1)}
 * returns "January"</li>
 * <li>Get month information: {@code month.getMonthNumber()},
 * {@code month.getMonthName()}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Business Context:</b>
 * This enum is used in the rewards system to organize customer rewards data
 * into
 * monthly summaries, enabling analysis of customer spending patterns across
 * different
 * months and years.
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 */
public enum MonthEnum {

    /**
     * January - the first month of the calendar year.
     */
    JANUARY(1, "January"),

    /**
     * February - the second month of the calendar year.
     */
    FEBRUARY(2, "February"),

    /**
     * March - the third month of the calendar year.
     */
    MARCH(3, "March"),

    /**
     * April - the fourth month of the calendar year.
     */
    APRIL(4, "April"),

    /**
     * May - the fifth month of the calendar year.
     */
    MAY(5, "May"),

    /**
     * June - the sixth month of the calendar year.
     */
    JUNE(6, "June"),

    /**
     * July - the seventh month of the calendar year.
     */
    JULY(7, "July"),

    /**
     * August - the eighth month of the calendar year.
     */
    AUGUST(8, "August"),

    /**
     * September - the ninth month of the calendar year.
     */
    SEPTEMBER(9, "September"),

    /**
     * October - the tenth month of the calendar year.
     */
    OCTOBER(10, "October"),

    /**
     * November - the eleventh month of the calendar year.
     */
    NOVEMBER(11, "November"),

    /**
     * December - the twelfth month of the calendar year.
     */
    DECEMBER(12, "December");

    /**
     * The numeric representation of the month (1-12).
     * Used for month-based filtering and calculations in the rewards system.
     */
    private final int monthNumber;

    /**
     * The full name of the month.
     * Used for display and reporting purposes in the rewards system.
     */
    private final String monthName;

    /**
     * Constructs a MonthEnum constant with its numeric value and display name.
     * 
     * @param monthNumber the numeric representation of the month (1 for January
     *                    through 12 for December)
     * @param monthName   the full name of the month as a string
     */
    MonthEnum(int monthNumber, String monthName) {
        this.monthNumber = monthNumber;
        this.monthName = monthName;
    }

    /**
     * Gets the numeric representation of this month.
     * 
     * <p>
     * Returns a value between 1 (January) and 12 (December), following the standard
     * calendar convention where January is month 1.
     * </p>
     * 
     * @return the month number (1-12)
     */
    public int getMonthNumber() {
        return monthNumber;
    }

    /**
     * Gets the full name of this month.
     * 
     * @return the month name (e.g., "January", "February", etc.)
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * Retrieves the month name corresponding to a given month number.
     * 
     * <p>
     * This utility method iterates through all {@code MonthEnum} constants to find
     * the
     * one matching the provided month number. It provides a convenient way to
     * convert
     * a numeric month value (1-12) into its corresponding month name.
     * </p>
     * 
     * <p>
     * <b>Example Usage:</b>
     * 
     * <pre>
     * {@code
     * String monthName = MonthEnum.getMonthNameByNumber(1); // Returns "January"
     * String monthName = MonthEnum.getMonthNameByNumber(12); // Returns "December"
     * }
     * </pre>
     * </p>
     * 
     * @param monthNumber the numeric month value to look up (1-12)
     * 
     * @return the month name corresponding to the provided month number
     * 
     * @throws IllegalArgumentException if the provided month number is not in the
     *                                  range 1-12
     */
    public static String getMonthNameByNumber(int monthNumber) {
        for (MonthEnum month : MonthEnum.values()) {
            if (month.getMonthNumber() == monthNumber) {
                return month.getMonthName();
            }
        }
        throw new IllegalArgumentException("Invalid month number: " + monthNumber);
    }
}