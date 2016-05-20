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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ConceptGroup.
 * Represents a concept group
 */
public class ConceptGroup extends ArrayList<Concept> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new concept group.
     *
     * @param conceptList the concepts list
     * @param sort true if the list needs to be sorted
     */
    public ConceptGroup(List<Concept> conceptList, boolean sort) {
        super();
        if (sort)
            Collections.sort(conceptList);
        this.addAll(conceptList);
    }

    /**
     * Instantiates a new concept group.
     *
     * @param conceptCollection the concepts list a Strings collection
     */
    public ConceptGroup(Collection<String> conceptCollection) {
        super();
        for (String conceptId : conceptCollection)
            this.add(new Concept(toInteger(conceptId), false));
        Collections.sort(this);
    }

    /**
     * Instantiates a new concept group.
     *
     * @param concepts the concepts list a Integer Array
     */
    public ConceptGroup(ArrayList<Integer> concepts) {
        super();
        // :NYI: defined or not_defined is indeterminate coming from classifier
        // callback.
        for (Integer concept : concepts)
            this.add(new Concept(concept.intValue(), false));
        Collections.sort(this);
    }

    /**
     * Instantiates a new concept group.
     *
     * @param concept a concept
     */
    public ConceptGroup(Concept concept) {
        super();
        this.add(concept); //
    }

    /**
     * Instantiates a new concept group.
     */
    public ConceptGroup() {
        super();
    }

    /**
     * toInteger. Transforms the String value to Integer
     *
     * @param id the string value
     * @return the integer value
     */
    static private int toInteger(final String id) {
        return Integer.parseInt(String.valueOf(id));
    }

}

