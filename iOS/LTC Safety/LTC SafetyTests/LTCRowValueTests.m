//
//  LTCRowValueTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCRowValue.h"

/**
 Tests for the LTCRowValue class.
 */
@interface LTCRowValueTests : XCTestCase

@end

@implementation LTCRowValueTests

/**
 Tests that the LTCRowValue class can parse a dictionary in the expected value selection configuration file format. 
 @note A test for an unexpected format is not needed because the format can be guaranteed to be correct at compile time. Any failure will result in an assertion during development.
 */
- (void)testInit {
    
    NSString *tag = @"FACILITY_1";
    NSDictionary *dictionary = @{@"title" : tag};
    LTCRowValue *rowValue = [[LTCRowValue alloc] initWithDictionary:dictionary];
    
    XCTAssertNotNil(rowValue);
    XCTAssertNotNil(rowValue.title);
    XCTAssertEqual(rowValue.tag, tag);
}

@end
