//
//
//                           PayOffFactory.h
//
//
#ifndef PAYOFF_FACTORY_H
#define PAYOFF_FACTORY_H
#include <PayOff3.h>

#if defined(_MSC_VER)
#pragma warning( disable : 4786)
#endif

#include <map>
#include <string>

class PayOffFactory  
{
public:

	typedef PayOff* (*CreatePayOffFunction)(double );

    static PayOffFactory& Instance();
    void RegisterPayOff(std::string, CreatePayOffFunction);
    PayOff* CreatePayOff(std::string PayOffId,double Strike);
	~PayOffFactory(){};

private:
    std::map<std::string, CreatePayOffFunction> TheCreatorFunctions;
	PayOffFactory(){}
	PayOffFactory(const PayOffFactory&){}
    PayOffFactory& operator=(const PayOffFactory&){ return *this;}

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

