/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 * Basic Module (Single server) entity structure
 * @author paweldylag
 */
@Entity
public class Distribution extends AbstractEntity {
    
    
    
    /** Distribution type name */
    @Enumerated(EnumType.STRING)
    private DistributionType type;

    public Distribution(DistributionType type) {
        this.type = type;
    }

    public Distribution() {
    }
    
    public DistributionType getType() {
        return type;
    }

    public void setType(DistributionType type) {
        this.type = type;
    }
    
}
