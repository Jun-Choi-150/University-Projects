/*
*
*   ping.h
*/

#ifndef PING_H_
#define PING_H_

#include <stdio.h>
#include <timer.h>
#include <inc/tm4c123gh6pm.h>
#include "stdbool.h"
#include "driverlib/interrupt.h"
#include <math.h>
#include "servo.h"
volatile int overflow;
void TIMER3B_Handler(void);

void ping_init(void);

void send_pulse(void);

float ping_getDistance(int degree);

#endif /* PING_H_ */
