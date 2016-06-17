/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

/**
 *
 * @author jarep
 */
public interface IActivatable {

    /**
     * Activate object - prevents changes in structure during simulation.
     *
     * @return {@code true} if successfully activated, {@code false} if it was
     * already activated or can not be activate.
     */
    public boolean activate();

    /**
     * Deactivate object - stops execution and allows changes.
     *
     * @return {@code true} if successfully deactivated, {@code false} if it was
     * already deactivated or can not be deactivate.
     */
    public boolean deactivate();

    /**
     * Check if object is activated.
     *
     * @return {@code true} if is active, {@code false} otherwise.
     */
    public boolean isActive();

}
