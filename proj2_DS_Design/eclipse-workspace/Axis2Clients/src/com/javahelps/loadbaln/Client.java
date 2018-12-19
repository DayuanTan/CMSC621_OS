package com.javahelps.loadbaln;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.xml.namespace.QName;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.*;

import com.javahelps.loadbaln.LoadBalnStub;
import com.javahelps.loadbaln.UpdateBusy;

public class Client {
	public static void main(String[] args) throws Exception{
		//Create the stub object
		LoadBalnStub stub = new LoadBalnStub();
		
		
		//Create the request
		LoadBalnStub.WhereServices request = new LoadBalnStub.WhereServices();
		
		
		//Set the parameters
		System.out.println("Which kind of services do you want? Please choose from 'sum', 'subtract' and 'multiply'.");
		System.out.print("Please input:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String serviceName = reader.readLine();
		request.setServiceName(serviceName);
	
		//update service busy table
		UpdateBusy up = new UpdateBusy();
		
		
		for (float i = 1; i < 10; i++) {
		
			//Invoke the service. Get wsdl form LoadBaln.
			LoadBalnStub.WhereServicesResponse response = stub.whereServices(request);
			String[] resArray = new String[5];
			resArray = response.get_return();
			String wsdl = resArray[0], namespace = resArray[1], methodPara = resArray[2], ipv4 = resArray[3], port = resArray[4];	
			//claim this service
			up.UpdateBusyTrue(methodPara, ipv4, port);
			/*
			System.out.println("The return wsdl 	is:	"  + wsdl);	
			System.out.println("The return nameSpace 	is:	"  + namespace);	
			System.out.println("The return method 	is:	"  + methodPara);
			System.out.println("The return ipv4 	is:	"  + ipv4);
			System.out.println("The return port 	is:	"  + port);
			*/
		
			
			//call webService using RPC
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
		
			//Call webService URL
			EndpointReference targetEPR = new EndpointReference(wsdl);
			options.setTo(targetEPR);
		
		
			//Set the parameters
			float a = i;
			float b = i * a;
			
			//set parameters
			Object[] opAddEntryArgs	= new Object[] {a,b};
			//set return's type's class object
			Class[] classes = new Class[] {float.class};
			//set method we want to call, parameters are (targetNamespace, targetMethod)
			QName opAddEntry = new QName(namespace,methodPara);
			//call
			float r = (float)(serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs,classes)[0]);
			System.out.print(ipv4 + ":" + port + " replies: ");
			System.out.println("The " + serviceName + " of " + a +" and " + b + " is : " + r);	
			
			//release this service
			up.UpdateBusyFalse(methodPara, ipv4, port);
		}
		System.out.println("Done!");
		

		
		
	}
}
