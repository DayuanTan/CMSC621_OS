package com.javahelps.serviceSum;

import java.util.concurrent.TimeUnit;

public class ServiceSum {
	public float getSum(float a, float b) throws Exception{
		TimeUnit.SECONDS.sleep(2);
		return a + b;
	}
	public float active(){
		return 1;
	}
}
