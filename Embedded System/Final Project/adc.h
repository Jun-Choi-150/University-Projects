/*
 * adc.h
 *
 *  Created on: Jun 14, 2022
 *      Author: ezmanus
 */

#ifndef ADC_H_
#define ADC_H_

#include "Timer.h"
#include "lcd.h"
#include "math.h"
#include "servo.h"


//void ir_init(void);
//
//int ir_read(void);
//
float ir_getDistance(int degree);

void adc_init(void);

int adc_read(void);



#endif /* ADC_H_ */
