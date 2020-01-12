package se.crisp.log.analysis;

import java.util.*;
import java.util.stream.Collectors;

public class Centroid {
    private Sample reference;
    private Collection<Sample> samples = new ArrayList<>();

    public Centroid(Sample reference) {
        this.reference = reference;
    }

    public Collection<Sample> getSamples() {
        return samples;
    }

    public double distance(Sample sample) {
        return sample.distance(this.reference);
    }

    public Centroid addSample(Sample sample) {
        this.samples.add(sample);
        return this;
    }

    public void clearSamples() {
        samples = new ArrayList<>();
    }

    public boolean centerToSample() {
        Map<Sample, Double> avgDistanceToOther = new HashMap<>();
        for (Sample outer: samples) {
            double distanceSum = 0;
            for (Sample inner: samples) {
                distanceSum += outer.distance(inner);
            }
            avgDistanceToOther.put(outer, distanceSum / samples.size());
        }
        Map.Entry<Sample, Double> first = avgDistanceToOther.entrySet().stream().min(Comparator.comparingDouble(Map.Entry::getValue))
                .orElseThrow(() -> new RuntimeException("No no no"));
        if (this.reference == first.getKey()) {
            return false;
        } else {
            this.reference = first.getKey();
            return true;
        }
    }

    @Override
    public String toString() {
        return "reference: "
                + this.reference.toString() + "\n"
                + this.samples.stream().map(Sample::toString).collect(Collectors.joining(" "))
                + "\n" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Centroid centroid = (Centroid) o;
        return reference.equals(centroid.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }
}
