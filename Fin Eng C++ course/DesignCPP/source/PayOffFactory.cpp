//
//
//                  PayOffFactory.cpp: 
//
//
#if defined(_MSC_VER)
#pragma warning( disable : 4786)
#endif

#include <PayOffFactory.h>
#include <iostream>
using namespace std;

void PayOffFactory::RegisterPayOff(string PayOffId, CreatePayOffFunction CreatorFunction)
{
     TheCreatorFunctions.insert(pair<string,CreatePayOffFunction>(PayOffId,CreatorFunction));
}

PayOff* PayOffFactory::CreatePayOff(string PayOffId, double Strike)
{
    map<string, CreatePayOffFunction>::const_iterator 
                                i = TheCreatorFunctions.find(PayOffId);
	
    if  (i == TheCreatorFunctions.end())
    {
		std::cout << PayOffId << " is an unknown payoff" << std::endl;
		return NULL;
	}

	return (i->second)(Strike);
}

PayOffFactory& PayOffFactory::Instance()
{
    static PayOffFactory theFactory;
    return theFactory;
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

