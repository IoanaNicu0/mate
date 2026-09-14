package org.fidami.mate;

@FunctionalInterface
public interface NumberGenerator {
    int nextInt(int min, int max);
}