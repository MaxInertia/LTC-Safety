//
//  Logger.m
//  LTC Safety
//
//  Created by Niklaas Neijmeijer on 2017-02-12.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
@import Firebase;

@implementation Logger : NSObject ;

+ (void)log:(NSString *) level :(NSString *)message{
    [FIRApp configure];
    [FIRAnalytics logEventWithName:kFIREventSelectContent
                        parameters:@{
                                     kFIRParameterItemID:@"level",
                                     kFIRParameterItemName:@"message",
                                     kFIRParameterContentType:@"log"
                                     }];
}


@end