//
//  LTCConcernTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-01.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "GTLRClient.h"

@interface LTCConcernTests : XCTestCase

@end

@implementation LTCConcernTests

- (void)setUp {
    [super setUp];
}

- (void)tearDown {
    [super tearDown];
}

- (void)testConcernWithConcern {

    //GTLRClient_Concern *concern = [[GTLRClient_Concern alloc] init];
    concern.archived = @false;
    concern.identifier = @100000000;
    //concern.submissionDate
    
    //concern.data = [[GTLRClient_ConcernData alloc] init];
    
    
}



//+ (instancetype)concernWithConcern:(GTLRClient_Concern *)concern;



@end
