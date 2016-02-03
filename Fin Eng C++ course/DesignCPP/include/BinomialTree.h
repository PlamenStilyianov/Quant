//
//
//              BinomialTree.h
//
//


#pragma warning( disable : 4786 )


#include <TreeProducts.h>
#include <vector>
#include <Parameters.h>
#include <Arrays.h>


class SimpleBinomialTree
{

public:
	SimpleBinomialTree(double Spot_,
                       const Parameters& r_,
                       const Parameters& d_, 
                       double Volatility_,
                       unsigned long Steps,
                       double Time);
                  

	double GetThePrice(const TreeProduct& TheProduct);

protected:

    void BuildTree(); 

private:
  
    double Spot;
    Parameters r;
	Parameters d;
    double Volatility;
    unsigned long Steps;
    double Time;
    bool TreeBuilt;

    std::vector<std::vector<std::pair<double, double> > > TheTree;
    MJArray Discounts;
};


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

