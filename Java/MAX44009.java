// Distributed with a free-will license.
// Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
// MAX44009
// This code is designed to work with the MAX44009_I2CS I2C Mini Module available from ControlEverything.com.
// https://www.controleverything.com/products

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.lang.Math;

public class MAX44009
{
	public static void main(String args[]) throws Exception
	{
		// Create I2C bus
		I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
		// Get I2C device, MAX44009 I2C address is 0x4A(74)
		I2CDevice device = bus.getDevice(0x4A);
		
		// Select configuration register, 0x02(02)
		// Continuous mode, Integration time = 800 ms
		device.write(0x02, (byte)0x40);
		Thread.sleep(800);
		
		// Read 2 bytes of data from address 0x03(03)
		// luminance MSB, luminance LSB
		byte[] data = new byte[2];
		device.read(0x03 , data, 0, 2);
		
		// Convert the data to lux
		int exponent = (data[0] & 0xF0) >> 4;
		int mantissa = ((data[0] & 0x0F) << 4) | (data[1] & 0x0F);
		double luminance = Math.pow(2, exponent) * mantissa * 0.045;
		
		// Output data to screen
		System.out.printf("Ambient Light luminance : %.2f lux %n", luminance);
	}	
}