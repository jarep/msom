/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.exceptions;

/**
 * Exception throws when detected that some task was received by some Task
 * Dispatcher a second time in one simulation or founded infinity loop in path
 * structure in Processing System.
 *
 * @author jarep
 */
public class PathDefinitionInfinityLoopExcpetion extends Exception {

    public PathDefinitionInfinityLoopExcpetion(String message) {
        super(message);
    }

}
