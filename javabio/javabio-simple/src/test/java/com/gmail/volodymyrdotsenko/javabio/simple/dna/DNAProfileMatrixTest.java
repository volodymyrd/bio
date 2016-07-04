package com.gmail.volodymyrdotsenko.javabio.simple.dna;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by Volodymyr Dotsenko on 7/3/16.
 */
public class DNAProfileMatrixTest {

    @Test
    public void shouldCreateProfileFromMotifs() {
        List<String> motifs = Stream
                .of("TCGGGGGTTTTT", "CCGGTGACTTAC", "ACGGGGATTTTC", "TTGGGGACTTTT", "AAGGGGACTTCC",
                        "TTGGGGACTTCC", "TCGGGGATTCAT", "TCGGGGATTCCT", "TAGGGGAACTAC", "TCGGGTATAACC")
                .collect(Collectors.toList());

        DNAProfileMatrix profile = new MotifsHolder(12, motifs).getProfileMatrix();

        assertEquals("A: 0.2, 0.2, 0.0, 0.0, 0.0, 0.0, 0.9, 0.1, 0.1, 0.1, 0.3, 0.0 C: 0.1, 0.6, 0.0, 0.0, 0.0, 0.0, 0.0, 0.4, 0.1, 0.2, 0.4, 0.6 G: 0.0, 0.0, 1.0, 1.0, 0.9, 0.9, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0 T: 0.7, 0.2, 0.0, 0.0, 0.1, 0.1, 0.0, 0.5, 0.8, 0.7, 0.3, 0.4 ",
                profile.toString());
    }
}