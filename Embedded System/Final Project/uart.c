/*
 *
 *   uart.c
 *
 *
 *
 *   @author
 *   @date
 */

#include "uart.h"
#include "Timer.h"

volatile char uart_data;  // Your UART interupt code can place read data here
volatile int flag;       // Your UART interupt can update this flag

void uart_init(void)        //initializes UART
{
    SYSCTL_RCGCGPIO_R |= 0b000010;
    timer_waitMillis(1);
    SYSCTL_RCGCUART_R = 0b010;
    timer_waitMillis(1); // Small delay before accessing device after turning on clock
    GPIO_PORTB_AFSEL_R |= 0b00000011;
    GPIO_PORTB_PCTL_R &= 0xFFFFFFEE;     // Force 0's in the disired locations
    GPIO_PORTB_PCTL_R |= 0x00000011;     // Force 1's in the disired locations
    GPIO_PORTB_DEN_R |= 0b00000011;
    GPIO_PORTB_DIR_R &= 0b11111110;       // Force 0's in the disired locations
    GPIO_PORTB_DIR_R |= 0b00000010;

    //compute baud values [UART clock= 16 MHz]
    double fbrd;
    int ibrd;
    //Baud rate in lab = 115200
    ibrd = 8;
    fbrd = 44;

    UART1_CTL_R &= 0xFE;      // disable UART1 (page 918)
    UART1_IBRD_R = ibrd;        // write integer portion of BRD to IBRD
    UART1_FBRD_R = fbrd;   // write fractional portion of BRD to FBRD
    UART1_LCRH_R = 0x60; // write serial communication parameters (page 916) * 8bit and no parity
    UART1_CC_R = 0x0;          // use system clock as clock source (page 939)
    UART1_CTL_R |= 0b1100000001;        // enable UART1
}

void uart_interrupt_init()           ////initializes UART interrupts
{
    UART1_CTL_R &= 0xFFFFFCFE;
    UART1_ICR_R |= 0xFFFF2FF2;
    UART1_IM_R |= 0x00000030;
    //NVIC_PRI1_R |= 0x00200000;
    NVIC_EN0_R |= 0x00000040;
    UART1_CTL_R |= 0x00000301;
        // Bind the interrupt to the handler.
        IntRegister(22, uart_handler);
        IntMasterEnable();
}



void uart_sendChar(char data)     // sends char to putty
{
    while (((UART1_FR_R) & 0x20)!=0)
    {
    }
    UART1_DR_R = data;
}

char uart_receive(void)            //receives char from putty
{
    char rec;
    while ((UART1_FR_R & 0x10))
    {
    }

    rec = (char) UART1_DR_R;
    return rec;
}

void uart_sendStr(const char *data)  //sends string to putty
{
    while (*data != '\0')
    {
        uart_sendChar(*data);
        data = data + 1;
    }
}

void uart_handler()
{
    if(UART1_MIS_R & 0b00010000)
    {
        UART1_ICR_R |= 0b10000;
        uart_data = (char) UART1_DR_R;
        flag = 1;
    }

    else if(UART1_MIS_R && 0b00100000)
    {
        UART1_ICR_R |= 0b100000;
    }
}

