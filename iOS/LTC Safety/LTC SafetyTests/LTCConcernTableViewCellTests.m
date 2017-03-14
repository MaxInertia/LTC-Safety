//
//  LTCConcernTableViewCellTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-04.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCConcernTableViewCell.h"
#import "LTCConcern_Testing.h"
#import "LTCCoreDataTestCase.h"

@interface LTCConcernTableViewCell ()
@property (nonatomic, strong) IBOutlet UILabel *concernNatureLabel;
@property (nonatomic, strong) IBOutlet UILabel *facilityLabel;
@property (nonatomic, strong) IBOutlet UILabel *dateLabel;
@end
/**
 Tests the functionality of the LTC Concern table view cells.
 */
@interface LTCConcernTableViewCellTests : LTCCoreDataTestCase

@end

@implementation LTCConcernTableViewCellTests

/**
 Testing of the population of a cell. Cannot be done through unit testing due to the data coming from main.storyboard which is not able to happen in this unit test. This will need to be tested with UI testing.
 */
- (void)testConfigure {
    
    // TODO Test with UITesting

    LTCConcern *concern = [LTCConcern testConcernWithContext:self.context];
    
    // This can't be tested with unit testing because the labels are only initialized when a cell is created inside the LTCConcernViewController by the storyboard
    // This causes all labels to be nil along with their corresponding text when assigned.
    
    LTCConcernTableViewCell *cell = [[LTCConcernTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"Cell"];
    [cell configureWithConcern:concern];
    
    //XCTAssertEqual(cell.concernNatureLabel.text, concern.concernNature);
    //XCTAssertEqual(cell.facilityLabel.text, concern.location.facilityName);
    //XCTAssertNotNil(cell.dateLabel.text);
}

/**
 Testing the awaking of a cell by creating a cell and calling awakeFromNib on that cell, then making sure that the cell is of the correct accessory type.
 */
- (void)testAwake {
    LTCConcernTableViewCell *cell = [[LTCConcernTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"Cell"];
    [cell awakeFromNib];
    
    XCTAssertTrue(cell.accessoryType == UITableViewCellAccessoryDisclosureIndicator);
}

@end
