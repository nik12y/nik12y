package com.nikspringComponent;


import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
 
@Configuration
@PropertySource("classpath:myLogger.properties")
public class MyConfigLogger {

//	@Value("${root.logger.level}")
//	private String rootLoggerLevel;
//
//	@Value("${printed.logger.level}")
//	private String printedLoggerLevel;
//
//	@PostConstruct
//	public void initLogger() {
/*	public MyConfigLogger(String rootLoggerLevel, String printedLoggerLevel) {
		// parse level
		Level printLevel = Level.parse(printedLoggerLevel);

		Level rootLevel = Level.parse(rootLoggerLevel);

		// get logger for app context
		Logger applicationContextLogger = Logger.getLogger(AnnotationConfigApplicationContext.class.getName());

		// get parent logger
		Logger loggerParent = applicationContextLogger.getParent();

		// set root logging level
		loggerParent.setLevel(rootLevel);

		// Console set up
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(printLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		
		// add handler to the logger
		loggerParent.addHandler(consoleHandler);
	}
*/
}
