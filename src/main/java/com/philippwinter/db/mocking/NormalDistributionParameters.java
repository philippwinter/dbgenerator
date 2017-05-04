package com.philippwinter.db.mocking;

import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * All copyright by Philipp Winter, 2017.
 */
public class NormalDistributionParameters {

    private double mean;
    private double stdev;

    private double lowerLimit;
    private double upperLimit;

    public NormalDistributionParameters(double mean, double stdev, double lowerLimit) {
        this(mean, stdev, lowerLimit, Double.MAX_VALUE);
    }

    public NormalDistributionParameters(double mean, double stdev) {
        this(mean, stdev, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public NormalDistributionParameters(double mean, double stdev, double lowerLimit, double upperLimit) {
        this.mean = mean;
        this.stdev = stdev;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public double generateValue(Random random) {
        return max(this.lowerLimit, min(this.upperLimit, random.nextGaussian()*this.stdev + this.mean));
    }

    public double getMean() {
        return mean;
    }

    public NormalDistributionParameters setMean(double mean) {
        this.mean = mean;
        return this;
    }

    public double getStdev() {
        return stdev;
    }

    public NormalDistributionParameters setStdev(double stdev) {
        this.stdev = stdev;
        return this;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public NormalDistributionParameters setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
        return this;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public NormalDistributionParameters setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
        return this;
    }

    public double generateDouble(Random random) {
        return generateValue(random);
    }

    public Integer generateInt(Random random) {
        return Math.toIntExact(Math.round(generateValue(random)));
    }
}
