//
//
//
//                SimpleMC2.h
//
//

#ifndef SIMPLEMC2_H
#define SIMPLEMC2_H

#include <PayOff2.h>

double SimpleMonteCarlo2(const PayOff& thePayOff, 
                         double Expiry, 
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

