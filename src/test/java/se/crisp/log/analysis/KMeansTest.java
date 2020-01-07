package se.crisp.log.analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KMeansTest {

    private static final int SOME_NUMBER_OF_CATEGORIES = 12;

    @Test
    @DisplayName("Given strings when initializing then place centroids at different coordinates.")
    @Timeout(1)
    void givenStringsWhenInitThenPlaceCentroidsDifferently() throws InterruptedException {
        String[] samples = givenRandomStrings();

        KMeans kMeans = new KMeans(samples, SOME_NUMBER_OF_CATEGORIES);

        Set<Centroid> centroids = kMeans.getCentroids();

        assertEquals(centroids.size(), SOME_NUMBER_OF_CATEGORIES);
    }

    private String[] givenRandomStrings() throws InterruptedException {
        String[] result = new String[SOME_NUMBER_OF_CATEGORIES];
        for (int n = 0; n < result.length; n++) {
            result[n] = aRandomString();
        }
        return result;
    }

    private String aRandomString() throws InterruptedException {
        Thread.sleep(1);
        return Date.from(Instant.now()).getTime() + "";
    }
}
