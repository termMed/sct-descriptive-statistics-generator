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
import java.util.Iterator;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class RelationshipGroupList.
 * Represents a relationship group list
 */
public class RelationshipGroupList extends ArrayList<RelationshipGroup> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new sno grp list.
     */
    public RelationshipGroupList() {
        super();
    }

    /**
     * Construct a ROLE_GROUP_LIST from <code>List&lt;Relationship&gt;</code><br>
     * <br>
     * <font color=#990099> IMPLEMENTATION NOTE: roleGroups MUST be pre-sorted
     * in C1-Group-Type-C2 order for this routine. Pre-sorting is used to
     * provide overall computational efficiency.</font>
     *
     * @param rels the rels
     */
    public RelationshipGroupList(List<Relationship> rels) {
        super();
        // First Group
        RelationshipGroup group = new RelationshipGroup();
        this.add(group);

        // First Relationship in First Group
        Iterator<Relationship> it = rels.iterator();
        Relationship relationshipA = it.next();
        group.add(relationshipA);

        while (it.hasNext()) {
            Relationship relationshipB = it.next();
            if (relationshipB.group == relationshipA.group) {
                group.add(relationshipB); // ADD TO SAME GROUP
            } else {
                group = new RelationshipGroup(); // CREATE NEW GROUP
                this.add(group); // ADD GROUP TO GROUP LIST
                group.add(relationshipB);
            }
            relationshipA = relationshipB;
        }
    }

    /**
     * Count rels.
     *
     * @return the int
     */
    public int countRels() {
        int returnCount = 0;
        for (RelationshipGroup sg : this)
            returnCount += sg.size();
        return returnCount;
    }

   
    /**
     * Which groups in this do not have ANY equal group in groupListB?<br>
     * <br>
     * <font color=#990099> IMPLEMENTATION NOTE: roleGroups MUST be pre-sorted
     * in C1-Group-Type-C2 order for this routine. Pre-sorting is used to
     * provide overall computational efficiency.</font>
     *
     * @param groupListB the group list b
     * @return <code>RelationshipGroupList</code>
     */
    public RelationshipGroupList whichNotEqual(RelationshipGroupList groupListB) {
        RelationshipGroupList sg = new RelationshipGroupList();
        for (RelationshipGroup groupA : this) {
            boolean foundEqual = false;
            for (RelationshipGroup groupB : groupListB) {
                if (groupA.equals(groupB)) {
                    foundEqual = true;
                    break;
                }
            }
            if (!foundEqual) {
                sg.add(groupA);
            }
        }
        return sg;
    }

}
