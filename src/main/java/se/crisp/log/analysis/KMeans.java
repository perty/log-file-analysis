package se.crisp.log.analysis;

import java.util.HashSet;
import java.util.Set;

public class KMeans {
    private String[] samples;
    private Set<Centroid> centroids;

    public KMeans(String[] samples, int categories) {
        this.samples = samples;
        placeCentroids(categories);
    }

    public Set<Centroid> getCentroids() {
        return centroids;
    }

    public void setCentroids(Set<Centroid> centroids) {
        this.centroids = centroids;
    }

    private void placeCentroids(int categories) {
        setCentroids(new HashSet<>());
        for (int n = 0; n < categories; n++) {
            while (true) {
                float random01 = (float) Math.random();
                int index = Math.round(random01 * samples.length);
                if (index < samples.length) {
                    Centroid centroid = new Centroid(samples[index]);
                    if (!centroids.contains(centroid)) {
                        centroids.add(centroid);
                        break;
                    }
                }
            }
        }
    }
}
