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

    private Sample[] randomSamples(int numberOfCategories) {
        Sample[] result = new TestSample[numberOfCategories];
        for (int n = 0; n < result.length; n++) {
            result[n] = new TestSample(aRandomString());
        }
        return result;
    }

    private String aRandomString() {
        return UUID.randomUUID().toString();
    }

    static class TestSample implements Sample {
        private final String payload;

        TestSample(String payload) {
            this.payload = payload;
        }

        @Override
        public double distance(Sample reference) {
            return Math.random();
        }
    }

}
