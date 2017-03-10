//
//  LTCNewConcernViewModelTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCCoreDataTestCase.h"
#import "LTCNewConcernViewModel.h"
#import "LTCClientApi.h"
#import <OCHamcrest/OCHamcrest.h>
#import <OCMockito/OCMockito.h>

@interface LTCNewConcernViewModel ()
@property (nonatomic, strong) LTCClientApi *clientApi;
@property (nonatomic, strong) NSManagedObjectContext *context;
@property (readonly, nonatomic, strong) NSArray <NSString *> *testHook_descriptors;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorReporterName;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorPhoneNumber;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorEmail;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorConcernNature;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorFacilityName;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorRoomNumber;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorActionsTaken;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorDescription;
@property (readonly, nonatomic, strong) NSString *testHook_descriptorSubmitConcern;
@end

/**
 Tests the functionality of the new concern view model.
 */
@interface LTCNewConcernViewModelTests : LTCCoreDataTestCase

@end

@implementation LTCNewConcernViewModelTests

/**
 Testing the initialization of the LTCNewConcernViewModel by allocating a new view model and checking that the client api, the context, and all of the rows are not nil. Test also checks that the sections count is 4.
 */
- (void)testInitWithContext {
    LTCNewConcernViewModel *viewModel = [[LTCNewConcernViewModel alloc] initWithContext:self.context];
    
    XCTAssertNotNil(viewModel.clientApi);
    XCTAssertNotNil(viewModel.context);
    XCTAssertEqual(viewModel.formSections.count, 5);
    
    for (NSString *tag in viewModel.testHook_descriptors) {
        XCTAssertNotNil([viewModel formRowWithTag:tag]);
    }
}

/**
 Tests the submission callback of the view model by setting the sumbission callback and then calling the submission callback and making sure the same value is achieved.
 */
- (void)testSubmissionCallback {
    
    SEL testSelector = @selector(testSubmissionCallback);
    
    LTCNewConcernViewModel *viewModel = [[LTCNewConcernViewModel alloc] initWithContext:self.context];
    
    // Test setting it and then getting because they are inverses
    viewModel.submissionCallback = testSelector;    
    XCTAssertTrue([NSStringFromSelector(viewModel.submissionCallback) isEqualToString:NSStringFromSelector(testSelector)]);
}

/**
 Testing the submission of a concern with an error by calling submitConcernData with a concern with an error message. Test asserts that the passed error is returned and that the concern is nil.
 */
- (void)testSubmitConcernDataError {
    
    NSString *identifier = [NSBundle mainBundle].bundleIdentifier;
    NSError *passedError = [NSError errorWithDomain:identifier code:0 userInfo:nil];
    
    LTCClientApi *mockClient = mock([LTCClientApi class]);

    LTCNewConcernViewModel *viewModel = [[LTCNewConcernViewModel alloc] initWithContext:self.context];
    viewModel.clientApi = mockClient;
    [viewModel submitConcernData:^(LTCConcern *concern, NSError *error){
        XCTAssertEqual(passedError, error);
        XCTAssertNil(concern);
    }];
    
    HCArgumentCaptor *argument = [[HCArgumentCaptor alloc] init];
    [verify(mockClient) submitConcern:anything() completion:(id)argument];
    void (^completion)(GTLRClient_SubmitConcernResponse *response, NSError *error) = [argument value];
    
    completion(nil, passedError);
}

/**
 Testing submission of concern data. The test creates a random concern and populates the form with that concerns contents. The test then checks that each proporty of the concern is equal to each property for the text field values.
 */
