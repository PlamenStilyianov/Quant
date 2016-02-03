//
//
//                        Random3.h
//
//

#ifndef RANDOM3_H
#define RANDOM3_H

#include <Arrays.h>
#include <Random2.h>

class RandomRand : public RandomBase
{
public:
   
    RandomRand(unsigned long Dimensionality, unsigned long InitialSeed_ = 1UL);

    virtual RandomBase* clone() const;    
    virtual void GetUniforms(MJArray& variates);
    virtual void Skip(unsigned long numberOfPaths);
    virtual void SetSeed(unsigned long Seed);
    virtual void Reset();


private:

    unsigned long InitialSeed;
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

