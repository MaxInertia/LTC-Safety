//
//  LTCConcernViewModelTests.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-27.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <XCTest/XCTest.h>
@import CoreData;

@interface LTCConcernViewModelTests : XCTestCase
@property (nonatomic,retain) NSPersistentContainer *container;
@end

@implementation LTCConcernViewModelTests

- (void)setUp {
    [super setUp];
    
    // Put setup code here. This method is called before the invocation of each test method in the class.
    
    // In UI tests it is usually best to stop immediately when a failure occurs.
    self.continueAfterFailure = NO;
    // UI tests must launch the application that they test. Doing this in setup will make sure it happens for each test method.
    [[[XCUIApplication alloc] init] launch];
    
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

- (void)testaddConcernTest {

}

- (void)testExample {
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

@end
