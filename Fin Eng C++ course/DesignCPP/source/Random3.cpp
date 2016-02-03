//
//
//                      Random3.cpp
//
//

#include <Random3.h>

#include <cstdlib>


// the basic math functions should be in namespace std but aren't in VCPP6
#if !defined(_MSC_VER)
using namespace std;
#endif


RandomRand::RandomRand(unsigned long Dimensionality, unsigned long InitialSeed_)
: RandomBase(Dimensionality), InitialSeed(InitialSeed_)
{
    SetSeed(InitialSeed);
}

RandomBase* RandomRand::clone() const
{
    return new RandomRand(*this);   
}

void RandomRand::GetUniforms(MJArray& variates)
{
    for (unsigned long i=0; i < GetDimensionality(); i++)
       variates[i] =(rand()+1.0)/(RAND_MAX+2.0);
      
}

void RandomRand::Skip(unsigned long numberOfPaths)
{
    for (unsigned long j=0; j < numberOfPaths; j++)
     for (unsigned long i=0; i < GetDimensionality(); i++)
       rand();
}

void RandomRand::SetSeed(unsigned long Seed)
{
    srand(static_cast<unsigned int>(Seed));
}

void RandomRand::Reset()
{
    SetSeed(InitialSeed);
}