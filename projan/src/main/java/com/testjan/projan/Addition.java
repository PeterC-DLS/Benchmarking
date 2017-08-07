package com.testjan.projan;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.january.dataset.AbstractDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

/**
 * Contains a method for the addition of two tensors of the same shape
 *
 */
public class Addition {
	
	/**
	 * Adding two datasets of the same shape a and b
	 * If the datasets aren't the same shape, throws an IllegalArgumentException
	 * @param a
	 * @param b
	 * @return
	 */
	public static Dataset add(Dataset a, Dataset b){
		if(!Arrays.equals(a.getShape(), b.getShape())){
			throw new IllegalArgumentException("Tensors to add must have the same shape");
		}else{
			//creating a result Dataset
			Dataset result = DatasetFactory.zeros(a.getShape());
			MyPositionIterator ita = new MyPositionIterator(a.getShape());//iterating through all three tensors in the same way, so only need one iterator
			final int[] apos = ita.getPos();
			while(ita.hasNext()){
				result.set(a.getDouble(apos) + b.getDouble(apos), apos);
			}
			return result;
		}
	}
	
	/**
	 * Adding two datasets of the same shape a and b, iterates through axes based on their strides
	 * If the datasets aren't the same shape, throws an IllegalArgumentException
	 * @param a
	 * @param b
	 * @return
	 */
	public static Dataset myAdd(Dataset a, Dataset b){
		if(!Arrays.equals(a.getShape(), b.getShape())){
			throw new IllegalArgumentException("Tensors to add must have the same shape");
		}else{
			//creating a result Dataset
			Dataset result = DatasetFactory.zeros(a.getShape());
			
			//want to order based on the strides of a
			int[] aoffset = new int[1];
			final int[] astride= AbstractDataset.createStrides(a, aoffset);
			int[] aaxes = new int[a.getRank()];
			//initialising aaxes
			for(int i=0;i<a.getRank();i++){
				aaxes[i]=i;
			}
		
			
			Integer[] aaxesobj = ArrayUtils.toObject(aaxes);
			Collections.sort(Arrays.asList(aaxesobj),new StrideSort(astride));//sorts aaxes
			aaxes = ArrayUtils.toPrimitive(aaxesobj);
			
			MyPositionIterator ita = new MyPositionIterator(a.getShape(),aaxes);//iterating through all three tensors in the same way
																				//so only need one iterator
			final int[] apos = ita.getPos();
			while(ita.hasNext()){
				//adding elements
				result.set(a.getDouble(apos) + b.getDouble(apos), apos);
			}
			
			return result;
		}
	}
	
	/**
	 * Adding two datasets of the same shape a and b, iterates through axes based on their strides
	 * If the datasets aren't the same shape, throws an IllegalArgumentException
	 * Unlike myAdd, compares the strides of the two dataset to see how to order the strides 
	 * rather than just ordering based on the strides of a
	 * @param a
	 * @param b
	 * @return
	 */
	public static Dataset myAdd2(Dataset a, Dataset b){
		if(!Arrays.equals(a.getShape(), b.getShape())){
			throw new IllegalArgumentException("Tensors to add must have the same shape");
		}else{
			//creating a result Dataset
			Dataset result = DatasetFactory.zeros(a.getShape());
			
			//'multiplying' the strides from the two datasets
			int[] aoffset = new int[1];
			final int[] astride= AbstractDataset.createStrides(a, aoffset);
			int[] boffset = new int[1];
			final int[] bstride= AbstractDataset.createStrides(b, boffset);
			
			final int[] strideMult = new int[astride.length];
			for(int i=0;i<strideMult.length;i++){
				strideMult[i] = astride[i]*bstride[i]; 
			}
			
			
			
			int[] aaxes = new int[a.getRank()];
			//initialising aaxes
			for(int i=0;i<a.getRank();i++){
				aaxes[i]=i;
			}
		
			
			Integer[] aaxesobj = ArrayUtils.toObject(aaxes);
			Collections.sort(Arrays.asList(aaxesobj),new StrideSort(strideMult));//sorts aaxes
			aaxes = ArrayUtils.toPrimitive(aaxesobj);
			
			MyPositionIterator ita = new MyPositionIterator(a.getShape(),aaxes);//iterating through all three tensors in the same way
																				//so only need one iterator
			final int[] apos = ita.getPos();
			while(ita.hasNext()){
				//adding elements
				result.set(a.getDouble(apos) + b.getDouble(apos), apos);
			}
			
			return result;
		}
	}
	

		
		
		
		
	
	
	
	
} 
