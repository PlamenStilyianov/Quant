//
//
//                      PayOffForward.h
//
//

#ifndef PAY_OFF_FORWARD_H
#define PAY_OFF_FORWARD_H

#include <PayOff3.h>
class PayOffForward : public PayOff
{
public:

    PayOffForward(double Strike_);

    virtual double operator()(double Spot) const;
    virtual ~PayOffForward(){}
    virtual PayOff* clone() const;

private:

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

