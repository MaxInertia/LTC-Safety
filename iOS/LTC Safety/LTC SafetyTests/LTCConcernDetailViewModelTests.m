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
@property (readonly, nonatomic, strong) NSString *testHook_descriptorReporterName;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorPhoneNumber;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorEmail;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorConcernNature;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorFacilityName;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorRoomNumber;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorActionsTaken;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorDescription;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorConcernStatus;

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
    XCTAssertEqual([viewModel formRowWithTag:viewModel.testHook_descriptorReporterName].value, testConcern.reporter.name);
    XCTAssertEqual([viewModel formRowWithTag:viewModel.testHook_descriptorEmail].value, testConcern.reporter.email);
    XCTAssertEqual([viewModel formRowWithTag:viewModel.testHook_descriptorPhoneNumber].value, testConcern.reporter.phoneNumber);
    XCTAssertEqual([viewModel formRowWithTag:viewModel.testHook_descriptorConcernNature].value, testConcern.concernNature);
    XCTAssertEqual([viewModel formRowWithTag:viewModel.testHook_descriptorFacilityName].value, testConcern.location.facilityName);
    XCTAssertEqual([viewModel formRowWithTag:viewModel.testHook_descriptorRoomNumber].value, testConcern.location.roomName);
    XCTAssertEqual([viewModel formRowWithTag:viewModel.testHook_descriptorActionsTaken].value, testConcern.actionsTaken);
    XCTAssertEqual([viewModel formRowWithTag:viewModel.testHook_descriptorDescription].value, testConcern.descriptionProperty);
    XCTAssertEqual([viewModel formRowWithTag:viewModel.testHook_descriptorConcernStatus].title , NSLocalizedString(testConcern.statuses.firstObject.concernType, nil));
    XCTAssertTrue([[viewModel formRowWithTag:viewModel.testHook_descriptorConcernStatus].value isEqualToString: [NSDateFormatter localizedStringFromDate: testConcern.statuses.firstObject.creationDate dateStyle:NSDateFormatterMediumStyle timeStyle:NSDateFormatterShortStyle]]);
    
    XCTAssertNotNil(viewModel.clientApi);
    XCTAssertNotNil(viewModel.concern);
    XCTAssertEqual(viewModel.formSections.count, 6);
    
}
@end
