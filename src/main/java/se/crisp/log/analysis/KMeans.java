package se.crisp.log.analysis;


import java.util.HashSet;
import java.util.Set;

public class KMeans {
    private Sample[] samples;
    private Set<Centroid> centroids;
    private int iterations;

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

    public void solve() {
        this.iterations = 0;
        for (boolean centroidMoved = true; centroidMoved; centroidMoved = centerCentroidsToSamples()) {
            centroids.forEach(Centroid::clearSamples);
            assignSamplesToNearestCentroid();
            this.iterations++;
        }
    }

    private boolean centerCentroidsToSamples() {
        boolean changed = false;
        for (Centroid centroid : centroids) {
            if (centroid.centerToSample()) {
                changed = true;
            }
        }
        return changed;
    }

    void assignSamplesToNearestCentroid() {
        for (Sample sample : this.samples) {
            assignSampleToNearestCentroid(sample);
        }
    }


    private void assignSampleToNearestCentroid(Sample sample) {
        Centroid bestCentroid = findBestCentroid(sample);
        bestCentroid.addSample(sample);
    }

    private Centroid findBestCentroid(Sample sample) {
        double shortestDistance = 1.0;
        Centroid bestCentroid = centroids.iterator().next();
        for (Centroid centroid : centroids) {
            double currentDistance = centroid.distance(sample);
            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                bestCentroid = centroid;
            }
        }
        return bestCentroid;
    }

    public int getCentroidSamplesSize(Centroid c) {
        return c.getSamples().size();
    }
}
