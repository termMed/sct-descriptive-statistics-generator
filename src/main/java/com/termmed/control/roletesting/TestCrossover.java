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
package com.termmed.control.roletesting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.termmed.inheritance.model.Relationship;
import com.termmed.inheritance.model.RelationshipGroup;
import com.termmed.utils.TClosure;


// TODO: Auto-generated Javadoc
/**
 * The Class TestCrossover.
 */
public class TestCrossover {

	/** The Tclos. */
	TClosure Tclos;

	/**
	 * Instantiates a new test crossover.
	 *
	 * @param tclos the tclos
	 */
	public TestCrossover(TClosure tclos) {
		super();
		Tclos = tclos;
	}

	/**
	 * The Enum TEST_RESULTS.
	 */
	public enum TEST_RESULTS {/** The CONCEP t1_ ancestoro f_ concep t2. */
		CONCEPT1_ANCESTOROF_CONCEPT2,/** The CONCEP t2_ ancestoro f_ concep t1. */
		CONCEPT2_ANCESTOROF_CONCEPT1,/** The CONCEPT s_ dif f_ hierarchy. */
		CONCEPTS_DIFF_HIERARCHY,
				
				/** The CONCEP t1_ subsu m_ concep t2. */
				CONCEPT1_SUBSUM_CONCEPT2,
		/** The CONCEP t1_ equa l_ concep t2. */
		CONCEPT1_EQUAL_CONCEPT2,
		/** The CONCEP t2_ subsu m_ concep t1. */
		CONCEPT2_SUBSUM_CONCEPT1,
		/** The THER e_ i s_ n o_ subsum. */
		THERE_IS_NO_SUBSUM,
				
				/** The ROL e1_ subsu m_ rol e2. */
				ROLE1_SUBSUM_ROLE2, 
		 /** The ROL e2_ subsu m_ rol e1. */
		 ROLE2_SUBSUM_ROLE1,
		/** The ROL e1_ equa l_ rol e2. */
		ROLE1_EQUAL_ROLE2,
		/** The ROLE s_ crossover. */
		ROLES_CROSSOVER,
				
				/** The ROLEGROU p1_ subsu m_ rolegrou p2. */
				ROLEGROUP1_SUBSUM_ROLEGROUP2,
		/** The ROLEGROU p2_ subsu m_ rolegrou p1. */
		ROLEGROUP2_SUBSUM_ROLEGROUP1,
		/** The ROLEGROU p1_ equa l_ rolegrou p2. */
		ROLEGROUP1_EQUAL_ROLEGROUP2,
		/** The ROLEGROUP s_ crossover. */
		ROLEGROUPS_CROSSOVER
	};
	
	/**
	 * Subsumption concept test.
	 *
	 * @param concept1 the concept1
	 * @param concept2 the concept2
	 * @return the tES t_ results
	 */
	public TEST_RESULTS subsumptionConceptTest(long concept1, long concept2){
		if (concept1==concept2){
			return TEST_RESULTS.CONCEPT1_EQUAL_CONCEPT2;
		}
		if (Tclos.isAncestorOf(concept1, concept2)){
			return TEST_RESULTS.CONCEPT1_ANCESTOROF_CONCEPT2;
		}
		if (Tclos.isAncestorOf(concept2, concept1)){
			return TEST_RESULTS.CONCEPT2_ANCESTOROF_CONCEPT1;
		}
		return TEST_RESULTS.CONCEPTS_DIFF_HIERARCHY;

	}

