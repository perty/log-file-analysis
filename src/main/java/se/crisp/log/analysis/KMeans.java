package se.crisp.log.analysis;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class KMeans {
    private Sample[] samples;
    private Set<Centroid> centroids;

    public KMeans(Sample[] samples, int categories) {
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

    public void assignSamplesToNearestCentroid() {
        for (Sample sample : this.samples) {
            assignSampleToNearestCentroid(sample);
        }
    }

    private void assignSampleToNearestCentroid(Sample sample) {
        double shortestDistance = 1.0;
        Optional<Centroid> bestCentroid = Optional.empty();
        for (Centroid centroid : centroids) {
            double currentDistance = centroid.distance(sample);
            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                bestCentroid = Optional.of(centroid);
            }
        }
        bestCentroid.map(c -> c.addSample(sample));
    }
}
