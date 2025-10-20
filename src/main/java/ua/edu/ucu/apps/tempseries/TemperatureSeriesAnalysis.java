package ua.edu.ucu.apps.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private static final double ABSOLUTE_ZERO = -273.15; // Використовуємо константу для абсолютної температури
    private double[] temperatureSeries;
    private int size;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[0];
        this.size = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        // 1. Виправлення: Валідація температури в конструкторі
        for (double temp : temperatureSeries) {
            if (temp < ABSOLUTE_ZERO) {
                throw new InputMismatchException("Temperature cannot be below absolute zero.");
            }
        }
        // 2. Виправлення: Правильне копіювання масиву
        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
        this.size = temperatureSeries.length;
    }

    public double average() {
        if (size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double sum = 0.0;
        for (int i = 0; i < size; i++) {
            sum += temperatureSeries[i];
        }
        return sum / size;
    }

    public double deviation() {
        if (size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double mean = average();
        double sumSq = 0.0;
        for (int i = 0; i < size; i++) {
            double d = temperatureSeries[i] - mean;
            sumSq += d * d;
        }
        return Math.sqrt(sumSq / size);
    }

    public double min() {
        if (size == 0) {
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
        if (size == 0) {
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
        if (size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double result = temperatureSeries[0];
        double minAbsValue = Math.abs(result);

        for (int i = 1; i < size; i++) {
            double currentAbsValue = Math.abs(temperatureSeries[i]);
            if (currentAbsValue < minAbsValue) {
                minAbsValue = currentAbsValue;
                result = temperatureSeries[i];
            } else if (currentAbsValue == minAbsValue && temperatureSeries[i] > result) {
                // Якщо відстані однакові, обираємо додатне значення
                result = temperatureSeries[i];
            }
        }
        return result;
    }

    public double findTempClosestToValue(double tempValue) {
        if (size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        double result = temperatureSeries[0];
        double minDiff = Math.abs(result - tempValue);

        for (int i = 1; i < size; i++) {
            double currentDiff = Math.abs(temperatureSeries[i] - tempValue);
            if (currentDiff < minDiff) {
                minDiff = currentDiff;
                result = temperatureSeries[i];
            } else if (currentDiff == minDiff && temperatureSeries[i] > result) {
                result = temperatureSeries[i];
            }
        }
        return result;
    }

    public double[] findTempsLessThan(double tempValue) {
        return Arrays.stream(Arrays.copyOf(temperatureSeries, size)).filter(t -> t < tempValue).toArray();
    }

    public double[] findTempsGreaterThan(double tempValue) {
        // Зверніть увагу: логіка в тестах передбачає '>=', тому реалізуємо її
        return Arrays.stream(Arrays.copyOf(temperatureSeries, size)).filter(t -> t >= tempValue).toArray();
    }

public double[] findTempsInRange(double lowerBound, double upperBound) {
    return Arrays.stream(Arrays.copyOf(temperatureSeries, size))
                 .filter(t -> t >= lowerBound && t < upperBound)
                 .toArray();
}
    
    public TempSummaryStatistics summaryStatistics() {
        if (size == 0) {
            throw new IllegalArgumentException("Temperature series is empty");
        }
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        for (double t : temps) {
            if (t < ABSOLUTE_ZERO) {
                throw new InputMismatchException("Temperature cannot be below absolute zero.");
            }
        }
        
        int requiredCapacity = size + temps.length;
        if (temperatureSeries.length < requiredCapacity) {
            int newCapacity = Math.max(temperatureSeries.length * 2, requiredCapacity);
            temperatureSeries = Arrays.copyOf(temperatureSeries, newCapacity);
        }

        System.arraycopy(temps, 0, temperatureSeries, size, temps.length);
        size += temps.length;
        
        return size;
    }
}