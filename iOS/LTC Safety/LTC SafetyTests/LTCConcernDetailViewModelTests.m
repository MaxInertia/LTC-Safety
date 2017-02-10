//
//  LTCDetailConcernViewModelTests.m
//  LTC Safety
//
//  Created by Daniel Morris on 2017-02-08.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCConcernDetailViewModel.h"
#import "LTCClientApi.h"
#import <XCTest/XCTest.h>
#import <OCHamcrest/OCHamcrest.h>
#import <OCMockito/OCMockito.h>
#import "LTCCoreDataTestCase.h"
#import "LTCConcern_Testing.h"


@interface LTCConcernDetailViewModel ()
@property (nonatomic, strong) LTCClientApi *clientApi;
@property (nonatomic, strong) LTCConcern *concern;
@end

/**
 Tests the functionality of the new concern view model.
 */
@interface LTCDetailConcernViewModelTests : LTCCoreDataTestCase

@end

@implementation LTCDetailConcernViewModelTests
/**
 Testing the initialization of the LTCConcernDetailViewModel by allocating a new view model and checking that the client api, the concern. Test also checks that the sections count is 6.
 */
- (void)testInitWithConcern {
    LTCConcern *testConcern = [LTCConcern testConcernWithContext:self.context];
    
    LTCConcernDetailViewModel *viewModel = [[LTCConcernDetailViewModel alloc] initWithConcern:testConcern];
    
    
    //access each row in viewModel to make sure the test concern was added correctly
    XCTAssertEqual([viewModel formRowWithTag:@"REPORTER_NAME"].title, testConcern.reporter.name);
    XCTAssertEqual([viewModel formRowWithTag:@"EMAIL_ADDRESS"].title, testConcern.reporter.email);
    XCTAssertEqual([viewModel formRowWithTag:@"PHONE_NUMBER"].title, testConcern.reporter.phoneNumber);
    XCTAssertEqual([viewModel formRowWithTag:@"CONCERN_NATURE"].title, testConcern.concernNature);
    XCTAssertEqual([viewModel formRowWithTag:@"FACILITY_NAME"].title, testConcern.location.facilityName);
    XCTAssertEqual([viewModel formRowWithTag:@"ROOM_NUMBER"].title, testConcern.location.roomName);
    XCTAssertEqual([viewModel formRowWithTag:@"ACTIONS_TAKEN"].title, testConcern.actionsTaken);
    XCTAssertTrue([[viewModel formRowWithTag:@"SUBMISSION_DATE"].title isEqualToString: [NSDateFormatter localizedStringFromDate: testConcern.statuses.firstObject.creationDate dateStyle:NSDateFormatterMediumStyle timeStyle:NSDateFormatterShortStyle]]);
    XCTAssertEqual([viewModel formRowWithTag:@"CONCERN_STATUS"].title, testConcern.statuses.lastObject.concernType);
    
    XCTAssertNotNil(viewModel.clientApi);
    XCTAssertNotNil(viewModel.concern);
    XCTAssertEqual(viewModel.formSections.count, 6);
    
}
@end
