package com.ipcalculator;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class IP {
	
	private InetAddress inetAddr;
	private Convertor convertor;

	public IP(String ip) {
		try {
			inetAddr = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		convertor = new Convertor();
	}
	
	public String getIP(){
		return inetAddr.getHostAddress();
	}
	
	public boolean setIP(String ip){
		boolean ret;
		try {
			inetAddr = InetAddress.getByName(ip);
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}
	
	private long ipToLong(){
		long bits = 0;  
		for (byte b: inetAddr.getAddress()){
		    bits = bits << 8 | (b & 0xFF);  
		}
		return bits;
	}
	
	public int getNbHost(int mask){
		return (int) Math.pow(2.0, (32-mask))-2;
	}
	
	public long maskToLong(int mask){
		return convertor.decMaskToLong(mask);
	}
	
	public String maskToSubnet(int mask){
		return convertor.decMaskToSubnet(mask);
	}
	
	public String getBroadcast(int mask){
		long networkPart = ipToLong();
		
		networkPart &= maskToLong(mask);
		long bits = networkPart | ~maskToLong(mask);
		
		return bitToString(bits);
	}
	
	public String getHost(int mask){
		long bits = ipToLong();
		bits &= ~maskToLong(mask);
		return bitToString(bits);
	}
	
	public String getNetwork(int mask){
		long bits = ipToLong();
		bits &= maskToLong(mask);
		return bitToString(bits);
	}
	
	public String bitToString(long bits){
		String str = String.format("%d.%d.%d.%d", (bits & 0x0000000000ff000000L) >> 24,
				(bits & 0x0000000000ff0000) >> 16,
				(bits & 0x0000000000ff00) >> 8,
				bits & 0xff);
		return str;
	}

}
