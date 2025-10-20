package ua.edu.ucu.apps.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private double[] temperatureSeries;
    private int size;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[0];
        this.size = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        if (this.temperatureSeries == null) {
            this.temperatureSeries = new double[0];
            this.size = 0;
        } else {
            this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
            this.size = this.temperatureSeries.length;
        }
    }

    public double average() {
        if (temperatureSeries == null || size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double sum = 0.0;
        for (double t : temperatureSeries) {sum += t;}
        return sum / size;
    }

    public double deviation() {
        if (temperatureSeries == null || size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double mean = average();
        double sumSq = 0.0;
        for (double t : temperatureSeries) {
            double d = t - mean;
            sumSq += d * d;
        }
        return Math.sqrt(sumSq / size);
    }

    public double min() {
        if (temperatureSeries == null || size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double m = temperatureSeries[0];
        for (int i = 1; i < size; i++) {
            if (temperatureSeries[i] < m)
                m = temperatureSeries[i];
        }
        return m;
    }

    public double max() {
        if (temperatureSeries == null || size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double m = temperatureSeries[0];
        for (int i = 1; i < size; i++) {
            if (temperatureSeries[i] > m)
                m = temperatureSeries[i];
        }
        return m;
    }

    public double findTempClosestToZero() {
        if (temperatureSeries == null || size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double result = temperatureSeries[0];
        double minValue = Math.abs(result);
        for (int i = 0; i < size; i++) {
            double currentValue = Math.abs(temperatureSeries[i]);
            if (currentValue < minValue || (currentValue == minValue && temperatureSeries[i] > result)) {
                result = temperatureSeries[i];
                minValue = currentValue;
                break;
            }
        }
        return result;
    }

    public double findTempClosestToValue(double tempValue) {
        if (temperatureSeries == null || size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double result = temperatureSeries[0];
        double minValue = Math.abs(result);
        for (int i = 1; i < size; i++) {
            double currentValue = Math.abs(temperatureSeries[i] - tempValue);
            if (currentValue < minValue || (currentValue == minValue && temperatureSeries[i] > result)) {
                result = temperatureSeries[i];
                minValue = currentValue;
                break;
            }
        }
        return result;
    }

    public double[] findTempsLessThan(double tempValue) {
        int count = 0;
        for (int i = 0; i < size; i++)
            if (temperatureSeries[i] < tempValue)
                count++;
        double[] result = new double[count];
        int idx = 0;
        for (int i = 0; i < size; i++) {
            if (temperatureSeries[i] < tempValue)
                result[idx++] = temperatureSeries[i];
        }
        return result;
    }

    public double[] findTempsGreaterThan(double tempValue) {
        int count = 0;
        for (int i = 0; i < size; i++)
            if (temperatureSeries[i] >= tempValue)
                count++;
        double[] result = new double[count];
        int idx = 0;
        for (int i = 0; i < size; i++) {
            if (temperatureSeries[i] >= tempValue)
                result[idx++] = temperatureSeries[i];
        }
        return result;
    }

    public double[] findTempsInRange(double lowerBound, double upperBound) {
        int count = 0;
        for (int i = 0; i < size; i++)
            if (lowerBound <= temperatureSeries[i] && temperatureSeries[i] < upperBound)
                count++;
        double[] result = new double[count];
        int idx = 0;
        for (int i = 0; i < size; i++) {
            if (lowerBound <= temperatureSeries[i] && temperatureSeries[i] < upperBound)
                result[idx++] = temperatureSeries[i];
        }
        return result;
    }

    public void reset() {
        this.temperatureSeries = new double[0];
        this.size = 0;
    }

    public double[] sortTemps() {
        double[] copy = Arrays.copyOf(temperatureSeries, size);
        Arrays.sort(copy);
        return copy;
    }

    public TempSummaryStatistics summaryStatistics() {
        if (temperatureSeries == null || size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double avgTemp = average();
        double devTemp = deviation();
        double minTemp = min();
        double maxTemp = max();
        return new TempSummaryStatistics(avgTemp, devTemp, minTemp, maxTemp);
    }

    public int addTemps(double... temps) {
        if (temps == null || temps.length == 0)
            return size;
        for (double t : temps) {
            if (t < -273)
                throw new InputMismatchException();
        }
        int required = size + temps.length;
        if (temperatureSeries.length < required) {
            int newCap = Math.max(Math.max(1, temperatureSeries.length * 2), required);
            double[] newArr = Arrays.copyOf(temperatureSeries, newCap);
            temperatureSeries = newArr;
        }
        for (double t : temps) {
            temperatureSeries[size++] = t;
        }
        return size;
    }
}
