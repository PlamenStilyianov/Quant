//
//
//                       ExoticEngine.cpp
//
//

#include <ExoticEngine.h>
#include <cmath>

ExoticEngine::ExoticEngine(const Wrapper<PathDependent>& TheProduct_,
                 const Parameters& r_)
                 :
                 TheProduct(TheProduct_),
                 r(r_),
                 Discounts(TheProduct_->PossibleCashFlowTimes())
{
    for (unsigned long i=0; i < Discounts.size(); i++)
        Discounts[i] = exp(-r.Integral(0.0, Discounts[i]));

    TheseCashFlows.resize(TheProduct->MaxNumberOfCashFlows());
}

void ExoticEngine::DoSimulation(StatisticsMC& TheGatherer, unsigned long NumberOfPaths)
{
    MJArray SpotValues(TheProduct->GetLookAtTimes().size());

    TheseCashFlows.resize(TheProduct->MaxNumberOfCashFlows());
    double thisValue;
    
    for (unsigned long i =0; i < NumberOfPaths; ++i)
    {
        GetOnePath(SpotValues);
        thisValue = DoOnePath(SpotValues);
        TheGatherer.DumpOneResult(thisValue);
    }

    return;
}

double ExoticEngine::DoOnePath(const MJArray& SpotValues) const
{
    unsigned long NumberFlows = TheProduct->CashFlows(SpotValues,
                                                      TheseCashFlows);
    double Value=0.0;
    
    for (unsigned i =0; i < NumberFlows; ++i)
        Value += TheseCashFlows[i].Amount * Discounts[TheseCashFlows[i].TimeIndex];

    return Value;
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

