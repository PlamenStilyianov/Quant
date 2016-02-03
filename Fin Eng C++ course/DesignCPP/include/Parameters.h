//
//
//
//                      Parameters.h
//
//


#ifndef PARAMETERS_H
#define PARAMETERS_H

class ParametersInner
{

public:
    ParametersInner(){}

    virtual ParametersInner* clone() const=0;
    virtual double Integral(double time1, double time2) const=0;
    virtual double IntegralSquare(double time1, double time2) const=0;

private:

};


class Parameters
{

public:

    Parameters(const ParametersInner& innerObject);
    Parameters(const Parameters& original);
    Parameters& operator=(const Parameters& original);
    virtual ~Parameters();

    inline double Integral(double time1, double time2) const;
    inline double IntegralSquare(double time1, double time2) const;
    
    double Mean(double time1, double time2) const;
    double RootMeanSquare(double time1, double time2) const;

private:

    ParametersInner* InnerObjectPtr;

};

inline double Parameters::Integral(double time1, double time2) const
{
    return InnerObjectPtr->Integral(time1,time2);
}


inline double Parameters::IntegralSquare(double time1, double time2) const
{
    return InnerObjectPtr->IntegralSquare(time1,time2);
}


class ParametersConstant : public ParametersInner
{
public:

    ParametersConstant(double constant);
    
    virtual ParametersInner* clone() const;
    virtual double Integral(double time1, double time2) const;
    virtual double IntegralSquare(double time1, double time2) const;


private:

    double Constant;
    double ConstantSquare;

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

