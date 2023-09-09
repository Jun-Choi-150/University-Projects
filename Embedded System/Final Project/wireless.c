/*
 * header.c
 *
 *  Created on: May 26, 2022
 *      Author: ezmanus
 */

#include "move.h"
#include "open_interface.h"
#include "Timer.h"
#include "uart.h"
#include <stdio.h>
#include "adc.h"
#include "servo.h"
#include "button.h"
#include "ping.h"

typedef struct     //struct for object details
{
    int start;
    int end;
    float dist;
    float width;
    int angle;
} data;

void w_key(oi_t *sensor)
{
    move_forward(sensor, 60); //move forward 60 cm with W key
}

void s_key(oi_t *sensor)
{
    move_backward(sensor, 20); //move backward 20 cm with S key
}

void a_key(oi_t *sensor)
{
    turn_cclockwise(sensor, 90); //turn counterclockwise 90 degrees with A key
}

void q_key(oi_t *sensor)
{
    turn_cclockwise(sensor, 1); //turn counterclockwise 1 degrees with Q key
}

void d_key(oi_t *sensor)
{
    turn_clockwise(sensor, 90);  //turn clockwise 90 degrees with D key
}

void e_key(oi_t *sensor)
{
    turn_clockwise(sensor, 1);   //turn clockwise 1 degrees with E key
}

void plus_key(oi_t *sensor)
{
    move_forward(sensor, 5);  //move forward 5 cm with "=" key
}

void minus_key(oi_t *sensor)
{
    move_backward(sensor, 5); //move backward 5 cm with "-" key
}

void t_key(oi_t *sensor)
{
    ///////////////////////////////////////////////////////////
    ///////////////////Scan Forward Code///////////////////////
    ///////////////////////////////////////////////////////////

    float dist_ping = ping_getDistance(90);
    float dist_ir = ir_getDistance(90);
    int lo = 0;
    int l = 0;
    char line[30];
    char deg_dist[32] = "degrees     Distance (cm)    IR";
    for (l = 0; l < strlen(deg_dist); l = l + 1)
    {
        uart_sendChar(deg_dist[l]);
    }
    uart_sendChar('\n');
    uart_sendChar('\r');
    sprintf(line, "%3d         %4f      %4f", 90, dist_ping, dist_ir);
    for (lo = 0; lo <= strlen(line); lo = lo + 1)
    {
        uart_sendChar(line[lo]);
    }
    uart_sendChar('\n');
    uart_sendChar('\r');
    ////////////////////////////////////////////////////////////
}

void sound(int song, oi_t *sensor)
{
    //Plays out-of-bounds song
    if (song == 0)
    {
        int out_of_bounds = 0;
        int num_notes = 1;
        unsigned char out_of_bounds_pitch[] = { 54 }; //note pitch (range: 31-127)
        unsigned char out_of_bounds_duration[] = { 48 }; //plays num/64 of a second
        oi_loadSong(out_of_bounds, num_notes, out_of_bounds_pitch,
                    out_of_bounds_duration);
        oi_play_song(out_of_bounds);
    }

    //Plays beep sound
    if (song == 1)
    {
        int beep = 1;
        int num_notes = 2;
        unsigned char beep_pitch[] = { 60, 55 };    //note pitch (range: 31-127)
        unsigned char beep_duration[] = { 32, 32 };   //plays num/64 of a second
        oi_loadSong(beep, num_notes, beep_pitch, beep_duration);
        oi_play_song(beep);
    }

    //Plays bump sound
    if (song == 2)
    {
        int bump = 2;
        int num_notes = 3;
        unsigned char bump_pitch[] = { 65, 65, 65 }; //note pitch (range: 31-127)
        unsigned char bump_duration[] = { 32, 32, 32 }; //plays num/64 of a second
        oi_loadSong(bump, num_notes, bump_pitch, bump_duration);

        oi_play_song(bump);
    }

    //Plays mission complete sound
    if (song == 3)
    {
        int mission_complete = 3;
        int num_notes = 4;
        unsigned char mission_complete_pitch[] = { 57, 58, 59, 60 }; //note pitch (range: 31-127)
        unsigned char mission_complete_duration[] = { 16, 16, 16, 48 }; //plays num/64 of a second
        oi_loadSong(mission_complete, num_notes, mission_complete_pitch,
                    mission_complete_duration);
        oi_play_song(mission_complete);
    }

}

