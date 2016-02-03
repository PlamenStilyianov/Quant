//
//
//
//                  Parameters.cpp
//
//

#include <Parameters.h>

Parameters::Parameters(const ParametersInner& innerObject)
{
   InnerObjectPtr = innerObject.clone();
}

Parameters::Parameters(const Parameters& original)
{
   InnerObjectPtr = original.InnerObjectPtr->clone();
}

Parameters& Parameters::operator=(const Parameters& original)
{
   if (this != &original)
   {   
      delete InnerObjectPtr;
      InnerObjectPtr = original.InnerObjectPtr->clone();
   }
   return *this;
}

Parameters::~Parameters()
{
   delete InnerObjectPtr;
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



double Parameters::Mean(double time1, double time2) const
{
    double total = Integral(time1,time2);
    return total/(time2-time1);
}

double Parameters::RootMeanSquare(double time1, double time2) const
{
    double total = IntegralSquare(time1,time2);
    return total/(time2-time1);
}

ParametersConstant::ParametersConstant(double constant)
{
    Constant = constant;
    ConstantSquare = Constant*Constant;
}

ParametersInner* ParametersConstant::clone() const
{
    return new ParametersConstant(*this);
}


double ParametersConstant::Integral(double time1, double time2) const
{
    return (time2-time1)*Constant;
}

double ParametersConstant::IntegralSquare(double time1, double time2) const
{
    return (time2-time1)*ConstantSquare;
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

