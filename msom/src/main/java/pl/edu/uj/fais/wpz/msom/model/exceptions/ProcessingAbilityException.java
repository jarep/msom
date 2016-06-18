/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.exceptions;

/**
 * Exception throws when some type of task should be processed by some Task
 * Dispatcher, but this Task Dispatcher doesn't have a proper Processing Unit or
 * when task with unsupported type was forwarded to Processing Unit.
 *
 * @author jarep
 */
public class ProcessingAbilityException extends Exception {

    public ProcessingAbilityException(String message) {
        super(message);
    }

}
