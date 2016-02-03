//
//
//                  payfactorymain.cpp
//
//
/*
Uses
    PayOff3.cpp
    PayOffBridge.cpp
    PayOffFactory.cpp
    PayOffRegistration.cpp

*/

#include <PayOff3.h>
#include <PayOffConstructible.h>
#include <PayOffBridge.h>
#include <PayOffFactory.h>
#include <string>
#include <iostream>
using namespace std;

int main()
{

    double Strike;
    std::string name;

    cout << "Enter strike\n";
    cin >> Strike;
    
    cout << "\npay-off name\n";
    cin >> name;

    PayOff* PayOffPtr = PayOffFactory::Instance().CreatePayOff(name,Strike); 

    if (PayOffPtr != NULL)
    {
        double Spot;

        cout << "\nspot\n";
        cin >> Spot;

        cout << "\n" << PayOffPtr->operator ()(Spot) << "\n";    
        delete PayOffPtr;
    }
    
    double tmp;
    cin >> tmp;
    return 0;
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

