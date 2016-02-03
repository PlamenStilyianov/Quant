//
//
//
//                               SimpleMC6.h
//
//

#ifndef SIMPLEMC6_H
#define SIMPLEMC6_H

#include <Vanilla3.h>
#include <Parameters.h>

double SimpleMonteCarlo4(const VanillaOption& TheOption, 
						 double Spot,
						 const Parameters& Vol, 
						 const Parameters& r, 
						 unsigned long NumberOfPaths);

#endif

/*
 *
 * Copyright (c) 2002
 * Mark Joshi
 *
 * Permission to use, copy, modify, distribute and sell this
 * software for any purpose is hereby
 * granted without fee, provided that the above copyright notice
 * appear in all copies and that both that copyright notice and
 * this permission notice appear in supporting documentation.
 * Mark Joshi makes no representations about the
 * suitability of this software for any purpose. It is provided
 * "as is" without express or implied warranty.
*/

