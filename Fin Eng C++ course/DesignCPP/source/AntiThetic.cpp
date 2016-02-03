//
//
//                  AntiThetic.cpp
//
//

#include <AntiThetic.h>

AntiThetic::AntiThetic(const Wrapper<RandomBase>& innerGenerator )
            : RandomBase(*innerGenerator), 
              InnerGenerator(innerGenerator)
{
    InnerGenerator->Reset();
    OddEven =true;
    NextVariates.resize(GetDimensionality());
}

RandomBase* AntiThetic::clone() const
{
    return new AntiThetic(*this);
}
    
void AntiThetic::GetUniforms(MJArray& variates)
{
    if (OddEven)
    {
        InnerGenerator->GetUniforms(variates);

        for (unsigned long i =0; i < GetDimensionality(); i++)
            NextVariates[i] = 1.0-variates[i];
        
        OddEven = false;
    }
    else
    {
        variates = NextVariates;

        OddEven = true;
    }
}

void AntiThetic::SetSeed(unsigned long Seed)
{
    InnerGenerator->SetSeed(Seed);
    OddEven = true;
}

void AntiThetic::Skip(unsigned long numberOfPaths)
{
    if (numberOfPaths ==0)
        return;

    if (OddEven)
    {
        OddEven = false;
        numberOfPaths--;
    }

    InnerGenerator->Skip(numberOfPaths  / 2);

    if (numberOfPaths % 2)
    {
        MJArray tmp(GetDimensionality());

        GetUniforms(tmp);

    }



}

void AntiThetic::ResetDimensionality(unsigned long NewDimensionality)
{
    RandomBase::ResetDimensionality(NewDimensionality);

    NextVariates.resize(NewDimensionality);

    InnerGenerator->ResetDimensionality(NewDimensionality);
}

void AntiThetic::Reset()
{
    InnerGenerator->Reset();
    OddEven =true;
}