//
//
//                   Vanilla3.h
//
//
#ifndef VANILLA_3_H
#define VANILLA_3_H

#include <PayOffBridge.h>

class VanillaOption
{
public:

    VanillaOption(const PayOffBridge& ThePayOff_, double Expiry);

    double OptionPayOff(double Spot) const;
    double GetExpiry() const;

private:

    double Expiry;
    PayOffBridge ThePayOff;
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

