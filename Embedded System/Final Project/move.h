/*
 * move.h
 *
 *  Created on: May 24, 2022
 *      Author: ezmanus
 */

#ifndef MOVE_H_
#define MOVE_H_
#include "open_interface.h"
#include "Timer.h"


void move_forward(oi_t *sensor, int centimeters);
void move_backward(oi_t *sensor, int centimeters);
void turn_clockwise(oi_t *sensor, int degrees);
void turn_cclockwise(oi_t *sensor, int degrees);
void collision_left(oi_t *sensor);
void collision_right(oi_t *sensor);


#endif /* MOVE_H_ */
