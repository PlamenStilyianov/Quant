//
//
//              Bisection.h
//
//


template<class T>
double Bisection(double Target, 
                 double Low, 
                 double High,
                 double Tolerance,
                 T TheFunction) 
{

    double x=0.5*(Low+High);
    double y=TheFunction(x);
    
    do
    {
       
        if (y < Target)
            Low = x;

        if (y > Target)
            High = x;

        x = 0.5*(Low+High);

        y = TheFunction(x);
    
    }
    while 
        ( (fabs(y-Target) > Tolerance) );

    return x;
}

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

