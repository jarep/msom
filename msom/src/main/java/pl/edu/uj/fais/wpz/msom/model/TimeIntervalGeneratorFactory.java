/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import pl.edu.uj.fais.wpz.msom.entities.Distribution;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TimeIntervalGenerator;

/**
 * Fabryka zawsze zwraca dystrybucje wedlug rozkladu poissona !!!!!
 * DO POPRAWY W PRZYSZLYCH ITERACJACH WRAZ Z DODANIEM GAUSSA I INNYCH
 * @author zajac
 */
public class TimeIntervalGeneratorFactory {
    public static TimeIntervalGenerator getTimeIntervalGenerator(Distribution d){
        
        if(d.getType() == DistributionType.POISSON){
            return new PoissonIntervalGenerator(d.getParameterA(), d.getParameterB().intValue());
        }else if(d.getType() == DistributionType.GAUSSIAN){
            return new PoissonIntervalGenerator(d.getParameterA(), d.getParameterB().intValue());
        }else if(d.getType() == DistributionType.LINEAR){
            return new PoissonIntervalGenerator(d.getParameterA(), d.getParameterB().intValue());
        }
        return new PoissonIntervalGenerator(d.getParameterA(), d.getParameterB().intValue());
    
    }
    
}