	/**
	 * Subsumption role test.
	 *
	 * @param role1 the role1
	 * @param role2 the role2
	 * @return the tES t_ results
	 */
	public TEST_RESULTS subsumptionRoleTest(Relationship role1, Relationship role2){
		TEST_RESULTS relTargetSubSum=subsumptionConceptTest(role1.destinationId,role2.destinationId);
		TEST_RESULTS relTypeSubsum;
		switch(relTargetSubSum){
		case CONCEPT1_ANCESTOROF_CONCEPT2:
			relTypeSubsum=subsumptionConceptTest( role1.typeId,role2.typeId);
			switch(relTypeSubsum){
			case CONCEPT1_ANCESTOROF_CONCEPT2:
			case CONCEPT1_EQUAL_CONCEPT2:
				return TEST_RESULTS.ROLE1_SUBSUM_ROLE2;
			case CONCEPT2_ANCESTOROF_CONCEPT1:
				return TEST_RESULTS.ROLES_CROSSOVER;
			case CONCEPTS_DIFF_HIERARCHY:
				return TEST_RESULTS.THERE_IS_NO_SUBSUM;
			}
			break;
		case CONCEPT1_EQUAL_CONCEPT2:
			relTypeSubsum=subsumptionConceptTest( role1.typeId,role2.typeId);
			switch(relTypeSubsum){
			case CONCEPT1_ANCESTOROF_CONCEPT2:
				return TEST_RESULTS.ROLE1_SUBSUM_ROLE2;
			case CONCEPT1_EQUAL_CONCEPT2:
				return TEST_RESULTS.ROLE1_EQUAL_ROLE2;
			case CONCEPT2_ANCESTOROF_CONCEPT1:
				return TEST_RESULTS.ROLE2_SUBSUM_ROLE1;
			case CONCEPTS_DIFF_HIERARCHY:
				return TEST_RESULTS.THERE_IS_NO_SUBSUM;
			}
			break;
		case CONCEPT2_ANCESTOROF_CONCEPT1:
			relTypeSubsum=subsumptionConceptTest(role1.typeId,role2.typeId);
			switch(relTypeSubsum){
			case CONCEPT1_ANCESTOROF_CONCEPT2:
				return TEST_RESULTS.ROLES_CROSSOVER;
			case CONCEPT1_EQUAL_CONCEPT2:
			case CONCEPT2_ANCESTOROF_CONCEPT1:
				return TEST_RESULTS.ROLE2_SUBSUM_ROLE1;
			case CONCEPTS_DIFF_HIERARCHY:
				return TEST_RESULTS.THERE_IS_NO_SUBSUM;
			}
			break;
		case CONCEPTS_DIFF_HIERARCHY:
			return TEST_RESULTS.THERE_IS_NO_SUBSUM;
		}
		return null;

	}
	
	/**
	 * Checks if is crossover.
	 *
	 * @param rolegroup1 the rolegroup1
	 * @param rolegroup2 the rolegroup2
	 * @return true, if is crossover
	 */
	public boolean isCrossover(RelationshipGroup rolegroup1, RelationshipGroup rolegroup2) {
		TEST_RESULTS result = subsumptionRoleGroupTest( rolegroup1,  rolegroup2) ;
		return result.equals(TEST_RESULTS.ROLEGROUPS_CROSSOVER) || result.equals(TEST_RESULTS.ROLES_CROSSOVER );
	}
	
