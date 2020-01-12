package se.crisp.log.analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class KMeansTest {

    private static final int TIGHT_NUMBER_OF_CATEGORIES = 12;
    private static final int SOME_NUMBER_OF_SAMPLES = TIGHT_NUMBER_OF_CATEGORIES * 10;

    @Test
    @DisplayName("When initializing, place centroids at different coordinates.")
    @Timeout(1)
    void givenStringsWhenInitThenPlaceCentroidsDifferently() {
        Sample[] samples = linearSamples(TIGHT_NUMBER_OF_CATEGORIES);

        KMeans kMeans = new KMeans(samples, TIGHT_NUMBER_OF_CATEGORIES);

        Set<Centroid> centroids = kMeans.getCentroids();

        assertEquals(TIGHT_NUMBER_OF_CATEGORIES, centroids.size());
        assertFalse(aCentroidExistsWithSameValueAsAnother(centroids));
    }

    @Test
    @DisplayName("Given even spread of samples when assigning samples to nearest centroid then every centroid has at least one sample. ")
    void givenSpreadSamplesWhenAssigningThenEveryCentroidHasSamples() {
        Sample[] samples = linearSamples(SOME_NUMBER_OF_SAMPLES);
        KMeans kMeans = new KMeans(samples, TIGHT_NUMBER_OF_CATEGORIES);

        kMeans.assignSamplesToNearestCentroid();

        boolean anyMatch = kMeans.getCentroids().stream().anyMatch(c -> kMeans.getCentroidSamplesSize(c) == 0);
        assertFalse(anyMatch);
    }

    @Test
    @DisplayName("Given samples prepared for categories")
    void givenSamplesPreparedForCategoriesWhenIteratingThenPlaceCentroidsCorrectly() {
        List<Sample> sampleList = samplesInRange(0.0);
        sampleList.addAll(samplesInRange(200));
        sampleList.addAll(samplesInRange(800));
        sampleList.addAll(samplesInRange(6000));

        Sample[] samples = sampleList.toArray(new Sample[0]);

        KMeans kMeans = new KMeans(samples, 4);
        kMeans.solve();

        Set<Centroid> centroids = kMeans.getCentroids();
        centroidsHasCoveredAllSamples(sampleList.size(), centroids);
        //allCentroidsHave10Samples(centroids);
    }

    private void centroidsHasCoveredAllSamples(int expectedSum, Set<Centroid> centroids) {
        int sum = centroids.stream().mapToInt(c -> c.getSamples().size()).sum();
        assertEquals(expectedSum, sum);
    }

    private void allCentroidsHave10Samples(Set<Centroid> centroids) {
        assertFalse(centroids.stream().anyMatch(c -> c.getSamples().size() != 10));
    }

    private List<Sample> samplesInRange(double startValue) {
        List<Sample> result = new ArrayList<>();
        for (int n = 0; n < 10; n++) {
            result.add(new TestSample(startValue + 0.05 * n));
        }
        return result;
    }

    private Sample[] linearSamples(int numberOfSamples) {
        Sample[] result = new TestSample[numberOfSamples];
        for (int n = 0; n < result.length; n++) {
            double payload = ((double) n) / ((double) numberOfSamples);
            result[n] = new TestSample(payload);
        }
        return result;
    }

    private boolean aCentroidExistsWithSameValueAsAnother(Set<Centroid> centroids) {
        return centroids.stream().anyMatch(c -> centroids.stream().anyMatch(c2 -> c2.equals(c) && c2 != c));
    }

    static class TestSample implements Sample {
        private final double payload;

        TestSample(double payload) {
            this.payload = payload;
        }

        @Override
        public double distance(Sample reference) {
            if (reference instanceof TestSample) {
                return Math.abs(payload - ((TestSample) reference).payload);
            }
            return 1;
        }

        @Override
        public String toString() {
            return String.format("%4.3f", payload);
        }
    }

}
