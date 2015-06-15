#header()

#ifndef ROBOTMAP_H
\#define ROBOTMAP_H
\#include "WPILib.h"

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
class RobotMap {
public:
#@autogenerated_code("declarations", "	")
#parse("${exporter-path}RobotMap-declarations.h")
#end

	static void init();
};
#endif
