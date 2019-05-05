package com.count;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Calculator {
	private StringBuffer[] operator = new StringBuffer[4];
	private boolean createBrackets = true;//生成括号
	private boolean createMD = true;//生成乘除
	private boolean createDecimal = true;//是否包含小数
	private int maxNumber = 10;//最大值
	private ArrayList<StringBuffer> arrOperator;
	private ArrayList<StringBuffer> arrNumber;
	private ArrayList<StringBuffer> arrDemo;
	
	//"乘除","小数","括号","最大值"	
	public ArrayList<StringBuffer> create(Boolean MD, Boolean pointNum, Boolean brackets,String maxNum) {
			if(pointNum == false) 
				createDecimal = false;
			if(brackets == false) 
				createBrackets = false;
			if(MD == false) 
				createMD = false;
			maxNumber = Integer.parseInt(maxNum);
			operator[0] = new StringBuffer("+");
			operator[1] = new StringBuffer("-");
			operator[2] = new StringBuffer("*");
			operator[3] = new StringBuffer("/");
			arrOperator= new ArrayList<StringBuffer>();
			arrNumber = new ArrayList<StringBuffer>();
			arrDemo = new ArrayList<StringBuffer>();
		createOperator();
		if(createDecimal) 
			addDecimal();
		else
			addInteger();
		if(createBrackets)
			addBrackets();
		Merge();
		return arrDemo;
	}
	
	public void createOperator() {
		Random ra = new Random();
		int pointer = 0;
		int l = 4;
		if(!createMD) 
			l = 2;	
		int length = 2+(int)ra.nextInt(4);
		for(int i=0;i<length;++i) {
			pointer =ra.nextInt(l);
			if (i!=0&&operator[pointer]==arrOperator.get(i-1))
					i--;
			else
				arrOperator.add(operator[pointer]);
		}
	}
	
	public void addInteger() {
		int i=0;
		 Random ra = new Random();
		 int d = ra.nextInt(maxNumber);
		 arrNumber.add(new StringBuffer(Integer.toString(d)));
		for(StringBuffer sb:arrOperator) {
			int m = createInteger(i,sb);
			arrNumber.add(new StringBuffer(Integer.toString(m)));
			++i;
		}
	}
	
	public void addDecimal() {
		Random ra = new Random();
	    int d = ra.nextInt(maxNumber-1)+1;
	    arrNumber.add(new StringBuffer(Integer.toString(d)));
	    double flag = 0;
		for(StringBuffer sb:arrOperator) {
			flag = ra.nextDouble();
		    if(flag<0.7) {
				arrNumber.add(new StringBuffer(Integer.toString(ra.nextInt(maxNumber-1)+1)));
			 }else {
				 StringBuffer st = new StringBuffer();
				 st.append(ra.nextInt(maxNumber));
				 st.append('.');
				 st.append(ra.nextInt(maxNumber));
				 arrNumber.add(st);
			 }
		}
	}

	public void  Merge() {
		Iterator<StringBuffer> it1 = arrOperator.iterator();
		Iterator<StringBuffer> it2 = arrNumber.iterator();
	    for(;it1.hasNext();) {
	          arrDemo.add(it2.next());
	          arrDemo.add(it1.next());
	    }
	    arrDemo.add(it2.next());
	}
	
	private int createInteger(int i,StringBuffer sb) {
		Random ra = new Random();
	    if(sb==operator[3]) {
	    	int num = Integer.parseInt(arrNumber.get(i).toString());
	    	if(num == 0)
	    		return ra.nextInt(maxNumber)+1;
			for(int j = num-1;j>0;--j) 
				if(num%j == 0)
					return j;	
	    }
	    return ra.nextInt(maxNumber)+1;
	}

	public void addBrackets() {
		int tempI;
		double tempD;
		int i = 0;
		boolean jump = false;
		for(StringBuffer sb:arrNumber) {
			tempD = Double.parseDouble(sb.toString());
			tempI = (int)tempD;
			if((tempD-tempI)<0.00000001&&(tempI>1)) {
				String s = String.valueOf(tempD);
				for(int t=0;t<s.length();++t)
					if(s.charAt(t)!='.') {	
						departInteger(i);
						jump = true;
						break;
					}
				if(jump)
					break;
			}
			++i;
		}
	}
	
	public void departInteger(int i) {
		Random ra = new Random();
		int l = 4;
		if(!createMD)
			l=2;
		int pointer = ra.nextInt(l);
		int number1 = 0;
		int number2 = 0;
		StringBuffer s = arrNumber.get(i);
		int element = Integer.parseInt((s.toString()));
		if(pointer == 0) {
			number1 = ra.nextInt(element);
			number2 = element - number1;
			StringBuffer newS = new StringBuffer("("+Integer.toString(number1)+"+"+Integer.toString(number2)+")");
			arrNumber.remove(i);
			arrNumber.add(i,newS);
		}else if(pointer == 2) {
			for(number1=element;number1>0;--number1) 
				if(element%number1 == 0) {
					number2 = element/number1;
					break;
				}
			StringBuffer newS = new StringBuffer("("+Integer.toString(number1)+"*"+Integer.toString(number2)+")");
			arrNumber.remove(i);
			arrNumber.add(i,newS);
		}else if(pointer == 3) {
			for(int j=maxNumber-1;j>=1;++j) 
				if(j%element==0) {
					number1 = j;
					break;
				}
			number2 = number1/element;
			StringBuffer newS = new StringBuffer("("+Integer.toString(number1)+"/"+Integer.toString(number2)+")");
			arrNumber.remove(i);
			arrNumber.add(i,newS);
		}
	}
}
