#include <Arduino.h>
#include <Wire.h>
#include <hd44780.h>
#include <hd44780ioClass/hd44780_I2Cexp.h>

// initialize LED
hd44780_I2Cexp lcd;
const int LCD_COLUMN_CHARACTER_COUNT = 20;
const int LCD_ROW_CHARACTER_COUNT = 4;

// GPIO layout
const int PIN_BUTTON_1   =  4;
const int PIN_BUTTON_2   = 18;
const int PIN_BUTTON_3   = 19;
const int PIN_BUTTON_4   = 14;
const int PIN_BUTTON_5   = 27;
const int PIN_BUTTON_6   = 26;
const int PIN_BACKLIGHT  = 23;
const int PIN_PIR_SENSOR = 33;

// Button and Sensor Names
unsigned int button1 = 0;
unsigned int button2 = 0;
unsigned int button3 = 0;
unsigned int button4 = 0;
unsigned int button5 = 0;
unsigned int button6 = 0;
unsigned int motionSensor = 0;

// Dog names for screen
String dogB = "  Bucky: ";
String dogE = "  Ender: ";
String dogP = "Pretzel: ";
String dogS = " Stormy: ";

// Timestamps
unsigned long stampB1 = 0;
unsigned long stampB2 = 0;
unsigned long stampB3 = 0;
unsigned long stampB4 = 0;
unsigned long stampB5 = 0;
unsigned long stampB6 = 0;
unsigned long stampBL = 0;

// Elapsed Timers
unsigned long currentTime = 0;
unsigned long timeSinceB1 = 0;
unsigned long timeSinceB2 = 0;
unsigned long timeSinceB3 = 0;
unsigned long timeSinceB4 = 0;
unsigned long timeSinceB5 = 0;
unsigned long timeSinceB6 = 0;
unsigned long timeSinceBL = 0;

// Define timekeeping constants
const unsigned long HOURS_IN_MILLIS   = 3600000;
const unsigned long MINUTES_IN_MILLIS = 60000;
const unsigned long SECONDS_IN_MILLIS = 1000;

// LCD Refreshrate
unsigned long previousTime   = 0;
const unsigned int REFRESH_RATE_IN_MILLIS = 200;
const unsigned int BACKLIGHT_TIMEOUT      = 15 * SECONDS_IN_MILLIS;

// Print Bucky's Name
void bucky() {
  lcd.setCursor(0,0);
  lcd.print(dogB);
}

// Print Ender's Name
void ender() {
  lcd.setCursor(0,1);
  lcd.print(dogE);
}

// Print Pretzel's Name
void pretzel() {
  lcd.setCursor(0,2);
  lcd.print(dogP);
}

// Print Stormy's Name
void stormy() {
  lcd.setCursor(0,3);
  lcd.print(dogS);
}

// Array to call when you need to display times
String timeSinceFed[] = {
  "Now eating!", //  0
  "Recently   ", //  1
  "1 hour ago ", //  2
  "2 hours ago", //  3
  "3 hours ago", //  4
  "4 hours ago", //  5
  "5 hours ago", //  6
  "6 hours ago", //  7
  "7 hours ago", //  8
  "8 hours ago", //  9
  "9 hours ago", // 10
  "Safe to Eat", // 11
  "NEEDS FOOD!", // 12
  "updating..."  // 13
} ;

// Print times based off timeSinceFed array
void printElapsedTime(unsigned long myTime) {
  if (myTime < .2 * SECONDS_IN_MILLIS) {
    lcd.print(timeSinceFed[13]);
  } else if (myTime < 5 * MINUTES_IN_MILLIS) {
    lcd.print(timeSinceFed[0]);
  } else if (myTime < 1 * HOURS_IN_MILLIS) {
    lcd.print(timeSinceFed[1]);
  } else if (myTime < 2 * HOURS_IN_MILLIS) {
    lcd.print(timeSinceFed[2]);
  } else if (myTime < 3 * HOURS_IN_MILLIS) {
    lcd.print(timeSinceFed[3]);
  } else if (myTime < 4 * HOURS_IN_MILLIS) {
    lcd.print(timeSinceFed[4]);
  } else if (myTime < 5 * HOURS_IN_MILLIS) {
    lcd.print(timeSinceFed[5]);
  } else if (myTime < 6 * HOURS_IN_MILLIS) {
    lcd.print(timeSinceFed[6]);
  } else if (myTime < 7 * HOURS_IN_MILLIS) {
    lcd.print(timeSinceFed[7]);
  } else if (myTime < 8 * HOURS_IN_MILLIS) {
    lcd.print(timeSinceFed[8]);
  } else if (myTime < 9 * HOURS_IN_MILLIS) {
    lcd.print(timeSinceFed[9]);
  } else if (myTime < 10 * HOURS_IN_MILLIS) {
    lcd.print(timeSinceFed[10]);
  } else if (myTime < 12 * HOURS_IN_MILLIS){
    lcd.print(timeSinceFed[11]);
  } else {
    lcd.print(timeSinceFed[12]);
  }
}

