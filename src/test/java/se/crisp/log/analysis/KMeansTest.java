package se.crisp.log.analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

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

    private boolean aCentroidExistsWithSameValueAsAnother(Set<Centroid> centroids) {
        return centroids.stream().anyMatch(c -> centroids.stream().anyMatch(c2 -> c2.equals(c) && c2 != c));
    }

    @Test
    @DisplayName("Given even spread of samples when assigning samples to nearest centroid then every centroid has at least one sample. ")
    void givenSpreadSamplesWhenAssigningThenEveryCentroidHasSamples() {
        Sample[] samples = linearSamples(SOME_NUMBER_OF_SAMPLES);
        KMeans kMeans = new KMeans(samples, TIGHT_NUMBER_OF_CATEGORIES);

        kMeans.assignSamplesToNearestCentroid();

        boolean anyMatch = kMeans.getCentroids().stream().anyMatch(c -> c.getSamples().size() == 0);
        assertFalse(anyMatch);
    }

    private Sample[] linearSamples(int numberOfSamples) {
        Sample[] result = new TestSample[numberOfSamples];
        for (int n = 0; n < result.length; n++) {
            double payload = ((double) n) / ((double) numberOfSamples);
            result[n] = new TestSample(payload);
        }
        return result;
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
    }

}
