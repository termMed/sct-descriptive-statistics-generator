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
package com.termmed.inheritance.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Relationship.
 * Represents a relationship.
 */
public class Relationship implements Comparable<Object> {

    /** The relationship Id. */
    public String relationshipId;
    
    /** The source Id. */
    public Long sourceId; 
    
    /** The destination Id. */
    public Long destinationId;
    
    /** The type id. */
    public Long typeId;
    
    /** The group. */
    public int group;

    // Relationship form a versioned "new" database perspective
    /**
     * Instantiates a new sno rel.
     *
     * @param c1 the c1 id
     * @param c2 the c2 id
     * @param ty the role type id
     * @param group the group
     * @param relationshipId the rel nid
     */
    public Relationship(Long c1, Long c2, Long ty, int group, String relationshipId) {
        this.sourceId = c1;
        this.destinationId = c2;
        this.typeId = ty;
        this.group = group;
        this.relationshipId = relationshipId;
    }

    // Relationship from a SnoRocket perspective
    /**
     * Instantiates a new sno rel.
     *
     * @param sourceId the c1 id
     * @param destinationId the c2 id
     * @param roleTypeId the role type id
     * @param group the group
     */
    public Relationship(Long sourceId, Long destinationId, Long roleTypeId, int group) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.typeId = roleTypeId;
        this.group = group;
        this.relationshipId = "null";
    }

    /**
     * Gets the rel id.
     *
     * @return the rel id
     */
    public String getRelId() {
        return relationshipId;
    }

    /**
     * Sets the nid.
     *
     * @param nid the new nid
     */
    public void setNid(String nid) {
        this.relationshipId = nid;
    }

    // default sort order [c1-group-type-c2]
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        Relationship other = (Relationship) o;
        int thisMore = 1;
        int thisLess = -1;
        if (this.sourceId > other.sourceId) {
            return thisMore;
        } else if (this.sourceId < other.sourceId) {
            return thisLess;
        } else {
            if (this.group > other.group) {
                return thisMore;
            } else if (this.group < other.group) {
                return thisLess;
            } else {
                if (this.typeId > other.typeId) {
                    return thisMore;
                } else if (this.typeId < other.typeId) {
                    return thisLess;
                } else {
                    if (this.destinationId > other.destinationId) {
                        return thisMore;
                    } else if (this.destinationId < other.destinationId) {
                        return thisLess;
                    } else {
                        return 0; // this == received
                    }
                }
            }
        }
    } // Relationship.compareTo()

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(sourceId);
        sb.append(": ");
        sb.append(typeId);
        sb.append(": ");
        sb.append(destinationId);
        return sb.toString();
    }

    /**
     * To string c1.
     *
     * @return the string
     */
    public String toStringC1() {
       
        return Long.toString(sourceId);
    }

    /**
     * To string type.
     *
     * @return the string
     */
    public String toStringType() {
       
        return Long.toString(typeId);
    }

    /**
     * To string c2.
     *
     * @return the string
     */
    public String toStringC2() {
       
        return Long.toString(destinationId);
    }

    /**
     * To string group.
     *
     * @return the string
     */
    public String toStringGroup() {
        return Integer.toString(group);
    }


    /**
     * To string4 file.
     *
     * @return the string
     */
    public String toString4File() {
        return relationshipId + "\t" + sourceId + "\t" + destinationId + "\t" + typeId + "\t" + group;
    }

    /**
     * Dump to file.
     *
     * @param srl the srl
     * @param fName the f name
     * @param format the format
     */
    public static void dumpToFile(List<Relationship> srl, String fName, int format) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(fName));
            if (format == 1) { // RAW NIDs
                for (Relationship sr : srl) {
                    bw.write(sr.sourceId + "\t" + sr.typeId + "\t" + sr.destinationId + "\t" + sr.group + "\r\n");
                }
            }
            
            if (format == 6) { // Distribution Form
                int index = 0;
                bw.write("RELATIONSHIPID\t" + "CONCEPTID1\t" + "RELATIONSHIPTYPE\t"
                        + "CONCEPTID2\t" + "CHARACTERISTICTYPE\t" + "REFINABILITY\t"
                        + "RELATIONSHIPGROUP\r\n");
                for (Relationship sr : srl) {
                    // RELATIONSHIPID + CONCEPTID1 + RELATIONSHIPTYPE +
                    // CONCEPTID2
                    bw.write("#" + index + "\t" + sr.sourceId + "\t" + sr.typeId + "\t" + sr.destinationId
                            + "\t");
                    // CHARACTERISTICTYPE + REFINABILITY + RELATIONSHIPGROUP
                    bw.write("NA\t" + "NA\t" + sr.group + "\r\n");
                    index += 1;
                }
            }
            bw.flush();
            bw.close();
        
        } catch (IOException e) {
            // can be caused by new FileWriter
            Logger.getLogger(Relationship.class.getName()).log(Level.SEVERE, null, e);
        }
    }
} // class Relationship


