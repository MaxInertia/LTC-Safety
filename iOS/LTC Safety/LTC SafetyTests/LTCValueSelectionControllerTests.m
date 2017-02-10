//
//  LTCValueSelectionControllerTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCCategoryViewController.h"
#import "LTCFacilityViewController.h"
#import "LTCRowValue.h"

@interface LTCValueSelectionController ()
- (XLFormRowDescriptor *)_createRow:(LTCRowValue *)rowValue action:(SEL)action;
- (void)_selectCategory:(XLFormRowDescriptor *)categoryDescriptor;
@end

/**
 Unit tests for the LTCValueSelectionController class.
 */
@interface LTCValueSelectionControllerTests : XCTestCase

@end

@implementation LTCValueSelectionControllerTests

/**
 Testing the initialization of the value selection controller by creating a Category view controller and a facility view controller. The tests check that each controller is not nil, has 2 sections with 1 row in the second section, and that each descriptor in the rows has a title, tag, and a value.
 */
- (void)testInit {
    
    // Test all LTCValueSelectionController subclasses to ensure they are being properly loaded from file
    NSArray *classes = [NSArray arrayWithObjects:[LTCCategoryViewController class], [LTCFacilityViewController class], nil];
    
    for (Class valueSelectionClass in classes) {
        
        LTCValueSelectionController *controller = [[valueSelectionClass alloc] init];
        
        XCTAssertNotNil(controller.form);
        XCTAssertEqual(controller.form.formSections.count, 2);
        
        XLFormSectionDescriptor *otherSection = controller.form.formSections.lastObject;
        XCTAssertEqual(otherSection.formRows.count, 1);
        
        XLFormSectionDescriptor *valueSection = controller.form.formSections.firstObject;
        
        // Test that all rows have a title, tag, and no starting value
        for (XLFormRowDescriptor *descriptor in valueSection.formRows) {
            XCTAssertNotNil(descriptor.title);
            XCTAssertNotNil(descriptor.tag);
            XCTAssertNil(descriptor.value);
        }
    }
}

/**
 Testing the row creation by creating a row with a value then checking that that row has the same tag that was set to it's value, and that the row's title and tag is the same as the value's title and tag.
 */
- (void)testCreateRow {
    
    LTCCategoryViewController *controller = [[LTCCategoryViewController alloc] init];

    SEL action = @selector(_selectCategory:);
    LTCRowValue *value = [[LTCRowValue alloc] initWithTag:@"CATEGORY_1"];
    XLFormRowDescriptor *row = [controller _createRow:value action:action];
    
    // Tests that a row was created with a title, tag, and action selector
    XCTAssertTrue([NSStringFromSelector(action) isEqualToString:NSStringFromSelector(row.action.formSelector)]);
    XCTAssertEqual(row.title, value.title);
    XCTAssertEqual(row.tag, value.tag);
}

/**
 Tests the selection of a category by simulating the selection of a row and testing that the output value corresponds to that of the row that was passed.
 */
- (void)testSelectCategory {
    
    LTCCategoryViewController *controller = [[LTCCategoryViewController alloc] init];
    controller.rowDescriptor = [XLFormRowDescriptor formRowDescriptorWithTag:@"other tag" rowType:@"other type"];

    // The descriptor for the category to select.
    XLFormRowDescriptor *descriptor = [XLFormRowDescriptor formRowDescriptorWithTag:@"tag" rowType:@"type"];
    descriptor.title = @"testSelectCategory";
    
    [controller _selectCategory:descriptor];
    
    XCTAssertTrue([descriptor.title isEqualToString:controller.rowDescriptor.value]);
}

@end
