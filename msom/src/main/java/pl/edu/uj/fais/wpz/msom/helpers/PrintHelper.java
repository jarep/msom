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
        System.out.println("[" + who + "]:" + msg);
    }

    public static void printAlert(String who, String msg) {
        System.out.println("[! " + who + " !]:" + msg);
    }

    public static void printError(String who, String msg) {
        System.out.println("ERROR [!!! " + who + " !!!!]");
        System.out.println("--- " + msg + " ---");
        System.out.println("--------- ERROR |");

    }

}
