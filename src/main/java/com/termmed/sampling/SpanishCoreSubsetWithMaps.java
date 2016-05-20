package com.termmed.sampling;

import java.io.*;
import java.util.*;

/**
 * Created by alo on 2/15/16.
 */
public class SpanishCoreSubsetWithMaps {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        List<String> coreSubsetIds = new ArrayList<String>();
        File coreSubsetFile = new File("/Users/alo/Downloads/SNOMEDCT_CORE_SUBSET_201508/SNOMEDCT_CORE_SUBSET_201508.txt");
        BufferedReader coreSubsetBr = new BufferedReader(new FileReader(coreSubsetFile));
        String coreSubsetLine;
        coreSubsetBr.readLine(); // Skip header
        while ((coreSubsetLine = coreSubsetBr.readLine()) != null) {
            String[] columns = coreSubsetLine.split("\\|");
            if (columns[2].equals("Current") && columns[7].equals("False")) {
                coreSubsetIds.add(columns[0]);
            }
        }
        System.out.println("Core Subset loaded: " + coreSubsetIds.size());
        class SpanishCoreLine {
            String descriptionId;
            String conceptId;
            String term;
            String ICD10 = "";
            String ICPC2 = "";

            public String getICD10() {
                return ICD10;
            }

            public void setICD10(String ICD10) {
                this.ICD10 = ICD10;
            }

            public String getICPC2() {
                return ICPC2;
            }

            public void setICPC2(String ICPC2) {
                this.ICPC2 = ICPC2;
            }

            public SpanishCoreLine(String descriptionId, String conceptId, String term) {
                this.descriptionId = descriptionId;
                this.conceptId = conceptId;
                this.term = term;
            }
        }
        List<SpanishCoreLine> spanishLines = new ArrayList<SpanishCoreLine>();
        File spanishDescriptionsFile = new File("/Users/alo/Downloads/SnomedCT_SpanishRelease-es_INT_20151031/RF2Release/Snapshot/Terminology/sct2_Description_SpanishExtensionSnapshot-es_INT_20151031.txt");
        BufferedReader spanishDescriptionsBr = new BufferedReader(new FileReader(spanishDescriptionsFile));
        String spanishDescriptionsLine;
        spanishDescriptionsBr.readLine(); // Skip header
        int spanishDescriptionsCount = 0;
        while ((spanishDescriptionsLine = spanishDescriptionsBr.readLine()) != null) {
            spanishDescriptionsCount++;
            if (spanishDescriptionsCount % 10000 == 0) {
                System.out.print(".");
            }
            List<String> columns = Arrays.asList(spanishDescriptionsLine.split("\t", -1));
            if (columns.get(2).equals("1") && columns.get(6).equals("900000000000013009")) {
                if (coreSubsetIds.contains(columns.get(4))) {
                    SpanishCoreLine loopLine = new SpanishCoreLine(columns.get(0),columns.get(4),columns.get(7));
                    spanishLines.add(loopLine);
                }
            }
        }
        System.out.println(".");
        System.out.println("Spanish descriptions loaded: " + spanishLines.size());

        Map<String,String> icd10Map = new HashMap<String, String>();
        File icd10MapsFile = new File("/Users/alo/Downloads/der2_iisssccRefset_ComplexMapCorrected_INT_20130731 (3)/der2_iisssccRefset_ComplexMapSnapshot_INT_20130731.txt");
        BufferedReader icd10MapsBr = new BufferedReader(new FileReader(icd10MapsFile));
        String icd10MapsLine;
        icd10MapsBr.readLine(); // Skip header
        int icd10MapsCount = 0;
        while ((icd10MapsLine = icd10MapsBr.readLine()) != null) {
            icd10MapsCount++;
            if (icd10MapsCount % 1000 == 0) {
                System.out.print(".");
            }
            List<String> columns = Arrays.asList(icd10MapsLine.split("\t", -1));
            if (columns.get(2).equals("1")) {
                if (coreSubsetIds.contains(columns.get(5)) &&
                        (columns.get(8).equals("TRUE") || columns.get(8).equals("OTHERWISE TRUE"))) {
                    icd10Map.put(columns.get(5), columns.get(10));
                }
            }
        }
        System.out.println(".");
        System.out.println("ICD10 Map loaded: " + icd10Map.size());

        Map<String,String> icpcMap = new HashMap<String, String>();
        File icpcMapsFile = new File("/Users/alo/Downloads/SnomedCT_GPFPICPC2_Baseline_INT_20150930/RF2Release/Snapshot/Refset/Map/xder2_iisssccRefset_ICPC2ExtendedMapSnapshot_INT_20150930.txt");
        BufferedReader icpcMapsBr = new BufferedReader(new FileReader(icpcMapsFile));
        String icpcMapsLine;
        icpcMapsBr.readLine(); // Skip header
        int icpcMapsCount = 0;
        while ((icpcMapsLine = icpcMapsBr.readLine()) != null) {
            icpcMapsCount++;
            if (icpcMapsCount % 100 == 0) {
                System.out.print(".");
            }
            List<String> columns = Arrays.asList(icpcMapsLine.split("\t", -1));
            //System.out.println(columns.get(5) + " " + columns.get(8));
            if (columns.get(2).equals("1")) {
                if (coreSubsetIds.contains(columns.get(5)) &&
                        (columns.get(8).equals("TRUE") || columns.get(8).equals("OTHERWISE TRUE"))) {
                    icpcMap.put(columns.get(5), columns.get(10));
                }
            }
        }
        System.out.println(".");
        System.out.println("ICPC2 Map loaded: " + icpcMap.size());

        for (SpanishCoreLine loopSpanishLine : spanishLines) {
            loopSpanishLine.setICD10(icd10Map.get(loopSpanishLine.conceptId));
            loopSpanishLine.setICPC2(icpcMap.get(loopSpanishLine.conceptId));
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("CoreSubsetSpanishMaps.txt")));
        writer.write("descriptionId\tconceptId\tterm\ticd10\ticpc");
        writer.newLine();
        for (SpanishCoreLine loopSpanishLine : spanishLines) {
            if (icd10Map.containsKey(loopSpanishLine.conceptId)) {
                loopSpanishLine.setICD10(icd10Map.get(loopSpanishLine.conceptId));
            }
            if (icpcMap.containsKey(loopSpanishLine.conceptId)) {
                loopSpanishLine.setICPC2(icpcMap.get(loopSpanishLine.conceptId));
            }
            writer.write(loopSpanishLine.descriptionId);
            writer.write("\t");
            writer.write(loopSpanishLine.conceptId);
            writer.write("\t");
            writer.write(loopSpanishLine.term);
            writer.write("\t");
            if (loopSpanishLine.getICD10() != null) {
                writer.write(loopSpanishLine.getICD10());
            }
            writer.write("\t");
            if (loopSpanishLine.getICPC2() != null) {
                writer.write(loopSpanishLine.getICPC2());
            }
            writer.newLine();
        }
        writer.close();
        System.out.println("File write finished");
        System.out.println("Done!");
    }
}
