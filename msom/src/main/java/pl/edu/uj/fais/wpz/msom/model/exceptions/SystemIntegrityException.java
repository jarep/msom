/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.exceptions;

/**
 * Exception of system integrity
 *
 * @author jarep
 */
public class SystemIntegrityException extends Exception {

    public SystemIntegrityException(String message) {
        super(message);
    }

}
