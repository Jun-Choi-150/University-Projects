/*
 * wireless.h
 *
 *  Created on: May 26, 2022
 *      Author: ezmanus
 */

#ifndef WIRELESS_H_
#define WIRELESS_H_

#include "move.h"
#include "open_interface.h"
#include "Timer.h"
#include "uart.h"
#include <stdio.h>
#include "adc.h"
#include "servo.h"
#include "button.h"
#include "ping.h"



void check(char byte, oi_t *sensor);

void checkm(char byte, oi_t *sensor);

void w_key(oi_t *sensor);

void s_key(oi_t *sensor);

void a_key(oi_t *sensor);

void q_key(oi_t *sensor);

void d_key(oi_t *sensor);

void e_key(oi_t *sensor);

void plus_key(oi_t *sensor);

void minus_key(oi_t *sensor);

void c_key(oi_t *sensor);

void v_key(oi_t *sensor);

void sound(int song, oi_t *sensor);

void p_key(oi_t *sensor);

void m_key(oi_t *sensor);

void t_key(oi_t *sensor);

#endif /* WIRELESS_H_ */
