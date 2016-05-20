/**
 * Copyright (c) 2016 TermMed SA
 * Organization
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

/**
 * Author: Alejandro Rodriguez
 */
package com.termmed.sampling;

import java.io.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * Created by alo on 2/15/16.
 */
public class ConceptsWithMoreThanTwoSynonyms {
    
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        File conceptsFile = new File("/Users/alo/Downloads/SnomedCT_RF2Release_INT_20160131-1/Snapshot/Terminology/sct2_Concept_Snapshot_INT_20160131.txt");
        BufferedReader br = new BufferedReader(new FileReader(conceptsFile));
        String line;
        Set<String> allActiveConcepts = new HashSet<String>();
        int count = 0;
        br.readLine();
        while ((line = br.readLine()) != null) {
            // process the line.
            List<String> columns = Arrays.asList(line.split("\t", -1));
            if (columns.size() >= 2 && columns.get(2).equals("1") ) {
                allActiveConcepts.add(columns.get(0));
            }
        }
        System.out.println("All active concepts loaded");
        File langFile = new File("/Users/alo/Downloads/SnomedCT_RF2Release_INT_20160131-1/Snapshot/Refset/Language/der2_cRefset_LanguageSnapshot-en_INT_20160131.txt");
        BufferedReader br3 = new BufferedReader(new FileReader(langFile));
        String line3;
        Set<String> allLanguageDescriptions = new HashSet<String>();
        int count3 = 0;
        br.readLine();
        while ((line = br3.readLine()) != null) {
            // process the line.
            List<String> columns = Arrays.asList(line.split("\t", -1));
            if (columns.size() >= 2 && columns.get(2).equals("1") &&
                    columns.get(4).equals("900000000000509007")) {
                allLanguageDescriptions.add(columns.get(5));
            }
        }
        System.out.println("All language refset lines");
        File descsFile = new File("/Users/alo/Downloads/SnomedCT_RF2Release_INT_20160131-1/Snapshot/Terminology/sct2_Description_Snapshot-en_INT_20160131.txt");
        BufferedReader br2 = new BufferedReader(new FileReader(descsFile));
        String line2;
        int count2 = 0;
        Map<String,Integer> descsCounts = new HashMap<String,Integer>();
        br.readLine();
        while ((line2 = br2.readLine()) != null) {
            // process the line.
            count2++;
            if (count2 % 10000 == 0) {
                //System.out.println(count2);
            }
            List<String> columns = Arrays.asList(line2.split("\t", -1));
            if (columns.size() >= 6 && allLanguageDescriptions.contains(columns.get(0))) {
//                if (columns.get(4).equals("714539008")) {
//                    System.out.println("Found...");
//                }
                if (columns.get(2).equals("1") && columns.get(6).equals("900000000000013009")) {
                    if (descsCounts.containsKey(columns.get(4))) {
                        int newDescsCount = descsCounts.get(columns.get(4)) + 1;
                        descsCounts.put(columns.get(4),newDescsCount);
                    } else {
                        descsCounts.put(columns.get(4),1);
                    }
                }
            }
        }
        System.out.println("Description counts loaded");

        int count4 = 0;
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("ConceptsWithMoreThanTwoSynonyms.csv")));;
        for (String loopConcept : allActiveConcepts) {
            if (descsCounts.containsKey(loopConcept)) {
                if (descsCounts.get(loopConcept) > 4) {
                    writer.write(loopConcept);
                    writer.newLine();
                    count4++;
                }
            } else {
                System.out.println("Missing concept " + loopConcept);
            }

        }
        writer.close();
        System.out.println("Done, found " + count4 + " concepts");
    }
}
