package se.crisp.log.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Centroid {
    private Sample reference;
    private Collection<Object> samples = new ArrayList<>();

    public Centroid(Sample reference) {
        this.reference = reference;
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

    public Collection<Object> getSamples() {
        return samples;
    }

    public double distance(Sample sample) {
        return sample.distance(this.reference);
    }

    public Centroid addSample(Sample sample) {
        this.samples.add(sample);
        return this;
    }
}
