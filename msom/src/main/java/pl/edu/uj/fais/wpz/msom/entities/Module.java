/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import javax.persistence.Entity;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 * Basic Module (Single server) entity structure
 * @author paweldylag
 */
@Entity
public class Module extends AbstractEntity {

    /** Number of servers cores */
    private Integer cores;
    /** Server's efficiency value */
    private Integer efficiency;
    
    public Module() {}

    public Module(Integer cores, Integer efficiency) {
        this.cores = cores;
        this.efficiency = efficiency;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }
    
    
    
    
    
    
   
}
