/*
 * move.c
 *
 *  Created on: May 24, 2022
 *      Author: ezmanus
 */

#include "move.h"
#include "open_interface.h"
#include "Timer.h"
#include "uart.h"
#include "ping.h"
#include "adc.h"
#include "wireless.h"

void move_forward(oi_t *sensor, int centimeters)
{
//cyBot #13 wheel speeds = 152.7, 147
    double sum = 0;
    double total = 0;
    int l = 0;
    int lo = 0;
    char line[30];

    oi_setWheels(149, 151.5); // move forward;

    while (sum < (10 * centimeters))
    {
        oi_update(sensor);

        //detecting hole
        if (((sensor->cliffLeftSignal) < 1000) || ((sensor->cliffFrontLeftSignal) < 1000)
                || ((sensor->cliffRightSignal) < 1000) || ((sensor->cliffFrontRightSignal) < 1000))
        {
            sound(2, sensor);
            move_backward(sensor, ((sum / 10)));
            uart_sendChar('\n');
            uart_sendChar('\r');

            sprintf(line, "Cliff Detected");

            for (lo = 0; lo <= strlen(line); lo = lo + 1) //sending message to putty that there's a hole/cliff
            {
                uart_sendChar(line[lo]);
            }
            uart_sendChar('\n');
            uart_sendChar('\r');
            break;
        }

        //detecting white tape
        if (((sensor->cliffLeftSignal) > 2600) || ((sensor->cliffFrontLeftSignal) > 2600)
                || ((sensor->cliffRightSignal) > 2600) || ((sensor->cliffFrontRightSignal) > 2600))
        {
            move_backward(sensor, (sum / 10));

            uart_sendChar('\n');
            uart_sendChar('\r');

            sprintf(line, "Tape Detected");

            for (lo = 0; lo <= strlen(line); lo = lo + 1) //sending message to putty that there's white tape
            {
                uart_sendChar(line[lo]);
            }

            uart_sendChar('\n');
            uart_sendChar('\r');

            sound(0, sensor);
            break;
        }

        if (sensor->bumpLeft)       //if sensor is bumped from the left side
        {
            sound(2, sensor);
            move_backward(sensor, (sum / 10));    //moves back to where it was
            uart_sendChar('\n');
            uart_sendChar('\r');

            sprintf(line, "Stump Hit");

            for (lo = 0; lo <= strlen(line); lo = lo + 1)   //sending message to putty that there's a stump
            {
                uart_sendChar(line[lo]);
            }

            uart_sendChar('\n');
            uart_sendChar('\r');
            break;
        }

        if (sensor->bumpRight)      //if sensor is bumped from the left side
        {
            uart_sendChar('\n');
            uart_sendChar('\r');

            sprintf(line, "Stump Hit");

            for (lo = 0; lo <= strlen(line); lo = lo + 1)  //sending message to putty that there's a stump
            {
                uart_sendChar(line[lo]);
            }
            uart_sendChar('\n');
            uart_sendChar('\r');

            sound(2, sensor);
            move_backward(sensor, (sum / 10));
            break;
        }

        sum += sensor->distance;
    }

    oi_setWheels(0, 0); // stop
}
void move_backward(oi_t *sensor, int centimeters)
{
    double sumb = 0;
    int lo = 0;
    char line[20];
    oi_setWheels(-149, -151.5); // move backward;

    while (sumb > -10 * centimeters)
    {
        oi_update(sensor);

        //detecting white tape
        if (((sensor->cliffLeftSignal) > 2600) || ((sensor->cliffFrontLeftSignal) > 2600)
                || ((sensor->cliffRightSignal) > 2600) || ((sensor->cliffFrontRightSignal) > 2600))
        {
            uart_sendChar('\n');
            uart_sendChar('\r');

            sprintf(line, "Tape detected");

            for (lo = 0; lo <= strlen(line); lo = lo + 1) //sending message to putty that there's white tape
            {
                uart_sendChar(line[lo]);
            }

            uart_sendChar('\n');
            uart_sendChar('\r');

            sound(2, sensor);
        }

        sumb += sensor->distance;
    }
    oi_setWheels(0, 0); // stop
}

//cyBot turns right
void turn_clockwise(oi_t *sensor, int degrees)
{
    double sum = 0;
    oi_setWheels(-149, 151.5); // rotate right;

    while (sum > 0.91 * (-degrees))
    {
        oi_update(sensor);
        sum += sensor->angle;
    }

    oi_setWheels(0, 0); // stop
}

//cyBot turns left
void turn_cclockwise(oi_t *sensor, int degrees)
{
    double sum = 0;
    oi_setWheels(149, -151.5); // rotate left

    while (sum < 0.91 * (degrees))
    {
        oi_update(sensor);
        sum += sensor->angle;
    }
    oi_setWheels(0, 0); // stop
}

//bump detection for left bumper
void collision_left(oi_t *sensor)
{
    uart_sendChar('L');
    move_backward(sensor, 3);
    turn_clockwise(sensor, 90);
    move_forward(sensor, 10);
    turn_cclockwise(sensor, 90);
}

//bump detection for right bumper
void collision_right(oi_t *sensor)
{
    uart_sendChar('R');
    move_backward(sensor, 3);
    turn_cclockwise(sensor, 90);
    move_forward(sensor, 10);
    turn_clockwise(sensor, 90);
}
