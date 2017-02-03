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
@property (readwrite, nonatomic, strong) NSPersistentStoreCoordinator *coordinator;
@property (readwrite, nonatomic, strong) NSManagedObjectContext *context;
@end

@implementation LTCCoreDataTestCase

- (void)setUp {
    [super setUp];
    
    NSURL *modelURL = [[NSBundle mainBundle] URLForResource:@"LTC_Safety" withExtension:@"momd"];
    NSManagedObjectModel *model = [[NSManagedObjectModel alloc] initWithContentsOfURL:modelURL];
    
    NSError *error = nil;
    self.coordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:model];
    [self.coordinator addPersistentStoreWithType:NSInMemoryStoreType configuration:nil URL:nil options:nil error:&error];
    
    XCTAssertNil(error);
    
    self.context = [[NSManagedObjectContext alloc] initWithConcurrencyType:NSMainQueueConcurrencyType];
    [self.context setPersistentStoreCoordinator:self.coordinator];

    // Set up in memory persistent store for testing
    /*NSPersistentStoreDescription *description = [[NSPersistentStoreDescription alloc] init];
    description.type = NSInMemoryStoreType;
    
    self.container = [[NSPersistentContainer alloc] initWithName:@"LTC_Safety"];
    self.container.persistentStoreDescriptions = @[description];
    [self.container loadPersistentStoresWithCompletionHandler:^(NSPersistentStoreDescription *storeDescription, NSError *error) {
        if (error != nil) {
            XCTFail("Failed to load in memory persistent store.");
        }
    }];*/
}

- (void)tearDown {
    //self.container = nil;
    [super tearDown];
}

- (void)testExample {
    // Use recording to get started writing UI tests.
    // Use XCTAssert and related functions to verify your tests produce the correct results.
}

@end
