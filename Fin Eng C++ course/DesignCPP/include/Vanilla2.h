//
//
//                   Vanilla2.h
//
//
#ifndef VANILLA_2_H
#define VANILLA_2_H

#include <PayOff3.h>

class VanillaOption
{
public:

    VanillaOption(const PayOff& ThePayOff_, double Expiry_);
    VanillaOption(const VanillaOption& original);

    VanillaOption& operator=(const VanillaOption& original);   
    ~VanillaOption();

    double GetExpiry() const;
    double OptionPayOff(double Spot) const;


private:

    double Expiry;
    PayOff* ThePayOffPtr;
};

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

