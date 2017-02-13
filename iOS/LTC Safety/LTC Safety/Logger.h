//
//  Logger.h
//  LTC Safety
//
//  Created by Niklaas Neijmeijer on 2017-02-12.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

/**
 The Logger class records logs for use with tracing
 */
#import "LTCLogLevel.h"
@interface Logger : NSObject

/**
 log records a message with the specified level
 @param message    The message to be logged
 @param level      The severity of the message
 */
+ (void)log:(NSString *)message level:(LTCLogLevel)level;

/**
 Called once, before any logging is done to setup the system
 */
+ (void)configure;

@end