package se.crisp.log.analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class KMeansTest {

    private static final int TIGHT_NUMBER_OF_CATEGORIES = 12;

    @Test
    @DisplayName("When initializing, place centroids at different coordinates.")
    @Timeout(1)
    void givenStringsWhenInitThenPlaceCentroidsDifferently() {
        Sample[] samples = randomSamples(TIGHT_NUMBER_OF_CATEGORIES);

        KMeans kMeans = new KMeans(samples, TIGHT_NUMBER_OF_CATEGORIES);

        Set<Centroid> centroids = kMeans.getCentroids();

        assertEquals(TIGHT_NUMBER_OF_CATEGORIES, centroids.size());
    }

    @Test
    @DisplayName("When iterating, assign samples to nearest centroid")
    void whenIteratingAssignSamplesToNearestCentroid() {
        Sample[] samples = randomSamples(TIGHT_NUMBER_OF_CATEGORIES * 10);
        KMeans kMeans = new KMeans(samples, TIGHT_NUMBER_OF_CATEGORIES);

        kMeans.assignSamplesToNearestCentroid();

        boolean anyMatch = kMeans.getCentroids().stream().anyMatch(c -> c.getSamples().size() == 0);
        assertFalse(anyMatch);
    }

    private Sample[] randomSamples(int numberOfSamples) {
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
