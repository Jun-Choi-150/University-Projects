/*
 * main.c
 *
 *  Created on: Jun 14, 2022
 *      Author: ezmanus
 */

#include "adc.h"
#include "Timer.h"
#include "lcd.h"
#include "math.h"

void adc_init(void)
{
    //First
       SYSCTL_RCGCGPIO_R |= 0b000010;        // enable Port B
       SYSCTL_RCGCADC_R |= 0x1;              // enable clock for ADC0
       timer_waitMillis(1);
       //Set up GPIO
       GPIO_PORTB_AFSEL_R |= 0b00010000;      // Set pin 4 to alternate function
       GPIO_PORTB_AMSEL_R |= 0b00010000;      // Enable pin 4 to analog mode
       GPIO_PORTB_DEN_R &= 0b111011111;       // Disable pin 4 to Digital Function
       GPIO_PORTB_DIR_R &= 0b111011111;       // Set pin4 to input

       //Set up ADC
       ADC0_ACTSS_R &= 0b1110;               // clear SS0 in ADC0

       ADC0_SSMUX0_R |= 0x0A;              // 1st sample AIN1, 2nd sample AIN2, 3rd sample AIN8
       ADC0_SSCTL0_R |= 0x06;              // end at 3rd sample with interrupt

       ADC0_IM_R &= 0xFFFFE;

       ADC0_SAC_R = 0x3; //8 sample

       ADC0_ACTSS_R = 0b0001; //Enable SS0 in ADC0
}

int adc_read(void)           //gets raw IR values
{
    int data = 0;

    ADC0_PSSI_R |= 0x1;

    ADC0_ISC_R |= 0x00001;

    while(ADC0_RIS_R & 0x01)    // while FIFO is empty
    {

    }

    data = (int)ADC0_SSFIFO0_R;

    return data;
}



float ir_getDistance(int degree)       //uses adc_read to get distance by plugging it into the equation
{
    servo_move(degree);
    int value = adc_read();

    float distance = 136906 * pow(value, -1.214);        // distance equation from excel graph

    return distance;
}
