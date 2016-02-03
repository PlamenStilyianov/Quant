//
//
//                      PathDependent.cpp
//
//

#include <PathDependent.h>

PathDependent::PathDependent(const MJArray& LookAtTimes_)
                        :   LookAtTimes(LookAtTimes_)
{}

const MJArray& PathDependent::GetLookAtTimes() const
{
    return LookAtTimes;
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

