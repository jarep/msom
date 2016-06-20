/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import org.apache.commons.math3.distribution.PoissonDistribution;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TimeIntervalGenerator;

/**
 *
 * @author Adam
 */
public class PoissonIntervalGenerator implements TimeIntervalGenerator{
           private PoissonDistribution distribution;
           private int maxInterval;
           private DistributionType typeOfDistribution = DistributionType.POISSON;
           

    public PoissonIntervalGenerator(double lambda ,int maxInterval) {
        this.distribution = new PoissonDistribution(lambda, maxInterval);
    }
    @Override
    public int getNext(){
        return distribution.sample();
    }

    @Override
    public void setParamters(double A, double B) {
        this.distribution = new PoissonDistribution(A, B); 
        this.maxInterval=(int)B;
    }

    @Override
    public List<Double> getParameters() {
        List<Double> param = new ArrayList<>();
        param.add(distribution.getMean());
        param.add((double)maxInterval);
        return param;
    }

    @Override
    public DistributionType getDistributionType() {
        return typeOfDistribution;
    }
           
}
