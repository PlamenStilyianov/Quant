//
//
//                  Normals.cpp
//
//  
/*
code to implement the basic distribution functions necessary in mathematical finance
via rational approximations
  */
 
#include <cmath>
#include <Normals.h>

// the basic math functions should be in namespace std but aren't in VCPP6
#if !defined(_MSC_VER)
using namespace std;
#endif

const double OneOverRootTwoPi = 0.398942280401433; 

// probability density for a standard Gaussian distribution
double NormalDensity(double x)
{
    return OneOverRootTwoPi*exp(-x*x/2);
}

// the InverseCumulativeNormal function via the Beasley-Springer/Moro approximation
double InverseCumulativeNormal(double u)
{


    static double a[4]={  2.50662823884,
                        -18.61500062529,
                         41.39119773534,
                        -25.44106049637};

    static double b[4]={-8.47351093090,
                        23.08336743743,
                       -21.06224101826,
                         3.13082909833};

    static double c[9]={0.3374754822726147,
                        0.9761690190917186,
                        0.1607979714918209,
                        0.0276438810333863,
                        0.0038405729373609,
                        0.0003951896511919,
                        0.0000321767881768,
                        0.0000002888167364,
                        0.0000003960315187};

    
    double x=u-0.5;
    double r;

    if (fabs(x)<0.42) // Beasley-Springer
    {
        double y=x*x;
        
        r=x*(((a[3]*y+a[2])*y+a[1])*y+a[0]) / ((((b[3]*y+b[2])*y+b[1])*y+b[0])*y+1.0);
               
    }
    else // Moro
    {
    
        r=u;
    
        if (x>0.0) 
            r=1.0-u;
        
        r=log(-log(r));
        
        r=c[0]+r*(c[1]+r*(c[2]+r*(c[3]+r*(c[4]+r*(c[5]+r*(c[6] + r*(c[7]+r*c[8])))))));
        
        if (x<0.0) 
            r=-r;
    
    }

    return r;
}


// standard normal cumulative distribution function
double CumulativeNormal(double x)
{
    static double a[5] = { 0.319381530,
                          -0.356563782,
                           1.781477937,
                          -1.821255978,
                           1.330274429};

    double result;
    
    if (x<-7.0)
        result = NormalDensity(x)/sqrt(1.+x*x);
    
    else 
    {
        if (x>7.0)
            result = 1.0 - CumulativeNormal(-x);
        else
        {
            double tmp = 1.0/(1.0+0.2316419*fabs(x));

            result=1-NormalDensity(x) * (tmp*(a[0]+tmp*(a[1]+tmp*(a[2]+tmp*(a[3]+tmp*a[4])))));

            if (x<=0.0) 
                result=1.0-result;

        }
    }

    return result;
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


