package ua.edu.ucu.tempseries;

import org.junit.Test;
import ua.edu.ucu.apps.tempseries.TemperatureSeriesAnalysis;
import ua.edu.ucu.apps.tempseries.TempSummaryStatistics;
import java.util.InputMismatchException;
import static org.junit.Assert.*;

public class TemperatureSeriesAnalysisTest {

    @Test
    public void testAverageWithOneElementArray() {
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        double actualResult = seriesAnalysis.average();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        seriesAnalysis.average();
    }

    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testDeviation() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = Math.sqrt(14.0);

        double actualResult = analysis.deviation();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testMin() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        assertEquals(-5.0, analysis.min(), 0.00001);
    }

    @Test
    public void testMax() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        assertEquals(5.0, analysis.max(), 0.00001);
    }

    @Test
    public void testFindTempClosestToZero() {
        double[] temperatureSeries = {-0.2, 0.2, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        assertEquals(0.2, analysis.findTempClosestToZero(), 0.00001);
    }

    @Test
    public void testFindTempClosestToValue() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        assertEquals(3.0, analysis.findTempClosestToValue(2.5), 0.00001);
    }

    @Test
    public void testFindTempsLessThan() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        double[] expected = {-5.0, 1.0};
        assertArrayEquals(expected, analysis.findTempsLessThan(2.0), 0.00001);
    }

    @Test
    public void testFindTempsGreaterThan() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        double[] expected = {3.0, 5.0};
        assertArrayEquals(expected, analysis.findTempsGreaterThan(2.0), 0.00001);
    }

    @Test
    public void testFindTempsInRange() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        double[] expected = {1.0, 3.0};
        assertArrayEquals(expected, analysis.findTempsInRange(1.0, 3.0), 0.00001);
    }

    @Test
    public void testSortTemps() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        double[] expected = {-5.0, 1.0, 3.0, 5.0};
        assertArrayEquals(expected, analysis.sortTemps(), 0.00001);
    }

    @Test
    public void testSummaryStatistics() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        TempSummaryStatistics stats = analysis.summaryStatistics();

        assertEquals(1.0, stats.getAvgTemp(), 0.00001);
        assertEquals(Math.sqrt(14.0), stats.getDevTemp(), 0.00001);
        assertEquals(-5.0, stats.getMinTemp(), 0.00001);
        assertEquals(5.0, stats.getMaxTemp(), 0.00001);
    }

    @Test
    public void testAddTemps() {
        double[] temperatureSeries = {1.0, 2.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        int newCount = analysis.addTemps(3.0, 4.0, 5.0);
        assertEquals(5, newCount);
        assertEquals(5.0, analysis.max(), 0.00001);
    }

    @Test
    public void testReset() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

        analysis.reset();
        try {
            analysis.average();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test(expected = InputMismatchException.class)
    public void testConstructorRejectsBelowAbsoluteZero() {
        double[] invalidSeries = {1.0, -280.0};
        new TemperatureSeriesAnalysis(invalidSeries);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSummaryStatisticsEmptyThrows() {
        double[] empty = {};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(empty);

        analysis.summaryStatistics();
    }
}