// Turn the backlight on
void enableBacklight() {
  digitalWrite(PIN_BACKLIGHT, HIGH);
}

// Turn the backlight off
void disableBacklight() {
  digitalWrite(PIN_BACKLIGHT, LOW);
}

// Reset the backlight timer
void resetBacklightTimer() {
  stampBL = currentTime;
}

// Logic for button 1
void pushButton1() {
  if (button1 == LOW) {
    stampB1 = currentTime;
    resetBacklightTimer();    
  }
}

// Logic for button 2
void pushButton2() {
  if (button2 == LOW) {
    stampB2 = currentTime;
    resetBacklightTimer();
  }  
}

// Logic for button 3
void pushButton3() {
  if (button3 == LOW) {
    stampB3 = currentTime;
    resetBacklightTimer();
  }
}

// Logic for button 4
void pushButton4() {
  if (button4 == LOW) {
    stampB4 = currentTime;
    resetBacklightTimer();
  }  
}

// Logic for button 5
void pushButton5() {
  if (button5 == LOW) {
    stampB1 = currentTime;
    stampB2 = currentTime;
    stampB3 = currentTime;
    stampB4 = currentTime;
    stampB5 = currentTime;
    resetBacklightTimer();
  }
}

// Logic for button 6
void pushButton6() {
  if (button6 == LOW) {
    stampB6 = currentTime;
    resetBacklightTimer();
  }  
}

// Logic for backlight
void checkBacklight() {
  if (motionSensor == HIGH) {
    enableBacklight();
    resetBacklightTimer();
  } else if (timeSinceBL <= BACKLIGHT_TIMEOUT) {
    enableBacklight();
  } else {
    disableBacklight();
  }
}

// Reads GPIO and checks for button presses
void checkButtonUpdates() {
  button1 = digitalRead(PIN_BUTTON_1);
  button2 = digitalRead(PIN_BUTTON_2);
  button3 = digitalRead(PIN_BUTTON_3);
  button4 = digitalRead(PIN_BUTTON_4);
  button5 = digitalRead(PIN_BUTTON_5);
  button6 = digitalRead(PIN_BUTTON_6);
  motionSensor = digitalRead(PIN_PIR_SENSOR);
  pushButton1();
  pushButton2();
  pushButton3();
  pushButton4();
  pushButton5();
  pushButton6();
}

// Updates the timers
void updateTimers() {
  currentTime = millis();
  timeSinceB1 = currentTime - stampB1;
  timeSinceB2 = currentTime - stampB2;
  timeSinceB3 = currentTime - stampB3;
  timeSinceB4 = currentTime - stampB4;
  timeSinceB5 = currentTime - stampB5;
  timeSinceB6 = currentTime - stampB6;
  timeSinceBL = currentTime - stampBL;
}

// Print updated info to the LCD
void screenRefresh () {
  if (currentTime - previousTime >= REFRESH_RATE_IN_MILLIS) {
  bucky();
  printElapsedTime(timeSinceB1);
  ender();
  printElapsedTime(timeSinceB2);
  pretzel();
  printElapsedTime(timeSinceB3);
  stormy();
  printElapsedTime(timeSinceB4);
  previousTime = currentTime;
  }
}

// Bootup sequence
void setup() {
  Serial.begin(115200);

  // Initialize GPIO types using the internal pull up resistor instead of hard wiring one
  pinMode (PIN_BUTTON_1, INPUT_PULLUP); 
  pinMode (PIN_BUTTON_2, INPUT_PULLUP);
  pinMode (PIN_BUTTON_3, INPUT_PULLUP);
  pinMode (PIN_BUTTON_4, INPUT_PULLUP);
  pinMode (PIN_BUTTON_5, INPUT_PULLUP);
  pinMode (PIN_BUTTON_6, INPUT_PULLUP);
  pinMode (PIN_BACKLIGHT, OUTPUT);
  pinMode (PIN_PIR_SENSOR, INPUT);

  // Initiialize LCD Screen settings
  int status;  
  status = lcd.begin(LCD_COLUMN_CHARACTER_COUNT,LCD_ROW_CHARACTER_COUNT);
  if (status) {
    hd44780::fatalError(status);
  }
  lcd.noBlink();
  lcd.noAutoscroll();
  enableBacklight();
}

// Main sequence
void loop() {
  updateTimers();
  checkBacklight();
  checkButtonUpdates();
  screenRefresh();
}
