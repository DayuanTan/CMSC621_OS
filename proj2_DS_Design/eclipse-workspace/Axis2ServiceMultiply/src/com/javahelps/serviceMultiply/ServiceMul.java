package com.javahelps.serviceMultiply;

import java.util.concurrent.TimeUnit;

public class ServiceMul {
	public float getProduct(float a, float b) throws Exception{
		TimeUnit.SECONDS.sleep(2);
		return (float)(a*b);
	}
	public float active(){
		return 1;
	}
}
