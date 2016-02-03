/*
 Copyright (C) 2007 Richard Gomes
 Copyright (C) 2008 Q. Boiler

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the QuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.
 
 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */
package org.jquantlib.performance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Q.Boiler
 */
public class PerformanceDriver {

    private final static Logger logger = LoggerFactory.getLogger(PerformanceDriver.class);

    //  Could just scan for all classes in macroscopic and 
	//  microscopic and run the tests.
	List<PerformanceTest> microscopicTests = new ArrayList<PerformanceTest>();
	List<PerformanceTest> macroscopicTests = new ArrayList<PerformanceTest>();
	List<PerformanceResults> microscopicResults = new ArrayList<PerformanceResults>();
	List<PerformanceResults> macroscopicResults = new ArrayList<PerformanceResults>();
	

	{
		populateMicroscopicTests();
		populateMacroscopicTests();
	}
	
	//TODO: code review
	public void populateMicroscopicTests(){
//		microscopicTests.add(new Array());
//		microscopicTests.add(new PrimeNumbers());
	}
	
	
	public void populateMacroscopicTests(){
	}

	public static void main(String args[]){
		PerformanceDriver instance = new PerformanceDriver();

		if(args.length==0){
			//  Run all tests
			logger.info("------Microscopic----------");
			logger.info("---------------------------");
			execute(instance.microscopicTests,instance.microscopicResults);
			printResults(instance.microscopicResults);
			logger.info("------Macroscopic----------");
			logger.info("---------------------------");
			execute(instance.macroscopicTests,instance.macroscopicResults);
			printResults(instance.macroscopicResults);

		}else if(args[0].equalsIgnoreCase("microscopic")){
			//  run microscopic tests.
			logger.info("------Microscopic----------");
			logger.info("---------------------------");
			execute(instance.microscopicTests,instance.microscopicResults);
			printResults(instance.microscopicResults);
		}else if(args[0].equalsIgnoreCase("macroscopic")){
			//  run macroscopic tests.
			logger.info("------Macroscopic----------");
			logger.info("---------------------------");
			execute(instance.macroscopicTests,instance.macroscopicResults);
			printResults(instance.macroscopicResults);
		}else{
			logger.info("Unrecognized option: "+args[0]);
		}
	}

	public static void execute(List<PerformanceTest> test, List<PerformanceResults> results){
		for (Iterator<PerformanceTest> it = test.iterator(); it.hasNext();) {
			PerformanceTest performanceTest = it.next();
			results.add(performanceTest.execute());
		}
	}

	public static void printResults(List<PerformanceResults> results){
		for (Iterator<PerformanceResults> it = results.iterator(); it.hasNext();) {
			PerformanceResults performanceResults = it.next();
			logger.info(performanceResults.toString());
			if(performanceResults.compositeResults!=null){
				logger.info("\t----------------Composite Tests----------------------------------");
				printResults(performanceResults.compositeResults);
				logger.info("\t----------------END Composite Tests----------------------------------");
			}
		}
	}
}
