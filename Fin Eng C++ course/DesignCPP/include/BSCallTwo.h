//
//
//                  BSCallTwo.h
//
//
#ifndef BS_CALL_TWO_H
#define BS_CALL_TWO_H

class BSCallTwo
{

public:

    BSCallTwo(double r_, double d_,
                     double T, double Spot_,
                     double Strike_);

    double Price(double Vol) const;
    double Vega(double Vol) const;

private:

    double r;
    double d;
    double T;
    double Spot;
    double Strike;

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

