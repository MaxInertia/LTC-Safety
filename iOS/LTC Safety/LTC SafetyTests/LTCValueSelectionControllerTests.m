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

@interface LTCValueSelectionControllerTests : XCTestCase

@end

@implementation LTCValueSelectionControllerTests

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
