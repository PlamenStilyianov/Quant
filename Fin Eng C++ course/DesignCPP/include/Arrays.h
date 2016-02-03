//
//
//
//		Arrays.h	
//
//
//

#ifndef MJARRAYS_H
#define MJARRAYS_H

#ifdef USE_VAL_ARRAY

#include <valarray>

typedef  std::valarray<double> MJArray;

#else   // ifdef USE_VAL_ARRAY


class MJArray
{

public:

	explicit MJArray(unsigned long size=0);
	MJArray(const MJArray& original);
	
	~MJArray();

	MJArray& operator=(const MJArray& original);
	MJArray& operator=(const double& val);

	MJArray& operator+=(const MJArray& operand);
	MJArray& operator-=(const MJArray& operand);
	MJArray& operator/=(const MJArray& operand);
	MJArray& operator*=(const MJArray& operand);

	MJArray& operator+=(const double& operand);
	MJArray& operator-=(const double& operand);
	MJArray& operator/=(const double& operand);
	MJArray& operator*=(const double& operand);

	MJArray apply(double f(double)) const;

	inline double operator[](unsigned long i) const;
	inline double& operator[](unsigned long i);

	inline unsigned long size() const;

	void resize(unsigned long newSize);

	double sum() const;
	double min() const;
	double max() const;

private:


	double* ValuesPtr;
	double* EndPtr;


	unsigned long Size;
	unsigned long Capacity;
	

};

inline double MJArray::operator[](unsigned long i) const
{
#ifdef RANGE_CHECKING
	if (i >= Size)
	{
		throw("Index out of bounds");
	}
#endif 

	return ValuesPtr[i];
}


inline double& MJArray::operator[](unsigned long i)
{
#ifdef  RANGE_CHECKING
	if (i >= Size)
	{
		throw("Index out of bounds");
	}
#endif 

	return ValuesPtr[i];
}

inline unsigned long MJArray::size() const
{
	return Size;
}



#endif // ifdef USE_VAL_ARRAY

#endif // ifndef MJARRAYS_H


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