- (void)testSubmitConcernData {
    
    LTCClientApi *mockClient = mock([LTCClientApi class]);
    
    LTCNewConcernViewModel *viewModel = [[LTCNewConcernViewModel alloc] initWithContext:self.context];
    viewModel.clientApi = mockClient;
    
    NSString *tokenValue = [NSUUID UUID].UUIDString;
    NSString *typeValue = [NSUUID UUID].UUIDString;
    NSDate *dateValue = [NSDate date];
    NSNumber *identifierValue = [NSNumber numberWithLongLong:123456];

    NSString *reporterNameValue = [NSUUID UUID].UUIDString;
    NSString *phoneNumberValue = [NSUUID UUID].UUIDString;
    NSString *emailValue = [NSUUID UUID].UUIDString;
    NSString *concernNatureValue = [NSUUID UUID].UUIDString;
    NSString *facilityNameValue = [NSUUID UUID].UUIDString;
    NSString *roomNumberValue = [NSUUID UUID].UUIDString;
    NSString *actionsTakenValue = [NSUUID UUID].UUIDString;
    NSString *descriptionValue = [NSUUID UUID].UUIDString;

    // Populate the row values with random strings as if they were input from the user
    [viewModel formRowWithTag:viewModel.testHook_descriptorReporterName].value = reporterNameValue;
    [viewModel formRowWithTag:viewModel.testHook_descriptorPhoneNumber].value = phoneNumberValue;
    [viewModel formRowWithTag:viewModel.testHook_descriptorEmail].value = emailValue;
    [viewModel formRowWithTag:viewModel.testHook_descriptorConcernNature].value = concernNatureValue;
    [viewModel formRowWithTag:viewModel.testHook_descriptorFacilityName].value = facilityNameValue;
    [viewModel formRowWithTag:viewModel.testHook_descriptorRoomNumber].value = roomNumberValue;
    [viewModel formRowWithTag:viewModel.testHook_descriptorActionsTaken].value = actionsTakenValue;
    [viewModel formRowWithTag:viewModel.testHook_descriptorDescription].value = descriptionValue;
    
    [viewModel submitConcernData:^(LTCConcern *concern, NSError *error){
        
        XCTAssertEqual(reporterNameValue, concern.reporter.name);
        XCTAssertEqual(phoneNumberValue, concern.reporter.phoneNumber);
        XCTAssertEqual(emailValue, concern.reporter.email);
        XCTAssertEqual(concernNatureValue, concern.concernNature);
        XCTAssertEqual(facilityNameValue, concern.location.facilityName);
        XCTAssertEqual(roomNumberValue, concern.location.roomName);
        XCTAssertEqual(actionsTakenValue, concern.actionsTaken);
        XCTAssertEqual(descriptionValue, concern.descriptionProperty);
        
        XCTAssertTrue([concern.identifier isEqualToString:identifierValue.stringValue]);
        XCTAssertEqual(concern.ownerToken, tokenValue);
        
        XCTAssertEqual(1, concern.statuses.count);
        XCTAssertEqual(typeValue, concern.statuses.firstObject.concernType);
    }];
    
    HCArgumentCaptor *argument = [[HCArgumentCaptor alloc] init];
    HCArgumentCaptor *concernArgument = [[HCArgumentCaptor alloc] init];

    [verify(mockClient) submitConcern:(id)concernArgument completion:(id)argument];
    void (^completion)(GTLRClient_SubmitConcernResponse *response, NSError *error) = [argument value];
    
    // Construct the concern that is returned by the ClientApi mock based on the concern data that is passed to it
    GTLRClient_ConcernData *data = [concernArgument value];
    GTLRClient_Concern *concern = [[GTLRClient_Concern alloc] init];
    
    concern.archived = @NO;
    concern.identifier = identifierValue;
    concern.submissionDate = [GTLRDateTime dateTimeWithDate:dateValue];
    concern.data = data;
    
    GTLRClient_ConcernStatus *status = [[GTLRClient_ConcernStatus alloc] init];
    status.creationDate = [GTLRDateTime dateTimeWithDate:dateValue];
    status.type = typeValue;
    concern.statuses = [NSArray arrayWithObject:status];
    
    GTLRClient_OwnerToken *ownerToken = [[GTLRClient_OwnerToken alloc] init];
    ownerToken.token = tokenValue;
    
    GTLRClient_SubmitConcernResponse *submitConcernResponse = [[GTLRClient_SubmitConcernResponse alloc] init];
    submitConcernResponse.concern = concern;
    submitConcernResponse.ownerToken = ownerToken;
    
    completion(submitConcernResponse, nil);
}

@end
