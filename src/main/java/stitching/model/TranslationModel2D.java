/*
 * #%L
 * Fiji distribution of ImageJ for the life sciences.
 * %%
 * Copyright (C) 2007 - 2021 Fiji developers.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package stitching.model;

import java.util.Collection;

/**
 * TODO
 *
 * @author Stephan Saalfeld
 */
public class TranslationModel2D extends InvertibleModel
{
	static final protected int MIN_SET_SIZE = 1;
	final protected float[] translation = new float[ 2 ];
	public float[] getTranslation(){ return translation; }
	
	@Override
	final public int getMinSetSize(){ return MIN_SET_SIZE; }

	//@Override
	@Override
	public float[] apply( float[] point )
	{
		assert point.length == 2 : "2d translations can be applied to 2d points only.";
		
		return new float[]{
			point[ 0 ] + translation[ 0 ],
			point[ 1 ] + translation[ 1 ] };
	}
	
	//@Override
	@Override
	public void applyInPlace( float[] point )
	{
		assert point.length == 2 : "2d translations can be applied to 2d points only.";
		
		point[ 0 ] += translation[ 0 ];
		point[ 1 ] += translation[ 1 ];
	}
	
	//@Override
	@Override
	public float[] applyInverse( float[] point )
	{
		assert point.length == 2 : "2d translations can be applied to 2d points only.";
		
		return new float[]{
				point[ 0 ] - translation[ 0 ],
				point[ 1 ] - translation[ 1 ] };
	}

	//@Override
	@Override
	public void applyInverseInPlace( float[] point )
	{
		assert point.length == 2 : "2d translations can be applied to 2d points only.";
		
		point[ 0 ] -= translation[ 0 ];
		point[ 1 ] -= translation[ 1 ];
	}

	
	@Override
	public String toString()
	{
		return ( "[1,2](" + translation[ 0 ] + "," + translation[ 1 ] + ") " + cost );
	}

	@Override
	final public void fit( Collection< PointMatch > matches ) throws NotEnoughDataPointsException
	{
		if ( matches.size() < MIN_SET_SIZE ) throw new NotEnoughDataPointsException( matches.size() + " data points are not enough to estimate a 2d translation model, at least " + MIN_SET_SIZE + " data points required." );
		
		// center of mass:
		float pcx = 0, pcy = 0;
		float qcx = 0, qcy = 0;
		
		double ws = 0.0;
		
		for ( PointMatch m : matches )
		{
			float[] p = m.getP1().getL(); 
			float[] q = m.getP2().getW(); 
			
			float w = m.getWeight();
			ws += w;
			
			pcx += w * p[ 0 ];
			pcy += w * p[ 1 ];
			qcx += w * q[ 0 ];
			qcy += w * q[ 1 ];
		}
		pcx /= ws;
		pcy /= ws;
		qcx /= ws;
		qcy /= ws;

		translation[ 0 ] = qcx - pcx;
		translation[ 1 ] = qcy - pcy;
	}
	
	/**
	 * change the model a bit
	 * 
	 * estimates the necessary amount of shaking for each single dimensional
	 * distance in the set of matches
	 * 
	 * @param matches point matches
	 * @param scale gives a multiplicative factor to each dimensional distance (scales the amount of shaking)
	 * @param center local pivot point for centered shakes (e.g. rotation)
	 */
	@Override
	final public void shake(
			Collection< PointMatch > matches,
			float scale,
			float[] center )
	{
		// TODO If you ever need it, please implement it...
	}

	@Override
	public TranslationModel2D clone()
	{
		TranslationModel2D tm = new TranslationModel2D();
		tm.translation[ 0 ] = translation[ 0 ];
		tm.translation[ 1 ] = translation[ 1 ];
		tm.cost = cost;
		return tm;
	}
}
