//
//  LTCClientApiTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "GTLRClientServiceMock.h"
#import "LTCClientApi.h"
#import "GTLRClientObjects_Testing.h"

@interface LTCClientApi ()
@property (nonatomic, strong) GTLRClientService *service;
@end

@interface LTCClientApiTests : XCTestCase
@property (nonatomic, strong) GTLRClientServiceMock *service;
@end

@implementation LTCClientApiTests

- (void)setUp {
    self.service = [[GTLRClientServiceMock alloc] init];
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}

- (void)testSubmitConcern {
    LTCClientApi *clientApi = [[LTCClientApi alloc] init];
    self.service.simulateError = NO;
    clientApi.service = self.service;
    
    GTLRClient_ConcernData *concern = [GTLRClient_ConcernData testConcernData];
    [clientApi submitConcern:concern completion:^(GTLRClient_SubmitConcernResponse *response, NSError *error){
        XCTAssertNotNil(response);
        XCTAssertNil(error);
    }];
}

- (void)testSubmitConcernError {
    
    LTCClientApi *clientApi = [[LTCClientApi alloc] init];
    self.service.simulateError = YES;
    clientApi.service = self.service;
    
    GTLRClient_ConcernData *concern = [GTLRClient_ConcernData testConcernData];
    [clientApi submitConcern:concern completion:^(GTLRClient_SubmitConcernResponse *response, NSError *error){
        XCTAssertNil(response);
        XCTAssertNotNil(error);
    }];
}

- (void)testRetractConcern {
    
    LTCClientApi *clientApi = [[LTCClientApi alloc] init];
    self.service.simulateError = NO;
    clientApi.service = self.service;
    
    [clientApi retractConcern:@"an arbitrary jwt" completion:^(NSError *error){
        XCTAssertNil(error);
    }];
}

- (void)testRetractConcernError {
    
    LTCClientApi *clientApi = [[LTCClientApi alloc] init];
    self.service.simulateError = YES;
    clientApi.service = self.service;
    
    [clientApi retractConcern:@"an arbitrary jwt" completion:^(NSError *error){
        XCTAssertNotNil(error);
    }];
}

@end
