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

@interface LTCConcernTableViewCellTests : LTCCoreDataTestCase

@end

@implementation LTCConcernTableViewCellTests

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

- (void)testWake {
    LTCConcernTableViewCell *cell = [[LTCConcernTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"Cell"];
    [cell awakeFromNib];
    
    XCTAssertTrue(cell.accessoryType == UITableViewCellAccessoryDisclosureIndicator);
}

@end
