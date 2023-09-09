/*
 * servo.h
 *
 *  Created on: Jun 23, 2022
 *      Author: ezmanus
 */

#ifndef SERVO_H_
#define SERVO_H_

#include <inc/tm4c123gh6pm.h>
#include <stdint.h>
#include <lcd.h>
#include "button.h"
#include "timer.h"
#include "lcd.h"

void servo_init();

void servo_move(int degree);

void servo_calibrate(void);

#endif /* SERVO_H_ */
