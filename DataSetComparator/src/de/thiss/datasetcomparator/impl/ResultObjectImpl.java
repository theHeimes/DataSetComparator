package de.thiss.datasetcomparator.impl;

import de.thiss.datasetcomparator.ResultObject;

public class ResultObjectImpl implements ResultObject {

	private ResultTypes resultType;
	private String key;
	private double referenceValue;
	private double comparedValue;
	
	public ResultObjectImpl(ResultTypes resultType, String key , double comparedValue, double referenceValue) {
		this.resultType = resultType;
		this.key = key;
		this.comparedValue = comparedValue;
		this.referenceValue = referenceValue;
	}
	
	@Override
	public String resultToString() {
		return resultType.toString() + "|" + key + "|" + comparedValue + "|" + referenceValue;
	}

	@Override
	public ResultTypes getResultType() {
		return resultType;
	}

}
