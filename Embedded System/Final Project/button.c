/*
 * button.c
 *
 *  Created on: Jun 1, 2022
 *      Author: Ezmnus
 */

//The buttons are on PORTE 0:5
#include "button.h"
uint8_t _prevButton; //must be set yourself in button_getButton()

/**
 * Initialize PORTE and configure bits 5-0 to be used as inputs.
 */
void button_init() {
	static uint8_t initialized = 0;

	//Check if already initialized
	if(initialized)
		return;

	//Turn on PORTE system clock
	SYSCTL_RCGCGPIO_R |= 0x10;

	//Set the buttons to inputs and enable
	GPIO_PORTE_DIR_R &= ~(0x40 - 1); //Clear bits 5:0
	GPIO_PORTE_DEN_R |= (0x40 - 1);

	initialized = 1;
}

/**
 * returns a 6-bit bit field, representing the push buttons. A 1 means the corresponding button is pressed.
 */
uint8_t button_checkButtons() {
	return (~GPIO_PORTE_DATA_R) & (0x40 - 1); //Return the button status
}

/**
 * Returns the position of the leftmost button being pushed.
 * @return the position of the leftmost button being pushed. A 6 is the leftmost button, 1 is the rightmost button.  0 indicates no button being pressed
 */
uint8_t button_getButton() {
    uint8_t buttons;
    buttons = button_checkButtons();


    if ((buttons & 0x08) == 0x08){ //check button 4
        return 4;
    }
    if ((buttons & 0x04) == 0x04){ //check button 3
        return 3;
    }
    if ((buttons & 0x02) == 0x02){ //check button 2
        return 2;
    }
    if ((buttons & 0x01) == 0x01){ //check button 1
        return 1;
    }

}

uint8_t button_getButtonBlocking() {
	uint8_t button;

	//Wait for button to be pressed
	while( (button = button_getButton()) == 0 );

	return button;
}


int8_t button_getButtonChange() {
	uint8_t lastButton = _prevButton; //_prevButton can be set in button_getButton if you want to use this function.
	int8_t curButton = button_getButton();

	if(curButton != lastButton) {
		return curButton;
	}
	else {
		return -1;
	}
}

int8_t button_getButtonChangeBlocking() {
	int8_t button;

	while( (button = button_getButtonChange()) == -1);

	return button;
}
