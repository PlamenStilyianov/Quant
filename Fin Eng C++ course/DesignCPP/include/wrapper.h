//
//
//                              wrapper.h
//
//

#ifndef WRAPPER_H
#define WRAPPER_H

template< class T>
class Wrapper
{
public:

    Wrapper()
    { DataPtr =0;}

    Wrapper(const T& inner)
    {
        DataPtr = inner.clone();
    }

    ~Wrapper()
    {
        if (DataPtr !=0)
            delete DataPtr;
    }
  
    Wrapper(const Wrapper<T>& original)
    {
        if (original.DataPtr !=0)
            DataPtr = original.DataPtr->clone();
        else
            DataPtr=0;
    }

    Wrapper& operator=(const Wrapper<T>& original)
    {
        if (this != &original)
        {
            if (DataPtr!=0)
                delete DataPtr;

            DataPtr = (original.DataPtr !=0) ? original.DataPtr->clone() : 0;
        }

        return *this;
    }


    T& operator*()
    {
        return *DataPtr; 
    }

    const T& operator*() const
    {
        return *DataPtr; 
    }

    const T* const operator->() const
    {
        return DataPtr;
    }

    T* operator->()
    {
        return DataPtr;
    }


private:
    T* DataPtr;


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

