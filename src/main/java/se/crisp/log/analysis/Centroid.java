package se.crisp.log.analysis;

import java.util.Objects;

public class Centroid {
    private String reference;

    public Centroid(String reference) {
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
}
