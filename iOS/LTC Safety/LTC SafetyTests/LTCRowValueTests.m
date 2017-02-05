//
//  LTCRowValueTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCRowValue.h"

@interface LTCRowValueTests : XCTestCase

@end

@implementation LTCRowValueTests


- (void)testInit {
    
    NSString *tag = @"FACILITY_1";
    NSDictionary *dictionary = @{@"title" : tag};
    LTCRowValue *rowValue = [[LTCRowValue alloc] initWithDictionary:dictionary];
    
    XCTAssertNotNil(rowValue);
    XCTAssertNotNil(rowValue.title);
    XCTAssertEqual(rowValue.tag, tag);
}

@end
