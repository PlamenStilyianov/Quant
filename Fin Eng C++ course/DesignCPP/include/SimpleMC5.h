//
//
//
//                              SimpleMC5.h
//
//

#ifndef SIMPLEMC5_H
#define SIMPLEMC5_H

#include <Vanilla3.h>

double SimpleMonteCarlo3(const VanillaOption& TheOption, 
						 double Spot,
						 double Vol, 
						 double r, 
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

