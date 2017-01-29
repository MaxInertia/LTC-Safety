//
//  LTCCoreDataTestCase.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-29.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "LTCCoreDataTestCase.h"

@interface LTCCoreDataTestCase ()
@property (readwrite, nonatomic, strong) NSPersistentContainer *container;
@end

@implementation LTCCoreDataTestCase

- (void)setUp {
    [super setUp];
    
    // Put setup code here. This method is called before the invocation of each test method in the class.
    
    // In UI tests it is usually best to stop immediately when a failure occurs.
    self.continueAfterFailure = NO;
    
    // Set up in memory persistent store for testing
    NSPersistentStoreDescription *description = [[NSPersistentStoreDescription alloc] init];
    description.type = NSInMemoryStoreType;
    
    self.container = [[NSPersistentContainer alloc] initWithName:@"LTC_Safety"];
    self.container.persistentStoreDescriptions = @[description];
    [self.container loadPersistentStoresWithCompletionHandler:^(NSPersistentStoreDescription *storeDescription, NSError *error) {
        if (error != nil) {
            XCTFail("Failed to load in memory persistent store.");
        }
    }];
}

- (void)tearDown {
    self.container = nil;
    [super tearDown];
}

- (void)testExample {
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

@end
