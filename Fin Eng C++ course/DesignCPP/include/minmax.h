//
//
//
//                  MinMax.h
//
//
// these should be <algorithm> but sometimes aren't.

#ifndef MINMAX_H
#define MINMAX_H

template<class T> const T& max(const T& a, const T& b)
{
    return (a<b) ? b : a;
}


template<class T> const T& min(const T& a, const T& b)
{
    return (a>b) ? b : a;
}
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

