package main;

import java.util.Objects;

public final class ResultatSuppression {
    private final boolean supprime;

    private ResultatSuppression(boolean supprime) {
        this.supprime = supprime;
    }

    public static ResultatSuppression supprime() {
        return new ResultatSuppression(true);
    }

    public static ResultatSuppression introuvable() {
        return new ResultatSuppression(false);
    }

    public boolean estSupprime() {
        return supprime;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResultatSuppression other)) return false;
        return supprime == other.supprime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(supprime);
    }

    @Override
    public String toString() {
        return supprime ? "SUPPRIME" : "INTROUVABLE";
    }
}
