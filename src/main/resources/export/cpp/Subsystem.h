#set($subsystem = $helper.getByName($subsystem-name, $robot))
#header()

#ifndef #constant($subsystem.name)_H
\#define #constant($subsystem.name)_H
\#include "Commands/Subsystem.h"
\#include "WPILib.h"

/**
 *
 *
 * @author ExampleAuthor
 */
class #class($subsystem.name): public Subsystem {
private:
	// It's desirable that everything possible is private except
	// for methods that implement subsystem capabilities
#@autogenerated_code("declarations", "	")
#parse("${exporter-path}Subsystem-declarations.h")
#end
public:
	#class($subsystem.name)();
	void InitDefaultCommand();
};

#endif
