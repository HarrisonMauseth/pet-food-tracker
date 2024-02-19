# Dog Food Reminder Readme
This project stems from living in a house with four dogs who love to lie about how much food they have eaten. Since the dogs often slept in different rooms, there was not really a great system to ensure they all got fed the proper amount.

The solution? Create a display that lives near the dog food with physical buttons and a UI simple enough that a 5 year old can operate it while leaving myself room to iterate on the design as I learn more.

In order to accomplish this, I needed to do the following:

- Gather hardware for the physical controller and console
- Write code to control the hardware
- Create a simple printed circuit board to reduce wiring clutter
- Design a container in Fusion 360 to house the hardware
- 3D Print the container, solder the parts, and rollout the device to my home

## Key Directories
While the project is by no means massive, it is worth highlighting a few of the key directories found in this repository:

- `src` - contains the `main.cpp` file where the code for the application lives.
- `pcb-files` - as the name suggests, this directory is where you can find the files associated with the printed circuit board.
- `stl-files` - if you are looking to find the files for 3D printing, they stored here. 

## Roadmap
I am tackling this project with a three phased approach:
- Phase One - initial design and implementation (Completed)
- Phase Two - design a database and server to be accessible via a RESTful API
- Phase Three - client side GUI

The current state of the project is that I have a functioning piece of hardware in my home that works exactly as initially intended. However, since the start of this project, I have been toying with the idea of integrating a server and a client side GUI

## Phase One (Completed) - Initial Design and Implementation
This is where the project started. All I wanted was a physical device to sit on my counter to reduce the excessive daily conversations about whether or not the dogs have been fed. This phase of the project accomplished exactly that. 

### Hardware Required
The following hardware was used on the physical device. Since the 3D printed container was modeled based on these specific brands, I have included a link to the exact models I purchased. With that said, there is no way for me to be certain that the products stayed consistent since my purchase in July of 2023, so your milage may vary:

- ESP32 Microcontroller [(Sold by Flutesan on Amazon)](https://www.amazon.com/dp/B09GK74F7N)
- LCD2004 I2C Serial LCD [(Sold by GeekPi on Amazon)](https://www.amazon.com/dp/B09GK74F7N/)
- HC-SR501 PIR sensor [(Sold by DIYmall on Amazon)](https://www.amazon.com/gp/product/B012ZZ4LPM)
- 4-Pin Tactile Push Button Momentary Switch [(Sold by TWTADE on Amazon)](https://www.amazon.com/gp/product/B07CG7VTGD)
- M3 x 5.7mm Threaded Inserts [(Sold by Ruthex on Amazon)](https://www.amazon.com/gp/product/B08BCRZZS3)

In addition to this, I also designed a printed circuit board (PCB) for stability and cleanliness. Should you want to order your own, I have included the KiCad files and the gerber files needed for ordering from a site like PCBWay in the `pcb-files` directory of this repository.

The PCB is quite simplistic. It provides a flush mount for 6 momentary switches and the ESP32.  It also provides solder pads for the 20x4 LCD wiring as well as the IR sensor wiring.

The LCD can print up to 4 rows, 20 characters each. On the back, it has a jumper cable that connects the positive terminal to the backlight. I removed this jumper cable and instead wired up a PIR sensor so that way I can have the display turn on automatically as I approach the device.

### Code for Physical Implementation
In the current state of the project, the code for the physical device does the following:
- Assigns the GPIO of the the ESP32
- Provides a series of variables that track elapsed time
- Prints information to the LCD screen
- Provides logic to control the LCD backlight via IR sensor
- Provides logic to print correct time frames

The code for this project was written before I ever had any formal training on industry standards. While I see plenty of opportunities to refactor this code, it functions exactly as I intended it to and is still fairly concise given the lack of coding knowledge I had at the time of writing this. With that said, there were quite a few challenges I needed to overcome.

The first hurdle was implementing the logic to track elapsed time without using timestamps in a database. I ended up using Arduino's `millis()` function which returns the number of milliseconds passed since the current program began running. Given that the datatype is an unsigned long, there is the inevitability that this will reset after about 50 days. While far from perfect, human error (read: we forget to push the buttons) happens about once or twice per week. This ended up being an acceptable compromise for the scope of the project. 

The second challenge was figuring out the best way to print the elapsed times to the LCD. I wanted something that looks nice, clean, and can convey all of the information with the strict limit of 20 characters per line. I opted for doing having a "right aligned column" for the dog names and then a left aligned column for the timers. Additionally, instead of displaying a continuous stopwatch, I opted for phrases like "now eating", "5 hours ago", and "safe to eat". This was less of a challenge of logic and more of a challenge of design, but we landed somewhere that works perfectly for our house.

The last challenge was refactoring my code. With zero coding experience, this was by far the hardest part of the project for me at the time. As is the theme of this project, it is far from perfect. As they say, hindsight is 20/20. And while I see plenty of opportunity to refactor and implement the principles of Object Oriented Programming, I'm just happy that I had the foresight to break this down into bite-sized methods with meaningful variable names. Since the plan is to overhaul the code to be a client-side API, I don't see any point in cleaning this up to show off my skills now. To me, the code holds more value as a memento of who I was when I started coding and can help me get out of a mental block when the imposter syndrome inevitably comes up.

### Printed Circuit Board (PBC) Layout
The PCB for this project is rudimentary. There are no complex timing chips or resistors needed. The goal was to provide a sturdy mounting plate for the ESP32 and the 6 buttons. The added benefit is that it reduces the clutter from wires and helps eliminate confusion during the soldering process. Given that this was designed for a specific ESP32 and some specific momentary switches, your milage may vary.

The KiCad project files and the Gerber files can be found within the `pcb-files` directory of the repository.

### 3D Printing Design
As is the case with the PCB, I have designed the case to fit the specific hardware. As much as I loved having the breadboard and exposed wires out, I knew that a long-term solution was needed. I opted for a design that has a bit of a retro feel. The design choice was simply based on making the 3D printing process easier. 

All files can be printed without supports. I printed this in a black PLA filament. The base holds the PCB and has cutouts to feed the wires. There is a cover that sits flush with the specific model of momentary switches. The top of the case holds the LCD and the infrared sensor. I have also included some guide holes to allow for threaded inserts. See the hardware list for more information.

## Phase Two - Database Design and Server Side API
This is the current phase of the project. At the time of writing this, I am wrapping up the client-server programming module of a full-stack software development bootcamp and want to implement what I have been learning into this project. Over the next few weeks, I want to start moving away from the all-in-one physical device that I created in phase one and start moving towards a server/client based application. This can be broken down into a few major steps:

- Create a PostgreSQL database to track timestamps, serving sizes, nutritious facts, etc.
- Develop a Java application for accessing the data using Spring's JDBC
- Implement a server-side RESTful API using the Spring Boot framework
- Build a physical server to host the database
- Overhaul the application on the physical device to act as a client-side controller

## Phase Three - Client Side GUI
As the final phase of the project, this is still in a pre-brainstorming state. I will likely be developing a web application using HTML, CSS, and JavaScript to allow for remote access to the database. More information will be made available once Phase Two has completed.