//
//  Logger.h
//  LTC Safety
//
//  Created by Niklaas Neijmeijer on 2017-02-12.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#ifndef Logger_h
#define Logger_h


#endif /* Logger_h */
/**
 The Logger class sends logs to Firebase
 */
@interface Logger : NSObject

/**
 log sends a message to Firebase with the recorded level
 */
+ (void)log:(NSString *) level :(NSString*)message;


@end