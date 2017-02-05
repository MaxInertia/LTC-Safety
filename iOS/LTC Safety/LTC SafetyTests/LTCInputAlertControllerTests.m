//
//  LTCInputAlertControllerTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCInputAlertController.h"

/**
 Tests for the LTCInputAlertController class.
 */
@interface LTCInputAlertControllerTests : XCTestCase

@end


@implementation LTCInputAlertControllerTests

/**
 Tests that the designated LTCInputAlertController constructor creates an object with a valid title, message, textfield, and two buttons.
 */
- (void)testAlertControllerWithTitle {
    
    NSString *title = @"Title";
    NSString *message = @"Message";
    
    LTCInputAlertController *controller = [LTCInputAlertController alertControllerWithTitle:title message:message preferredStyle:UIAlertControllerStyleAlert];
    
    XCTAssertTrue([controller isKindOfClass:[LTCInputAlertController class]]);
    XCTAssertNotNil(controller);

    XCTAssertEqual(title, controller.title);
    XCTAssertEqual(message, controller.message);
    
    XCTAssertEqual(1, controller.textFields.count);
    XCTAssertEqual(2, controller.actions.count);
}

@end
