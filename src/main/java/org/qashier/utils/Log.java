package org.qashier.utils;

import org.qashier.constants.COLORS;

public class Log {
    public static void green(String message) {
        System.out.println(COLORS.ANSI_GREEN + message + COLORS.ANSI_RESET);
    }

    public static void red(String message) {
        System.out.println(COLORS.ANSI_RED + message + COLORS.ANSI_RESET);
    }

    public static void yellow(String message) {
        System.out.println(COLORS.ANSI_YELLOW + message + COLORS.ANSI_RESET);
    }

    public static void blue(String message) {
        System.out.println(COLORS.ANSI_BLUE + message + COLORS.ANSI_RESET);
    }

    public static void purple(String message) {
        System.out.println(COLORS.ANSI_PURPLE + message + COLORS.ANSI_RESET);
    }

    public static void cyan(String message) {
        System.out.println(COLORS.ANSI_CYAN + message + COLORS.ANSI_RESET);
    }

    public static void white(String message) {
        System.out.println(COLORS.ANSI_WHITE + message + COLORS.ANSI_RESET);
    }
}