	/**
	 * Subsumption role group test.
	 *
	 * @param rolegroup1 the rolegroup1
	 * @param rolegroup2 the rolegroup2
	 * @return the tES t_ results
	 */
	public TEST_RESULTS subsumptionRoleGroupTest(RelationshipGroup rolegroup1, RelationshipGroup rolegroup2) {
		TEST_RESULTS roleTestResult ;
		boolean rolesCrossover=false;
		boolean equalRoles=false;
		boolean oneSubsTwoRole=false;
		boolean twoSubsOneRole=false;
		boolean thereIsNoSubsRole=false;
		boolean crossover=false;
		boolean equal=false;
		boolean oneSubsTwo=false;
		boolean twoSubsOne=false;
		List<Relationship> tupleToTestSwitch=new ArrayList<Relationship>();

		Set<Relationship> tupleTested=new HashSet<Relationship>();
		for (Relationship role1: rolegroup1){
			for (Relationship role2:rolegroup2){
				roleTestResult = subsumptionRoleTest(role1, role2);
				switch(roleTestResult){
				case ROLES_CROSSOVER:
					crossover=true;
					break;
				case ROLE1_EQUAL_ROLE2:
					equal=true;
					tupleTested.add( role2);
					break;
				case ROLE1_SUBSUM_ROLE2:
					oneSubsTwo=true;
					break;
				case ROLE2_SUBSUM_ROLE1:
					twoSubsOne=true;
					tupleTested.add( role2);
					break;
				case THERE_IS_NO_SUBSUM:
					break;
				}
//				if (oneSubsTwo){
//					break;
//				}
			}
			if (oneSubsTwo){
				oneSubsTwoRole=true;
				oneSubsTwo=false;
				crossover=false;
				equal=false;
				twoSubsOne=false;
			}else if (twoSubsOne){
				twoSubsOneRole=true;
				twoSubsOne=false;
				crossover=false;
				equal=false;
			}else if (equal){
				equalRoles=true;
				equal=false;
				crossover=false;
			}else if (crossover){
				rolesCrossover=true;
				crossover=false;
			}else{
				thereIsNoSubsRole=true;		
			}

		}
		if (oneSubsTwoRole && twoSubsOneRole){
			return TEST_RESULTS.ROLEGROUPS_CROSSOVER;
		}
		if (rolesCrossover){
			return TEST_RESULTS.ROLES_CROSSOVER;
		}
		if (oneSubsTwoRole && rolegroup1.size()>rolegroup2.size()){
			return TEST_RESULTS.ROLEGROUPS_CROSSOVER;
		}
		if (twoSubsOneRole && rolegroup2.size()>rolegroup1.size()){
			return TEST_RESULTS.ROLEGROUPS_CROSSOVER;
		}
		if (thereIsNoSubsRole){
			return TEST_RESULTS.THERE_IS_NO_SUBSUM;
		}
		if (oneSubsTwoRole){
			return TEST_RESULTS.CONCEPT1_SUBSUM_CONCEPT2;
		}
		if (twoSubsOneRole){
			int tuplesTotest=rolegroup2.size()-tupleTested.size();
			if (tuplesTotest>0){
				for (Relationship role2:rolegroup2){
					if (!tupleTested.contains(role2)){
						tupleToTestSwitch.add(role2);
					}
				}

				RelationshipGroup relgroupToTest=new RelationshipGroup(tupleToTestSwitch,false);
				boolean shortResult=roleGroup1SubSumToRoleGroup2(relgroupToTest,rolegroup1);
				if (!shortResult){
					return TEST_RESULTS.THERE_IS_NO_SUBSUM;
				}
			}
			return TEST_RESULTS.CONCEPT2_SUBSUM_CONCEPT1;
		}
		if (equalRoles){
			return TEST_RESULTS.ROLEGROUP1_EQUAL_ROLEGROUP2;
		}
		return null;

	}


	/**
	 * Role group1 sub sum to role group2.
	 *
	 * @param rolegroup1 the rolegroup1
	 * @param rolegroup2 the rolegroup2
	 * @return true, if successful
	 */
	private boolean roleGroup1SubSumToRoleGroup2(
			RelationshipGroup rolegroup1, RelationshipGroup rolegroup2) {
		TEST_RESULTS roleTestResult ;
		boolean equal=false;
		boolean oneSubsTwo=false;
		boolean twoSubsOne=false;

		for (Relationship role1: rolegroup1){
			for (Relationship role2:rolegroup2){
				roleTestResult = subsumptionRoleTest(role1, role2);
				switch(roleTestResult){
				case ROLES_CROSSOVER:
					break;
				case ROLE1_EQUAL_ROLE2:
					equal=true;
					break;
				case ROLE1_SUBSUM_ROLE2:
					oneSubsTwo=true;
					break;
				case ROLE2_SUBSUM_ROLE1:
					twoSubsOne=true;
					break;
				case THERE_IS_NO_SUBSUM:
					break;
				}
				if (oneSubsTwo){
					break;
				}
			}
			if (oneSubsTwo){
				equal=false;
				oneSubsTwo=false;
				twoSubsOne=false;
			}else if (twoSubsOne){
				return false;
			}else if (equal){
				equal=false;
			}else {
				return false;		
			}

		}
		return true;

	}

	/**
	 * Checks if is redundant.
	 *
	 * @param rolegroup1 the rolegroup1
	 * @param rolegroup2 the rolegroup2
	 * @return true, if is redundant
	 */
	public boolean isRedundant(RelationshipGroup rolegroup1,RelationshipGroup rolegroup2) {

		TEST_RESULTS result = subsumptionRoleGroupTest( rolegroup1,  rolegroup2) ;
		return result.equals(TEST_RESULTS.CONCEPT1_SUBSUM_CONCEPT2) || result.equals(TEST_RESULTS.CONCEPT2_SUBSUM_CONCEPT1) || result.equals(TEST_RESULTS.ROLEGROUP1_EQUAL_ROLEGROUP2 );
	}
	

}
