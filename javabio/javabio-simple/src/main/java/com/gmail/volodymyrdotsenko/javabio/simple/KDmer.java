package com.gmail.volodymyrdotsenko.javabio.simple;

/**
 * Represent a (k,d)-mer is a pair of k-mers in Text separated by distance d
 * We use the notation (Pattern1|Pattern2) to refer to a (k,d)-mer whose k-mers are Pattern1 and Pattern2.
 * <p>
 * For example, (AAT|TGG) is a (3,4)-mer in TAATGCCATGGGATGTT
 * <p>
 * Created by Volodymyr Dotsenko on 8/2/16.
 */
public class KDmer implements Comparable<KDmer> {

    private final String pattern1;
    private final String pattern2;

    public KDmer(String pattern1, String pattern2) {
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
    }

    public KDmer getPrefix() {
        return new KDmer(SequencesUtil.prefix(pattern1), SequencesUtil.prefix(pattern2));
    }

    public KDmer getSuffix() {
        return new KDmer(SequencesUtil.suffix(pattern1), SequencesUtil.suffix(pattern2));
    }

    @Override
    public int compareTo(KDmer o) {
        if (pattern1.equals(o.pattern1))
            return pattern2.compareTo(o.pattern2);
        else
            return pattern1.compareTo(o.pattern1);
    }

    @Override
    public String toString() {
        return "(" + pattern1 + "|" + pattern2 + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KDmer kDmer = (KDmer) o;

        if (!pattern1.equals(kDmer.pattern1)) return false;
        return pattern2.equals(kDmer.pattern2);

    }

    @Override
    public int hashCode() {
        int result = pattern1.hashCode();
        result = 31 * result + pattern2.hashCode();
        return result;
    }
}