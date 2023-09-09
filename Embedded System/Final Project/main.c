/*
 * main.c
 *
 *  Created on: Jun 14, 2022
 *      Author: ezmanus
 */

#include "uart.h"
#include "Timer.h"
#include <inc/tm4c123gh6pm.h>
#include "driverlib/interrupt.h"
#include "lcd.h"
#include <stdbool.h>
#include <stdint.h>
#include <string.h>
#include "wireless.h"
#include <stdio.h>
#include "move.h"
#include "open_interface.h"
#include <math.h>
#include "adc.h"
#include "servo.h"
#include "ping.h"
#include "button.h"

volatile char uart_data;  // Your UART interupt code can place read data here
volatile int flag;        // Your UART interupt can update this flag

void main()
{
    timer_init();
    lcd_init();
    uart_init();
    uart_interrupt_init();
    ping_init();
    adc_init();
    servo_init();
    oi_t *sensor_data = oi_alloc();
    oi_init(sensor_data);

    char got[7] = "got an ";
    char temp = ' ';
    int j = 0;
    int autonomous = 0;
    flag = 0;

    while (1)
    {
        oi_update(sensor_data);
        lcd_printf("%d", sensor_data->cliffFrontLeftSignal);

        if ((flag == 1)||(autonomous==1))
        {
            flag = 0;
            uart_sendChar(uart_data);

            if (uart_data == 'o') //autonomous mode change (not used)
            {
                if (autonomous == 1)
                {
                    autonomous = 0;
                }
                else if (autonomous == 0)
                {
                    autonomous = 1;
                }
            }

            if (autonomous == 0) //manual mode
            {
                lcd_printf("manual");
                char byte = (char) uart_data;

                checkm(byte, sensor_data);
            }

            if (autonomous == 1) // autonomous mode (Note: autonomous mode was not used in our project
            {                    // since it did not work. So this part doesn't do anything)
                lcd_printf("autonomous");
                checkm('m', sensor_data);
            }

        }
    }

}

