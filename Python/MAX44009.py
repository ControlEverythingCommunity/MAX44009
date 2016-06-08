# Distributed with a free-will license.
# Use it any way you want, profit or free, provided it fits in the licenses of its associated works.
# MAX44009
# This code is designed to work with the MAX44009_I2CS I2C Mini Module available from ControlEverything.com.
# https://www.controleverything.com/products

import smbus
import time

# Get I2C bus
bus = smbus.SMBus(1)

# MAX44009 address, 0x4A(74)
# Select configuration register, 0x02(02)
#		0x40(64)	Continuous mode, Integration time = 800 ms
bus.write_byte_data(0x4A, 0x02, 0x40)

time.sleep(0.5)

# MAX44009 address, 0x4A(74)
# Read data back from 0x03(03), 2 bytes
# luminance MSB, luminance LSB
data = bus.read_i2c_block_data(0x4A, 0x03, 2)

# Convert the data to lux
exponent = (data[0] & 0xF0) >> 4
mantissa = ((data[0] & 0x0F) << 4) | (data[1] & 0x0F)
luminance = ((2 ** exponent) * mantissa) * 0.045

# Output data to screen
print "Ambient Light luminance : %.2f lux" %luminance
