//
//  LTCClientApiTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCClientApi.h"
#import "GTLRClientObjects_Testing.h"
#import <OCHamcrest/OCHamcrest.h>
#import <OCMockito/OCMockito.h>
/**
 Unit tests for the LTCClientApi class.
 */
@interface LTCClientApi ()
@property (nonatomic, strong) GTLRClientService *service;
@end

@interface LTCClientApiTests : XCTestCase
@end

@implementation LTCClientApiTests


/**
 Testing submit concern when an error is not introduced. Method tests that the concern was successfully added to the datastore by checking that a response came back from the server and that there was no error that came back with it.
 */
- (void)testSubmitConcern {
    GTLRClientService *serviceMock = mock([GTLRClientService class]);
    
    LTCClientApi *clientApi = [[LTCClientApi alloc] init];
    clientApi.service = serviceMock;
    
    GTLRClient_ConcernData *concern = [GTLRClient_ConcernData testConcernData];
    [clientApi submitConcern:concern completion:^(GTLRClient_SubmitConcernResponse *response, NSError *error){
        XCTAssertNotNil(response);
        XCTAssertNil(error);
    }];
    
    HCArgumentCaptor *argument = [[HCArgumentCaptor alloc] init];
    [verify(serviceMock) executeQuery:anything() completionHandler:(id)argument];
    void (^handler)(GTLRServiceTicket *ticket, id object, NSError *error) = [argument value];
    handler(nil, [GTLRClient_Concern testConcern], nil);
}
/**
 Testing submit concern when an error is introduced. Method tests that the concern was unsuccessfully added to the datastore by checking that a response came back from the server with an error that came back with it.
 */
- (void)testSubmitConcernError {
    
    NSString *identifier = [NSBundle mainBundle].bundleIdentifier;
    NSError *passedError = [NSError errorWithDomain:identifier code:0 userInfo:nil];
    
    GTLRClientService *serviceMock = mock([GTLRClientService class]);
    
    LTCClientApi *clientApi = [[LTCClientApi alloc] init];
    clientApi.service = serviceMock;
    
    GTLRClient_ConcernData *concern = [GTLRClient_ConcernData testConcernData];
    [clientApi submitConcern:concern completion:^(GTLRClient_SubmitConcernResponse *response, NSError *error){
        XCTAssertNil(response);
        XCTAssertEqual(passedError, error);
    }];
    
    HCArgumentCaptor *argument = [[HCArgumentCaptor alloc] init];
    [verify(serviceMock) executeQuery:anything() completionHandler:(id)argument];
    void (^handler)(GTLRServiceTicket *ticket, id object, NSError *error) = [argument value];
    handler(nil, nil, passedError);
}
/**
 Testing retract concern when an error is not introduced. Method tests that the concern was successfully retracted from the datastore by checking that a response came back from the server with a nil error.
 */
- (void)testRetractConcern {
    
    GTLRClientService *serviceMock = mock([GTLRClientService class]);
    
    LTCClientApi *clientApi = [[LTCClientApi alloc] init];
    clientApi.service = serviceMock;
    
    [clientApi retractConcern:@"an arbitrary jwt" completion:^(GTLRClient_UpdateConcernStatusResponse *response, NSError *error){
        XCTAssertNil(error);
    }];
    
    HCArgumentCaptor *argument = [[HCArgumentCaptor alloc] init];
    [verify(serviceMock) executeQuery:anything() completionHandler:(id)argument];
    void (^handler)(GTLRServiceTicket *ticket, id object, NSError *error) = [argument value];
    handler(nil, nil, nil);
}
/**
 Testing retract concern when an error is introduced. Method tests that the concern was unsuccessfully retracted from the datastore by checking that a response came back from the server with an error.
 */
- (void)testRetractConcernError {
    
    NSString *identifier = [NSBundle mainBundle].bundleIdentifier;
    NSError *passedError = [NSError errorWithDomain:identifier code:0 userInfo:nil];
    
    GTLRClientService *serviceMock = mock([GTLRClientService class]);
    
    LTCClientApi *clientApi = [[LTCClientApi alloc] init];
    clientApi.service = serviceMock;
    
    [clientApi retractConcern:@"an arbitrary jwt" completion:^(GTLRClient_UpdateConcernStatusResponse *response, NSError *error){
        XCTAssertEqual(passedError, error);
    }];
    
    HCArgumentCaptor *argument = [[HCArgumentCaptor alloc] init];
    [verify(serviceMock) executeQuery:anything() completionHandler:(id)argument];
    void (^handler)(GTLRServiceTicket *ticket, id object, NSError *error) = [argument value];
    handler(nil, nil, passedError);
}

@end