//Used when an ideal tree is detected
void p_key(oi_t *sensor)
{
    int lo = 0;
    char line[20];
    sound(1, sensor);
    uart_sendChar('\n');
    uart_sendChar('\r');
    sprintf(line, "kill kill");
    for (lo = 0; lo <= strlen(line); lo = lo + 1)
    {
        uart_sendChar(line[lo]);
    }
    uart_sendChar('\n');
    uart_sendChar('\r');
}

//Code for all the buttons used in manual mode
void checkm(char byte, oi_t *sensor)
{
    int q = 0;
    int index = 0;
    float smallest = 0;
    int i = 0;
    int o = 0;
    int l = 0;
    int lo = 0;
    int found = 0;
    char line[30];
    int scanDataPing[89], scanDataIR[89];
    int obj1[5], obj2[5], obj3[5], obj4[5], obj5[5];

    uart_sendChar('\n');
    uart_sendChar('\r');

    for (lo = 0; lo <= 30; lo = lo + 1)  //prints out spaces to putty
    {
        line[lo] = ' ';
    }

    if (byte == 'w')         // Move forward 1 tile
    {
        w_key(sensor);
    }

    if (byte == 'a')          // cybot turns 90 degrees left
    {
        a_key(sensor);
    }

    if (byte == 'q')         // cybot turns 1 degree to the left
    {
        q_key(sensor);
    }

    if (byte == 's')          // move backward 1 tile
    {
        s_key(sensor);
    }

    if (byte == 'd')           // cybot turns 90 degrees to the right
    {
        d_key(sensor);
    }

    if (byte == 'e')           // cybot turns 1 degree to the right
    {
        e_key(sensor);
    }

    if (byte == '=')           // cybot moves forward 5 cm
    {
        plus_key(sensor);
    }

    if (byte == '-')           //cybot moves backward 5 cm
    {
        minus_key(sensor);
    }

    if (byte == 'p')           //plays sound for tree detection & displays message to putty
    {
        p_key(sensor);
    }

    if (byte == 't')           // scan foward at 90 degrees
    {
        t_key(sensor);
    }

    if (byte == 'm')           // object detection
    {
        int ob = 1;
        int d = 0;
        int sample = 0;
        int i = 0;
        float dist_mem, dist_avg, dist_ping, dist_ir, dist_mem_ir;
        char uart[100];
        int lame = 1;
        data objects[11];
        objects[i].start = 0;
        objects[i].end = 1;
        objects[i].dist = 0;
        objects[i].width = 0;
        objects[i].angle = 0;
        i = 1;
        //initialize object arrays for later storage
        while (i < 11)
        {
            objects[i].start = 0;
            objects[i].end = 0;
            objects[i].dist = 0;
            objects[i].width = 0;
            objects[i].angle = 0;
            i = i + 1;
        }

        i = 1; //start object index at 1
        //header print statement
        char deg_dist[32] = "degrees     Distance (cm)    IR";

        for (l = 0; l < strlen(deg_dist); l = l + 1)
        {
            uart_sendChar(deg_dist[l]);
        }

        uart_sendChar('\n');
        uart_sendChar('\r');
        //store starting memory values to compare later
        dist_mem = ping_getDistance(d);
        timer_waitMillis(20);
        dist_mem_ir = ir_getDistance(d);
        //Begin scan sweep
        for (d = 0; d <= 180; d = d + 1)
        {
            dist_ping = ping_getDistance(d);
            dist_ir = ir_getDistance(d);
            //print statement for angle, ping distance, and ir distacnce
            sprintf(line, "%3d         %4f      %4f", d, dist_mem, dist_mem_ir);

            for (lo = 0; lo <= strlen(line); lo = lo + 1)
            {
                uart_sendChar(line[lo]);
            }

            uart_sendChar('\n');
            uart_sendChar('\r');
            if ((dist_ir < 80) || (dist_mem_ir < 80))
            {   //detect start edge of object
                if ((objects[i].start == 0) && (found == 0))
                {
                    found = 1;
                    objects[i].start = d; //start degree
                    //print start statement to PUTTY
                    sprintf(line, "start");

                    for (lo = 0; lo <= strlen(line); lo = lo + 1)
                    {
                        uart_sendChar(line[lo]);
                    }

                    uart_sendChar('\n');
                    uart_sendChar('\r');

                    dist_avg = dist_ping; //starts distance average
                    sample = 1; //sample number reset
                }

                //detect end edge of object
                if ((dist_ir > 80) && (objects[i].end == 0) && (found == 1))
                {
                    found = 0;
                    dist_avg = dist_avg / sample; //calculates average
                    objects[i].end = d; //end degree
                    //print end statement to PUTTY
                    sprintf(line, "end");

                    for (lo = 0; lo <= strlen(line); lo = lo + 1)
                    {
                        uart_sendChar(line[lo]);
                    }

                    uart_sendChar('\n');
                    uart_sendChar('\r');

                    objects[i].dist = dist_avg; //set distance

                    int beg = objects[i].start;
                    int end = objects[i].end;
                    //calculate object width
                    objects[i].width = end - beg - 2;
                    objects[i].width = (int) ((tan(
                            (3.14 / 180.0) * ((objects[i].width) / 2.0))
                            * objects[i].dist) * 2.2);
                    objects[i].angle = ((objects[i].end - objects[i].start) / 2)
                            + objects[i].start;

                    if ((objects[i].width > 0) && (objects[i].width < 10))
                    {//if object is ideal object run p key function to play sound and print to putty
                        p_key(sensor);
                    }

                    i = i + 1;//raise object index
                    objects[i] = (data){.start=0, .end=0, .dist=0, .width=0};//initialize next object in case of improper storage
                    ob = ob + 1;//raise detected object count
            }

        }
        else
        {   //ping distance averaging
            dist_avg = dist_avg + dist_mem;
            sample = sample + 1;
        }
            //update previous scan values
        dist_mem = dist_ping;
        dist_mem_ir = dist_ir;
    }
        //print header for detected objects table
    char header[50] = "Object        Degrees     Distance     Tree Width";

    for (lo = 0; lo <= strlen(header) + 1; lo = lo + 1)             // prints the array above (header[50]) to putty
    {
        uart_sendChar(header[lo]);
    }

    uart_sendChar('\n');
    uart_sendChar('\r');
    //print object info to table
    for (i = 0; i < ob; i = i + 1)                       // prints every object's angle, distance, and width to putty
    {
        sprintf(line, "%d            %3d           %4f          %f", i + 1,
                (objects[i].angle), objects[i].dist, objects[i].width);

        for (lo = 0; lo <= strlen(line); lo = lo + 1)
        {
            uart_sendChar(line[lo]);
        }

        uart_sendChar('\n');
        uart_sendChar('\r');
    }

    i = 1;
    int smallWidth = 999999999; //initialize smalest object size to lage value
    int smallObjAngle = 0;      //initialie smallest object index angle to 0, willl be replaced later

    for (q = 0; q < ob; q = q + 1)                                         // finds mid angle of each object and finds the smallest object
    {
        int setAngle = ((objects[q].end + objects[q].start) / 2);          //calculates mid angle of each object
        timer_waitMillis(500);

        double objWidth = (double) ((tan(                                       // calculates width of each object
                (3.14 / 180.0) * ((objects[q].width) / 2.0)) * objects[q].dist)
                * 2.2);

        if (objWidth < smallWidth)              //finds smallest width
        {
            smallWidth = objWidth;
            smallObjAngle = setAngle;
        }
    }
}
}
