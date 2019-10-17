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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * Created by alo on 2/15/16.
 */
public class ConceptsWithMoreThanThreeRoleGroups {
    
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        Map<String,Set<String>> groupsMap = new HashMap<String, Set<String>>();
        File relsFile = new File("/Users/alo/Downloads/SnomedCT_RF2Release_INT_20160131-1/Snapshot/Terminology/sct2_Relationship_Snapshot_INT_20160131.txt");
        BufferedReader br2 = new BufferedReader(new FileReader(relsFile));
        String line2;
        int count2 = 0;
        while ((line2 = br2.readLine()) != null) {
            // process the line.
            count2++;
            if (count2 % 10000 == 0) {
                //System.out.println(count2);
            }
            List<String> columns = Arrays.asList(line2.split("\t", -1));
            if (columns.size() >= 6) {
                if (columns.get(2).equals("1") && !columns.get(6).equals("0")) {
                    if (!groupsMap.containsKey(columns.get(4))) {
                        groupsMap.put(columns.get(4), new HashSet<String>());
                    }
                    groupsMap.get(columns.get(4)).add(columns.get(6));
                }
            }
        }
        System.out.println("Relationship groups loaded");
        Gson gson = new Gson();
        System.out.println("Reading JSON 1");
        File crossoverFile1 = new File("/Users/alo/Downloads/crossover_role_to_group.json");
        String contents = FileUtils.readFileToString(crossoverFile1, "utf-8");
        Type collectionType = new TypeToken<Collection<ControlResultLine>>(){}.getType();
        List<ControlResultLine> lineObject = gson.fromJson(contents, collectionType);
        Set<String> crossovers1 = new HashSet<String>();
        for (ControlResultLine loopResult : lineObject) {
            crossovers1.add(loopResult.conceptId);
        }
        System.out.println("Crossovers 1 loaded, " + lineObject.size() + " Objects");

        System.out.println("Reading JSON 2");
        File crossoverFile2 = new File("/Users/alo/Downloads/crossover_group_to_group.json");
        String contents2 = FileUtils.readFileToString(crossoverFile2, "utf-8");
        List<ControlResultLine> lineObject2 = gson.fromJson(contents2, collectionType);
        Set<String> crossovers2 = new HashSet<String>();
        for (ControlResultLine loopResult : lineObject2) {
            crossovers2.add(loopResult.conceptId);
        }
        System.out.println("Crossovers 2 loaded, " + lineObject2.size() + " Objects");

        Set<String> foundConcepts = new HashSet<String>();
        int count3 = 0;
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("ConceptsWithMoreThanThreeRoleGroups.csv")));;
        for (String loopConcept : groupsMap.keySet()) {
            if (groupsMap.get(loopConcept).size() > 3) {
                writer.write(loopConcept);
                writer.newLine();
                foundConcepts.add(loopConcept);
                count3++;
            }
        }
        writer.close();
        System.out.println("Found " + foundConcepts.size() + " concepts");

        int countCrossover1 = 0;
        for (String loopConcept : foundConcepts) {
            if (crossovers1.contains(loopConcept)) {
                countCrossover1++;
            }
        }
        System.out.println(countCrossover1 + " are present in crossover_role_to_group");

        int countCrossover2 = 0;
        for (String loopConcept : foundConcepts) {
            if (crossovers2.contains(loopConcept)) {
                countCrossover2++;
            }
        }
        System.out.println(countCrossover2 + " are present in crossover_group_to_group");

        System.out.println("Done");
    }
}
