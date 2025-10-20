package ua.edu.ucu.tempseries;

import org.junit.Before;
import org.junit.Test;
import ua.edu.ucu.apps.tempseries.TempSummaryStatistics;
import ua.edu.ucu.apps.tempseries.TemperatureSeriesAnalysis;

import java.util.Arrays;
import java.util.InputMismatchException;

import static org.junit.Assert.*;

public class TemperatureSeriesAnalysisTest {

    private TemperatureSeriesAnalysis seriesAnalysis;
    private TemperatureSeriesAnalysis seriesWithOneElement;
    private TemperatureSeriesAnalysis emptySeriesAnalysis;

    @Before
    public void setUp() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

        double[] oneElementSeries = {10.0};
        seriesWithOneElement = new TemperatureSeriesAnalysis(oneElementSeries);

        emptySeriesAnalysis = new TemperatureSeriesAnalysis();
    }

    @Test(expected = InputMismatchException.class)
    public void testConstructor_WithTempBelowAbsoluteZero_ShouldThrowException() {
        double[] invalidSeries = {1.0, -300.0, 5.0};
        new TemperatureSeriesAnalysis(invalidSeries);
    }

    @Test
    public void testConstructor_WithEmptyArray() {
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(new double[]{});
        assertEquals(0, analysis.addTemps());
    }

    @Test
    public void testAverage_WithMultipleElements() {
        double expResult = 1.0;
        double actualResult = seriesAnalysis.average();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testAverage_WithOneElement() {
        double expResult = 10.0;
        double actualResult = seriesWithOneElement.average();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverage_WithEmptySeries_ShouldThrowException() {
        emptySeriesAnalysis.average();
    }

    @Test
    public void testDeviation_WithMultipleElements() {
        double expResult = Math.sqrt(14.0);
        double actualResult = seriesAnalysis.deviation();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testDeviation_WithOneElement() {
        assertEquals(0.0, seriesWithOneElement.deviation(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeviation_WithEmptySeries_ShouldThrowException() {
        emptySeriesAnalysis.deviation();
    }

    @Test
    public void testMin() {
        assertEquals(-5.0, seriesAnalysis.min(), 0.00001);
    }

    @Test
    public void testMax() {
        assertEquals(5.0, seriesAnalysis.max(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMin_WithEmptySeries_ShouldThrowException() {
        emptySeriesAnalysis.min();
    }

    @Test
    public void testFindTempClosestToZero() {
        double[] temps = {3.0, -1.0, 1.5, 5.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temps);
        assertEquals(-1.0, analysis.findTempClosestToZero(), 0.00001);
    }

    @Test
    public void testFindTempClosestToZero_WithTie_ShouldReturnPositive() {
        double[] temps = {-0.5, 0.5, 10.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temps);
        assertEquals(0.5, analysis.findTempClosestToZero(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToZero_WithEmptySeries_ShouldThrowException() {
        emptySeriesAnalysis.findTempClosestToZero();
    }

    @Test
    public void testFindTempClosestToValue() {
        assertEquals(3.0, seriesAnalysis.findTempClosestToValue(2.5), 0.00001);
    }
    
    @Test
    public void testFindTempClosestToValue_WithTie_ShouldReturnLarger() {
        double[] temps = {2.0, 6.0};
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temps);
        assertEquals(6.0, analysis.findTempClosestToValue(4.0), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindTempClosestToValue_WithEmptySeries_ShouldThrowException() {
        emptySeriesAnalysis.findTempClosestToValue(10.0);
    }

    @Test
    public void testFindTempsLessThan() {
        double[] expected = {-5.0};
        assertArrayEquals(expected, seriesAnalysis.findTempsLessThan(1.0), 0.00001);
    }

    @Test
    public void testFindTempsGreaterThan() {
        double[] expected = {3.0, 5.0};
        assertArrayEquals(expected, seriesAnalysis.findTempsGreaterThan(3.0), 0.00001);
    }

@Test
public void testFindTempsInRange_LowerInclusive_UpperExclusive() {
    double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0, 4.0};
    TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(temperatureSeries);

    double[] expected = {1.0, 3.0};
    double[] actual = analysis.findTempsInRange(1.0, 4.0);

    Arrays.sort(expected);
    Arrays.sort(actual);

    assertArrayEquals(expected, actual, 0.00001);
}

    @Test
    public void testSummaryStatistics() {
        TempSummaryStatistics stats = seriesAnalysis.summaryStatistics();
        assertEquals(1.0, stats.getAvgTemp(), 0.00001);
        assertEquals(Math.sqrt(14.0), stats.getDevTemp(), 0.00001);
        assertEquals(-5.0, stats.getMinTemp(), 0.00001);
        assertEquals(5.0, stats.getMaxTemp(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSummaryStatistics_WithEmptySeries_ShouldThrowException() {
        emptySeriesAnalysis.summaryStatistics();
    }

    @Test
    public void testAddTemps_ToNonEmptySeries() {
        int initialSize = seriesAnalysis.addTemps();
        assertEquals(4, initialSize);

        int newSize = seriesAnalysis.addTemps(10.0, 20.0);
        assertEquals(6, newSize);
        assertEquals(20.0, seriesAnalysis.max(), 0.00001);
    }

    @Test
    public void testAddTemps_ToEmptySeries() {
        int newSize = emptySeriesAnalysis.addTemps(1.0, 2.0, 3.0);
        assertEquals(3, newSize);
        assertEquals(2.0, emptySeriesAnalysis.average(), 0.00001);
    }

    @Test
    public void testAddTemps_RequiresResize() {
        TemperatureSeriesAnalysis analysis = new TemperatureSeriesAnalysis(new double[]{1.0});
        int newSize = analysis.addTemps(2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
        assertEquals(10, newSize);
        assertEquals(10.0, analysis.max(), 0.00001);
    }

    @Test(expected = InputMismatchException.class)
    public void testAddTemps_WithInvalidTemp_ShouldThrowException() {
        seriesAnalysis.addTemps(10.0, -400.0);
    }
}