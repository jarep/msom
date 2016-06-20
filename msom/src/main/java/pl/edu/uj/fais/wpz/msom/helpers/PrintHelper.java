/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.helpers;

/**
 *
 * @author jarep
 */
public class PrintHelper {

    public static void printMsg(String who, String msg) {
        print("[" + who + "]:\t" + msg);
    }

    public static void printAlert(String who, String msg) {
        print("[! " + who + " !]:\t" + msg);
    }

    public static void printError(String who, String msg) {
        print("ERROR [!!! " + who + " !!!!]");
        print("--- " + msg + " ---");
        print("--------- ERROR |");
    }

    private static void print(String msg) {
        String threadName = Thread.currentThread().getName();
        System.out.println("{" + threadName + "} \t" + msg);
    }

    public static void printSeparator() {
        System.out.println("-------------------------------------------------------------");
    }

    public static void startSection(String header) {
        System.out.println("\n******************** -- START " + header + " -- ********************");
    }

    public static void endSection(String header) {
        System.out.println("******************** -- END OF " + header + " -- ********************\n");
    }

    public static void startSubSection(String header) {
        System.out.println("\t*** -- start " + header + " -- ***");
    }

    public static void endSubSection(String header) {
        System.out.println("\t*** -- end of " + header + " -- ***");
    }

}
