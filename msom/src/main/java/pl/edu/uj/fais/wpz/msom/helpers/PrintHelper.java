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

}
